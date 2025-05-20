package com.example.kzjzdatabase.service;

import com.example.kzjzdatabase.entity.Book.Book;
import com.example.kzjzdatabase.entity.FileUrl;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    String upload(MultipartFile file, MultipartFile cover, FileUrl fileUrl) throws Exception;

    String uploadcover(MultipartFile cover, FileUrl fileUrl) throws Exception;

    boolean deleteFile(String filePath);
}