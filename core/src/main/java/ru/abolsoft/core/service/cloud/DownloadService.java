package ru.abolsoft.core.service.cloud;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DownloadService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${yandex.bucket.name}")
    private String bucketName;


    public S3Object downloadImage(String imageName) {

        S3Object s3object = amazonS3.getObject(bucketName, imageName);

        return s3object;
    }
}
