package com.example.kzjzdatabase.service;
import com.example.kzjzdatabase.entity.File.File;
import com.example.kzjzdatabase.entity.File.File_NoUrl;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;

import java.util.List;

public interface FileService {
    void filecreate(File file);

    void fileupdate(UpdateRecive updateRecive);

    void filedelete(String Id);

    List<File_NoUrl> getFiles_nourl(GBList gbList);

    List<File> getFiles(GBList gbList);

    List<File> getFiles_scope(GBList_scope gbList_scope);

    File getFiles_byid(String Id);

    String selectidbyofname(String numberPart);

    String selectidbyimage(String filekey);
}
