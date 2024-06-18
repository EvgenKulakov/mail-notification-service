package ru.abolsoft.core.controller.user;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abolsoft.common.kafka.dto.MessageToSend;
import ru.abolsoft.core.entity.Account;
import ru.abolsoft.core.service.kafka.KafkaProducer;
import ru.abolsoft.core.service.kafka.MessageFactory;
import ru.abolsoft.core.service.persist.AccountService;
import ru.abolsoft.core.service.cloud.DownloadService;
import ru.abolsoft.core.service.persist.ImageMetadataService;

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
    @Autowired
    private MessageFactory messageFactory;
    @Autowired
    private KafkaProducer kafkaProducer;


    @GetMapping("/download/{imageName}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable("imageName") String imageName,
                                                             Principal principal) {

        Account currentAccount = accountService.getAccountByLogin(principal.getName());
        if (!imageMetadataService.existImageByAccount(imageName, currentAccount.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        S3Object s3Object = downloadService.downloadImage(imageName);
        InputStreamResource resource = new InputStreamResource(s3Object.getObjectContent());
        long sizeImage = s3Object.getObjectMetadata().getContentLength();

        MessageToSend messageToSend = messageFactory.downloadMessage(currentAccount, imageName, sizeImage);
        kafkaProducer.publicMessage(messageToSend);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + imageName)
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(sizeImage)
                .body(resource);
    }
}