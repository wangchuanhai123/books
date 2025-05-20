package com.example.kzjzdatabase.mapper;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.Picture.Picture;
import com.example.kzjzdatabase.entity.Picture.Picture_NoUrl;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureMapper {
    void picturecreate(Picture picture);

    void pictureupdate(UpdateRecive updateRecive);

    void picturedelete(String id);

    List<Picture_NoUrl> getPictures_nourl(GBList gbList);

    List<Picture> getPictures(GBList gbList);

    List<Picture> getPictures_scope(GBList_scope gbList_scope);

    Picture getPictures_byid(String Id);

}
