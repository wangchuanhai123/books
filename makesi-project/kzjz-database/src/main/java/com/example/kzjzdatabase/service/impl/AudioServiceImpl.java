package com.example.kzjzdatabase.service.impl;

import com.example.kzjzdatabase.entity.Audio.Audio;
import com.example.kzjzdatabase.entity.Audio.Audio_NoUrl;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.mapper.AudioMapper;
import com.example.kzjzdatabase.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudioServiceImpl implements AudioService {

    @Autowired
    AudioMapper audioMapper;

    @Override
    public void audiocreate(Audio audio) {
        audioMapper.audiocreate(audio);
    }

    @Override
    public void audioupdate(UpdateRecive updateRecive) {
        audioMapper.audioupdate(updateRecive);
    }

    @Override
    public void audiodelete(String Id) {
        audioMapper.audiodelete(Id);
    }

    @Override
    public List<Audio_NoUrl> getAudios_nourl(GBList gbList) {
        List<Audio_NoUrl> audio_noUrlList= audioMapper.getAudios_nourl(gbList);
        return audio_noUrlList;
    }

    @Override
    public List<Audio> getAudios(GBList gbList) {
        List<Audio> audioList= audioMapper.getAudios(gbList);
        return audioList;
    }


    @Override
    public List<Audio> getAudios_scope(GBList_scope gbList_scope) {
        List<Audio> audioList= audioMapper.getAudios_scope(gbList_scope);
        return audioList;
    }

    @Override
    public Audio getAudios_byid(String Id) {
        return audioMapper.getAudios_byid(Id);
    }
}
