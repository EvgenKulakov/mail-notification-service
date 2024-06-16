package ru.abolsoft.mail.test.email.sand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class EmailTestController {

    @Autowired
    private EmailTestService emailTestService;

    @GetMapping("/email{to}")
    public ResponseEntity<String> sandEmail(@PathVariable("to") String to) {
        String subject = "Test message";
        String text = "Hello";

        emailTestService.sendSimpleMessage(to, subject, text);

        return ResponseEntity.ok().body("success");
    }
}
