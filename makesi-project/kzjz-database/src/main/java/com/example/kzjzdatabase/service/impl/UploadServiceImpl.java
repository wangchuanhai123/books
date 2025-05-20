package com.example.kzjzdatabase.service.impl;


import com.example.kzjzdatabase.config.UploadProperties;
import com.example.kzjzdatabase.entity.Book.Book;
import com.example.kzjzdatabase.entity.FileUrl;
import com.example.kzjzdatabase.mapper.BookMapper;
import com.example.kzjzdatabase.service.UploadService;
import com.example.kzjzdatabase.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private UploadProperties uploadProperties;

    @Override
    public String upload(MultipartFile file, MultipartFile cover, FileUrl fileUrl) throws IOException {
        if(!uploadProperties.getAllowTypes().contains(file.getContentType())){
            throw new IOException("文件上传类型错误！");
        }
        String fileName = UploadUtils.generateFileName(file.getOriginalFilename());
        String filePathName =uploadProperties.getPath()+fileName;
        System.out.println(filePathName);

        String coverName = UploadUtils.generateFileName(cover.getOriginalFilename());
        String coverPathName =uploadProperties.getPath()+coverName;
        System.out.println(coverPathName);

        file.transferTo(new File(filePathName));
        cover.transferTo(new File(coverPathName));

        fileUrl.setFileUrl(filePathName.substring(1));
        fileUrl.setCoverUrl(coverPathName.substring(1));//去掉"E:/"
        return fileName;
    }

    @Override
    public String uploadcover(MultipartFile cover, FileUrl fileUrl) throws Exception {

        String coverName = UploadUtils.generateFileName(cover.getOriginalFilename());
        String coverPathName =uploadProperties.getPath()+coverName;

        cover.transferTo(new File(coverPathName));

        fileUrl.setFileUrl("");
        fileUrl.setCoverUrl(coverPathName.substring(3));//去掉"E:/"
        return coverName;
    }

    //ly added new func
    @Override
    public boolean deleteFile(String filePath){
        filePath = '/' + filePath; //加上”E:/"
        //System.out.println(filePath);
        File f = new File(filePath);
        if(f.exists()){
            if(f.delete())
                return true;
            else
                return false;
        }else
            return false;
    }
}