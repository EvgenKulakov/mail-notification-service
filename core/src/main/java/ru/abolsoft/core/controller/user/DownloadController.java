package ru.abolsoft.core.controller.user;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abolsoft.core.entity.Account;
import ru.abolsoft.core.service.AccountService;
import ru.abolsoft.core.service.DownloadService;
import ru.abolsoft.core.service.ImageMetadataService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;
    @Autowired
    private ImageMetadataService imageMetadataService;
    @Autowired
    private AccountService accountService;


    @GetMapping("/download/{imageName}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable("imageName") String imageName,
                                                             Principal principal) {

        Account currentAccount = accountService.getAccountByLogin(principal.getName());
        if (!imageMetadataService.existImageByAccount(imageName, currentAccount.getId())) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build();
        }

        InputStreamResource resource = downloadService.downloadImage(imageName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + imageName)
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}