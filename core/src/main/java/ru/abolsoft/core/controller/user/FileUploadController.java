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
import ru.abolsoft.core.cloud.service.CloudService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private CloudService cloudService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files) {

        try {
            cloudService.uploadFiles(files);
        } catch (ValidationException VE) {
            return ResponseEntity.badRequest().body(VE.getMessage());
        } catch (IOException IOE) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IOE.getMessage());
        }

        //TODO: send to kafka
        return ResponseEntity.ok("Files uploaded successfully");
    }
}
