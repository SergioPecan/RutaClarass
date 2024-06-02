package com.application.resources;

import com.application.dtos.UserRequestDto;
import com.application.entities.User;
import com.application.resources.exceptions.StandardError;
import com.application.resources.util.URL;
import com.application.services.UserService;
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
@RequestMapping(value = "/users")
public class UserResource {
    @Autowired
    private UserService userService;

    @ApiResponses(value = {
        @ApiResponse(responseCode="200", description="Success", content=@Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))
    })
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<List<User>> findAll(
        @RequestParam(value="name", required=false) String name,
        @RequestParam(value="email", required=false) String email
    ) {
        name = URL.decodeParam(name);
        email = URL.decodeParam(email);

        List<User> list = userService.getAll(name, email);
        return ResponseEntity.ok().body(list);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode="200", description="Success", content={ @Content(schema = @Schema(implementation = User.class)) }),
        @ApiResponse(responseCode="404", description="User not found error", content={ @Content(schema = @Schema(implementation = StandardError.class)) })
    })
    @GetMapping(value="/{id}", produces={"application/json"})
    public ResponseEntity<User> findByIdentifier(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode="201", description="Success", content={ @Content(schema = @Schema(implementation = User.class)) }),
        @ApiResponse(responseCode="422", description="Validation error", content={ @Content(schema = @Schema(implementation = StandardError.class)) }),
        @ApiResponse(responseCode="500", description="Generic error", content={ @Content(schema = @Schema(implementation = StandardError.class)) })
    })
    @PostMapping(consumes={"application/json"}, produces={"application/json"})
    public ResponseEntity<User> insert(@Valid @RequestBody UserRequestDto dto) {
        User user =  userService.insert(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode="200", description="Success", content={ @Content(schema = @Schema(implementation = User.class)) }),
        @ApiResponse(responseCode="422", description="Validation error", content={ @Content(schema = @Schema(implementation = StandardError.class)) }),
        @ApiResponse(responseCode="500", description="Generic error", content={ @Content(schema = @Schema(implementation = StandardError.class)) })
    })
    @PutMapping(value="/{id}", consumes={"application/json"}, produces={"application/json"})
    public ResponseEntity<User> update(@PathVariable String id, @Valid @RequestBody UserRequestDto dto) {
        User user = userService.updateById(id, dto);
        return ResponseEntity.ok().body(user);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode="200", description="Success"),
        @ApiResponse(responseCode="404", description="User not found error", content={ @Content(schema = @Schema(implementation = StandardError.class)) }),
        @ApiResponse(responseCode="500", description="Generic error", content={ @Content(schema = @Schema(implementation = StandardError.class)) })
    })
    @DeleteMapping(value="/{id}", produces={"application/json"})
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
