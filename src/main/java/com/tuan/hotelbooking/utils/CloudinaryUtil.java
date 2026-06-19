package com.tuan.hotelbooking.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tuan.hotelbooking.exception.OurException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryUtil {
    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryUtil(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file) {
        try {
            Map data = cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            return data.get("secure_url").toString();
        } catch (IOException e) {
            throw new OurException("File upload failed: " + e.getMessage());
        }
    }

    public String updateFile(MultipartFile file, String oldUrl) {
        deleteFile(oldUrl);
        return uploadFile(file);
    }

    public void deleteFile(String url) {
        try {
            String publicId = extractPublicId(url);
            String resourceType = getResourceTypeFromUrl(url);
            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", resourceType));
        } catch (IOException e) {
            throw new OurException("File deletion failed: " + e.getMessage());
        }
    }

    public void validateImageFile(MultipartFile image) {
        String filename = image.getOriginalFilename();
        if (filename == null || !isValidSuffixImage(filename)) {
            throw new OurException("Invalid image file type. Allowed types: .jpg, .jpeg, .png, .gif, .bmp, .webp");
        }
    }

    private boolean isValidSuffixImage(String img) {
        img = img.toLowerCase();
        return img.endsWith(".jpg") || img.endsWith(".jpeg") ||
                img.endsWith(".png") || img.endsWith(".gif") ||
                img.endsWith(".bmp") || img.endsWith(".webp");
    }

    private String getResourceType(String filename) {
        if (filename == null) return "raw";
        if (isValidSuffixImage(filename)) return "image";
        return "raw";
    }

    private String getResourceTypeFromUrl(String url) {
        if (url == null) return "raw";
        if (url.contains("/image/")) return "image";
        if (url.contains("/audio/")) return "audio";
        if (url.contains("/video/")) return "video";
        if (url.contains("/raw/")) return "raw";
        // Fallback based on file extension
        return getResourceType(url);
    }

    private String extractPublicId(String url) {
        if (url == null || url.isEmpty()) return null;
        // URL: https://res.cloudinary.com/your_cloud/image/upload/v1234567890/filename.jpg

        String[] parts = url.split("/");
        if (parts.length < 2) return null; // Invalid URL

        String filename = parts[parts.length - 1]; // filename.jpg
        // Remove file extension
        return filename.contains(".") ? filename.split("\\.")[0] : filename;
    }
}
