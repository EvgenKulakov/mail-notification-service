package ru.abolsoft.core.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abolsoft.core.service.DownloadService;

@RestController
@RequestMapping("/api")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;


    @GetMapping("/download/{imageName}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable("imageName") String imageName) {

        InputStreamResource resource = downloadService.downloadImage(imageName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + imageName)
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}