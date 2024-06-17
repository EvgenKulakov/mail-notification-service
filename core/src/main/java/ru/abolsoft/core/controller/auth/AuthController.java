package ru.abolsoft.core.controller.auth;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.abolsoft.core.dto.AccountRegisterDTO;
import ru.abolsoft.core.entity.Account;
import ru.abolsoft.core.service.AccountService;

@RestController
public class AuthController {

    @Autowired
    AccountService accountService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AccountRegisterDTO accountRegisterDTO,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(
                    """
                    Validation error, use JSON:
                    {
                         "username": "username",
                         "password": "username",
                         "role": "ROLE_MODERATOR",
                         "email": "email@email.com"
                    }
                    """);
        }

        Account account = null;
        try {
            account = createAccount(accountRegisterDTO);
            accountService.saveNewAccount(account);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.ok("Account %s successful save".formatted(account.getName()));
    }

    private Account createAccount(AccountRegisterDTO accountRegisterDTO) {
        Account account = new Account();
        String passwordHash = BCrypt.hashpw(accountRegisterDTO.getPassword(), BCrypt.gensalt());

        account.setName(accountRegisterDTO.getUsername());
        account.setPasswordHash(passwordHash);
        account.setRole(Account.Role.valueOf(accountRegisterDTO.getRole()));
        account.setEmail(accountRegisterDTO.getEmail());
        account.setIsBlock(false);

        return account;
    }
}
