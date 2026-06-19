package com.tuan.hotelbooking.service.impl;

import com.tuan.hotelbooking.service.interfaces.ICloudinaryService;
import com.tuan.hotelbooking.common.utils.CloudinaryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements ICloudinaryService {
    private final CloudinaryUtil cloudinaryUtil;

    @Override
    public String uploadImage(MultipartFile file) {
        cloudinaryUtil.validateImageFile(file);
        return cloudinaryUtil.uploadFile(file);
    }

    @Override
    public String updateImage(MultipartFile file, String oldUrl) {
        cloudinaryUtil.validateImageFile(file);
        return cloudinaryUtil.updateFile(file, oldUrl);
    }
}
