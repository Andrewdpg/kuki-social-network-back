package com.kuki.social_networking.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    /**
     * Uploads an image to the cloud storage.
     *
     * @param file The image file to upload.
     * @return The URL of the uploaded image.
     */
    String uploadImage(MultipartFile file) throws IOException;

    void validateImage(MultipartFile image);

}
