package ru.abolsoft.core.controller.user;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.abolsoft.core.entity.Account;
import ru.abolsoft.core.service.persist.AccountService;
import ru.abolsoft.core.service.cloud.UploadService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UploadController {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private AccountService accountService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files,
                                              Principal principal) {

        Account currentAccount = accountService.getAccountByLogin(principal.getName());

        try {
            uploadService.uploadFiles(files, currentAccount);
        } catch (ValidationException VE) {
            return ResponseEntity.badRequest().body(VE.getMessage());
        } catch (IOException IOE) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IOE.getMessage());
        }


        return ResponseEntity.ok("Files uploaded successfully");
    }
}
