package com.example.kzjzdatabase.service.impl;

import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.Video.Video;
import com.example.kzjzdatabase.entity.Video.Video_NoUrl;
import com.example.kzjzdatabase.mapper.VideoMapper;
import com.example.kzjzdatabase.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;

    @Override
    public void videocreate(Video video) {
        videoMapper.videocreate(video);
    }

    @Override
    public void videoupdate(UpdateRecive updateRecive) {
        videoMapper.videoupdate(updateRecive);
    }

    @Override
    public void videodelete(String Id) {
        videoMapper.videodelete(Id);
    }

    @Override
    public List<Video_NoUrl> getVideos_nourl(GBList gbList) {
        List<Video_NoUrl> video_noUrlList= videoMapper.getVideos_nourl(gbList);
        return video_noUrlList;
    }

    @Override
    public List<Video> getVideos(GBList gbList) {
        List<Video> videoList= videoMapper.getVideos(gbList);
        return videoList;
    }

    @Override
    public List<Video> getVideos_scope(GBList_scope gbList_scope) {
        List<Video> videoList= videoMapper.getVideos_scope(gbList_scope);
        return videoList;
    }

    @Override
    public Video getVideos_byid(String Id) {
        return videoMapper.getVideos_byid(Id);
    }
}
