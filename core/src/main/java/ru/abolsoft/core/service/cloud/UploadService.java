package ru.abolsoft.core.service.cloud;

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
import ru.abolsoft.common.kafka.dto.MessageToSend;
import ru.abolsoft.core.entity.Account;
import ru.abolsoft.core.entity.ImageMetadata;
import ru.abolsoft.core.service.kafka.KafkaProducer;
import ru.abolsoft.core.service.kafka.MessageFactory;
import ru.abolsoft.core.service.persist.ImageMetadataService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UploadService {

    @Autowired
    private ImageMetadataService imageMetadataService;
    @Autowired
    private MessageFactory messageFactory;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${yandex.bucket.name}")
    private String bucketName;


    @Transactional
    public void uploadFiles(MultipartFile[] files, Account currentAccount) throws IOException {

        validationFiles(files);

        for (MultipartFile file : files) {

            try {
                ImageMetadata metadata = createImageMetadata(file, currentAccount.getId());
                amazonS3.putObject(new PutObjectRequest(
                        bucketName, metadata.getName(), file.getInputStream(), new ObjectMetadata()
                ));
                imageMetadataService.save(metadata);
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new IOException("Error uploading file %s".formatted(file.getOriginalFilename()));
            }
        }

        MessageToSend messageToSend = messageFactory.uploadMessage(currentAccount, files);
        kafkaProducer.publicMessage(messageToSend);
    }

    private void validationFiles(MultipartFile[] files) {
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            if (!Objects.equals(contentType, "image/jpeg") && !Objects.equals(contentType, "image/png")) {
                throw new ValidationException("Invalid file format: %s".formatted(file.getOriginalFilename()));
            }
        }
    }

    private ImageMetadata createImageMetadata(MultipartFile file, Long currentAccountId) {

        String uniqueName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        ImageMetadata metadata = new ImageMetadata();
        metadata.setName(uniqueName);
        metadata.setUploadDate(LocalDate.now());
        metadata.setSize(file.getSize());
        metadata.setAccountId(currentAccountId);

        return metadata;
    }
}
