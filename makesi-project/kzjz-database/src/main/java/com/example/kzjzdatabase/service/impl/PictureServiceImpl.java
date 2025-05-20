package com.example.kzjzdatabase.service.impl;

import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.Picture.Picture;
import com.example.kzjzdatabase.entity.Picture.Picture_NoUrl;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.mapper.PictureMapper;
import com.example.kzjzdatabase.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    PictureMapper pictureMapper;

    @Override
    public void picturecreate(Picture picture) {
        pictureMapper.picturecreate(picture);
    }

    @Override
    public void pictureupdate(UpdateRecive updateRecive) {
        pictureMapper.pictureupdate(updateRecive);
    }

    @Override
    public void picturedelete(String Id) {
        pictureMapper.picturedelete(Id);
    }

    @Override
    public List<Picture_NoUrl> getPictures_nourl(GBList gbList) {
        List<Picture_NoUrl> picture_noUrlList= pictureMapper.getPictures_nourl(gbList);
        return picture_noUrlList;
    }

    @Override
    public List<Picture> getPictures(GBList gbList) {
        List<Picture> pictureList= pictureMapper.getPictures(gbList);
        return pictureList;
    }

    @Override
    public List<Picture> getPictures_scope(GBList_scope gbList_scope) {
        List<Picture> pictureList= pictureMapper.getPictures_scope(gbList_scope);
        return pictureList;
    }

    @Override
    public Picture getPictures_byid(String Id) {
        return pictureMapper.getPictures_byid(Id);
    }
}
