package ru.abolsoft.core.controller.moderator;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abolsoft.core.service.AccountService;

@RestController
@RequestMapping("/api/moderator")
public class BlockUnblockController {

    @Autowired
    private AccountService accountService;


    @GetMapping("/block/{username}")
    public ResponseEntity<String> blockAccount(@PathVariable("username") String username) {

        try {
            accountService.blockAccount(username);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Account %s successfully blocked".formatted(username));
    }

    @GetMapping("/unblock/{username}")
    public ResponseEntity<String> unblockAccount(@PathVariable("username") String username) {

        try {
            accountService.unblockAccount(username);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Account %s successfully unblocked".formatted(username));
    }
}
