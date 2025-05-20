package com.example.kzjzdatabase.service;

import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.Video.Video;
import com.example.kzjzdatabase.entity.Video.Video_NoUrl;

import java.util.List;

public interface VideoService {
    void videocreate(Video video);

    void videoupdate(UpdateRecive updateRecive);

    void videodelete(String Id);

    List<Video_NoUrl> getVideos_nourl(GBList gbList);

    List<Video> getVideos(GBList gbList);

    List<Video> getVideos_scope(GBList_scope gbList_scope);

    Video getVideos_byid(String Id);
}
