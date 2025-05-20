package com.example.kzjzdatabase.service;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.Picture.Picture;
import com.example.kzjzdatabase.entity.Picture.Picture_NoUrl;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;

import java.util.List;

public interface PictureService {
    void picturecreate(Picture picture);

    void pictureupdate(UpdateRecive updateRecive);

    void picturedelete(String Id);

    List<Picture_NoUrl> getPictures_nourl(GBList gbList);

    List<Picture> getPictures(GBList gbList);

    List<Picture> getPictures_scope(GBList_scope gbList_scope);

    Picture getPictures_byid(String Id);
}
