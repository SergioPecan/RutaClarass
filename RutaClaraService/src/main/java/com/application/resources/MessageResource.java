package com.application.resources;

import com.application.dtos.MessageRequestDto;
import com.application.entities.Message;
import com.application.resources.exceptions.StandardError;
import com.application.services.MessageService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/messages")
public class MessageResource {
    @Autowired
    private MessageService messageService;

    @ApiResponses(value = {
        @ApiResponse(responseCode="200", description="Success", content=@Content(array = @ArraySchema(schema = @Schema(implementation = Message.class)))),
        @ApiResponse(responseCode="404", description="Message not found", content={ @Content(schema = @Schema(implementation = StandardError.class)) })
    })
    @GetMapping(produces={"application/json"})
    public ResponseEntity<List<Message>> findByConversationId(@RequestParam(value="conversationId") String conversationId) {
        List<Message> list = messageService.findByConversationId(conversationId);
        return ResponseEntity.ok().body(list);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode="200", description="Success", content={ @Content(schema = @Schema(implementation = Message.class)) }),
        @ApiResponse(responseCode="404", description="Message not found", content={ @Content(schema = @Schema(implementation = StandardError.class)) })
    })
    @GetMapping(value="/{id}", produces={"application/json"})
    public ResponseEntity<Message> findById(@PathVariable String id) {
        Message message = messageService.findById(id);
        return ResponseEntity.ok().body(message);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode="201", description="Success", content={ @Content(schema = @Schema(implementation = Message.class)) }),
        @ApiResponse(responseCode="422", description="Validation error", content={ @Content(schema = @Schema(implementation = StandardError.class)) }),
        @ApiResponse(responseCode="500", description="Generic error", content={ @Content(schema = @Schema(implementation = StandardError.class)) }),
    })
    @PostMapping(consumes={"application/json"}, produces={"application/json"})
    public ResponseEntity<Message> insert(@Valid @RequestBody MessageRequestDto dto) {
        Message message = messageService.insert(dto.toMessage());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(message.getId())
                .toUri();

        return ResponseEntity.created(uri).body(message);
    }
}
