package com.application.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = {"id", "timestamp", "from", "to", "text"})
@EqualsAndHashCode(of = {"id"})
@Document(collection = "messages")
public class Message {
    @Getter
    @Setter
    @Id
    private String id;

    @Getter
    @Setter
    private String conversationId;

    @Getter
    @Setter
    private Instant timestamp;

    @Getter
    @Setter
    private String from;

    @Getter
    @Setter
    private String to;

    @Getter
    @Setter
    private String text;
}
