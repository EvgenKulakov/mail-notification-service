package ru.abolsoft.core.cloud.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
public class DownloadService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${yandex.bucket.name}")
    private String bucketName;


    public InputStreamResource downloadImage(String imageName) {

        S3Object s3object = amazonS3.getObject(bucketName, imageName);

        return new InputStreamResource(s3object.getObjectContent());
    }
}
