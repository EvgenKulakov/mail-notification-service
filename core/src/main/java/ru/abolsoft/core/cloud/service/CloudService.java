package ru.abolsoft.core.cloud.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class CloudService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${yandex.bucket.name}")
    private String bucketName;

    public void uploadFiles(MultipartFile[] files) throws IOException {

        validationFiles(files);

        for (MultipartFile file : files) {

            try {
                String key = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
                amazonS3.putObject(new PutObjectRequest(bucketName, key, file.getInputStream(), new ObjectMetadata()));
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new IOException("Error uploading file %s".formatted(file.getOriginalFilename()));
            }
        }
    }

    private void validationFiles(MultipartFile[] files) {
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            if (!Objects.equals(contentType, "image/jpeg") && !Objects.equals(contentType, "image/png")) {
                throw new ValidationException("Invalid file format: %s".formatted(file.getOriginalFilename()));
            }
        }
    }
}
