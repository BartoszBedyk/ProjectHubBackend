package com.sensilabs.projecthub;

import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
    String save(MultipartFile file);
    byte[] get(String path);
}
