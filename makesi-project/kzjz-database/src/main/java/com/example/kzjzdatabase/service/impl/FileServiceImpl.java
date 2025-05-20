package com.example.kzjzdatabase.service.impl;

import com.example.kzjzdatabase.entity.File.File;
import com.example.kzjzdatabase.entity.File.File_NoUrl;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.mapper.FileMapper;
import com.example.kzjzdatabase.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Override
    public void filecreate(File file) {
        fileMapper.filecreate(file);
    }

    @Override
    public void fileupdate(UpdateRecive updateRecive) {
        fileMapper.fileupdate(updateRecive);
    }

    @Override
    public void filedelete(String Id) {
        fileMapper.filedelete(Id);
    }

    @Override
    public List<File_NoUrl> getFiles_nourl(GBList gbList) {
        List<File_NoUrl> file_noUrlList= fileMapper.getFiles_nourl(gbList);
        return file_noUrlList;
    }

    @Override
    public List<File> getFiles(GBList gbList) {
        List<File> fileList= fileMapper.getFiles(gbList);
        return fileList;
    }

    @Override
    public List<File> getFiles_scope(GBList_scope gbList_scope) {
        List<File> fileList= fileMapper.getFiles_scope(gbList_scope);
        return fileList;
    }

    @Override
    public File getFiles_byid(String Id) {
        return fileMapper.getFiles_byid(Id);
    }

    @Override
    public String selectidbyofname(String numberPart) {
        return fileMapper.getidbyofname(numberPart);
    }

    @Override
    public String selectidbyimage(String filekey) {
        System.out.println("hellow" +filekey);
        return fileMapper.getimageurl(filekey);
    }

}
