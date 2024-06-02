package com.application.dtos;

import com.application.entities.Message;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {
    @Schema(example="5ea6f1925a50e86a4e0a266b", required=true)
    @Getter @Setter
    private String conversationId;

    @Schema(example="2020-05-04T19:21:37.008Z", required=true)
    @Getter @Setter
    @NotNull 
    private String timestamp;

    @Schema(example="5ea6e5f13d8c1f5b4730747d", required=true)
    @Getter @Setter
    @NotNull @Length(min=3)
    private String from;

    @Schema(example="5ea6e5f33d8c1f5b4730747e", required=true)
    @Getter @Setter
    @NotNull @Length(min=3)
    private String to;

    @Schema(example="Hello, how are you?", required=true)
    @Getter @Setter
    @NotNull @Length(min=3)
    private String text;

    public MessageRequestDto(Message obj) {
        this.conversationId = obj.getConversationId();
        this.timestamp = obj.getTimestamp().toString();
        this.from = obj.getFrom();
        this.to = obj.getTo();
        this.text = obj.getText();
    }

    public Message toMessage() {
        return Message.builder()
                .conversationId(this.conversationId)
                .timestamp(Instant.parse(this.timestamp))
                .from(this.from)
                .to(this.to)
                .text(this.text)
                .build();
    }
}
