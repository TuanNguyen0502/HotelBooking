package com.tuan.hotelbooking.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {
    String uploadImage(MultipartFile file);

    String updateImage(MultipartFile file, String oldUrl);
}