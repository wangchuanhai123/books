package com.example.kzjzdatabase.util;

import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UrlUtil {
    @Autowired
    BookMapper bookMapper;
    public String getUrl(String name){
        GBList gbList = GBLUtil.getGBList(name);
        System.out.println(bookMapper.getBooks(gbList));
        String pathName = bookMapper.getBooks(gbList).get(0).getFile_url();
        //System.out.println(pathName);
        return pathName;
    }
}
