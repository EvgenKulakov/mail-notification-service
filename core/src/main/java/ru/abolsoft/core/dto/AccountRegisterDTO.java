package ru.abolsoft.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.abolsoft.core.entity.Account;

@Setter
@Getter
@NoArgsConstructor
public class AccountRegisterDTO {

    // TODO: messages
    @NotBlank(message = "username - обязательное поле")
    private String username;

    @NotBlank
    private String password;

    @Pattern(regexp = "^(ROLE_USER|ROLE_MODERATOR)$")
    private String role;

    @Email
    private String email;
}
