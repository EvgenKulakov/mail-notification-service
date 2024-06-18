package ru.abolsoft.core.controller.moderator;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abolsoft.core.service.persist.AccountService;

@RestController
@RequestMapping("/api/moderator")
public class BlockUnblockController {

    @Autowired
    private AccountService accountService;


    @PatchMapping("/block/{username}")
    public ResponseEntity<String> blockAccount(@PathVariable("username") String username) {

        try {
            accountService.blockAccount(username);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Account %s successfully blocked".formatted(username));
    }

    @PatchMapping("/unblock/{username}")
    public ResponseEntity<String> unblockAccount(@PathVariable("username") String username) {

        try {
            accountService.unblockAccount(username);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Account %s successfully unblocked".formatted(username));
    }
}
