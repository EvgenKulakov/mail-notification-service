package ru.abolsoft.core.cloud.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.abolsoft.core.cloud.entity.ImageMetadata;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UploadService {

    @Autowired
    MetadataService metadataService;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${yandex.bucket.name}")
    private String bucketName;


    @Transactional
    public void uploadFiles(MultipartFile[] files) throws IOException {

        validationFiles(files);

        for (MultipartFile file : files) {

            try {
                ImageMetadata metadata = createImageMetadata(file);
                amazonS3.putObject(new PutObjectRequest(
                        bucketName, metadata.getName(), file.getInputStream(), new ObjectMetadata()
                ));
                metadataService.save(metadata);
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

    private ImageMetadata createImageMetadata(MultipartFile file) {

        String uniqueName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        ImageMetadata metadata = new ImageMetadata();
        metadata.setName(uniqueName);
        metadata.setUploadDate(LocalDate.now());
        metadata.setSize(file.getSize());
        metadata.setAccountId(1L);

        return metadata;
    }
}
