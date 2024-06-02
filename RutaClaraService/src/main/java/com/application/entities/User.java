package com.application.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = {"id", "name", "email"})
@EqualsAndHashCode(of = {"id"})
@Document(collection = "users")
public class User {
    @Getter
    @Setter
    @Id
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Indexed(unique=true)
    private String email;
}
