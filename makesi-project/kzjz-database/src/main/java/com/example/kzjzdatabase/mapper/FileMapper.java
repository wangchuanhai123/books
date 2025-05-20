package com.example.kzjzdatabase.mapper;

import com.example.kzjzdatabase.entity.File.File;
import com.example.kzjzdatabase.entity.File.File_NoUrl;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileMapper {
    void filecreate(File file);

    void fileupdate(UpdateRecive updateRecive);

    void filedelete(String id);

    List<File_NoUrl> getFiles_nourl(GBList gbList);

    List<File> getFiles(GBList gbList);

    List<File> getFiles_scope(GBList_scope gbList_scope);

    File getFiles_byid(String Id);

    String getidbyofname(String Name);

    String getimageurl(String filekey);
}
