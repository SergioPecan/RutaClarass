package com.application.dtos;

import com.application.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @Schema(example = "Jessica Abigail", required = true)
    @Getter
    @Setter
    @NotNull
    @Length(min = 3)
    private String name;

    @Schema(example = "jessica@gmail.com", required = true)
    @Getter
    @Setter
    @NotNull
    @Length(min = 3)
    @Email(message = "Email should be valid")
    private String email;

    public UserRequestDto(User obj) {
        this.name = obj.getName();
        this.email = obj.getEmail();
    }

    public User toUser() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .build();
    }
}
