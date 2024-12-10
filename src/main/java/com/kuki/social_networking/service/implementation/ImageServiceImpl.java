package com.kuki.social_networking.service.implementation;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.kuki.social_networking.service.interfaces.ImageService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String BUCKET_NAME = "e-commerce-encora";

    /**
     * Uploads an image to the cloud storage.
     *
     * @param file The image file to upload.
     * @return The URL of the uploaded image.
     */
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        BlobId blobId = BlobId.of(BUCKET_NAME, randomFileName(extension));
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        Blob blob = storage().create(blobInfo, file.getBytes());

        return blob.getMediaLink();
    }

    /**
     * Generates a random file name for the image.
     *
     * @return The random file name.
     */
    public String randomFileName(String extension) {
        return UUID.randomUUID() + "_" + Timestamp.from(Instant.now()) +  extension;
    }

    @Bean
    private Storage storage() throws IOException {
        String credentials = System.getenv("GOOGLE_CLOUD_CREDENTIALS");
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                new ByteArrayInputStream(credentials.getBytes(StandardCharsets.UTF_8))
        );

        return StorageOptions.newBuilder()
                .setProjectId("e-commerce-encora")
                .setCredentials(googleCredentials)
                .build()
                .getService();
    }

    public void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Image is required");
        }

        if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
            throw new IllegalArgumentException("Invalid image file");
        }

        if (image.getSize() > 10485760) {
            throw new IllegalArgumentException("Image file is too large");
        }
    }
}
