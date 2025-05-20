package com.example.kzjzdatabase.service;

import com.example.kzjzdatabase.entity.Audio.Audio;
import com.example.kzjzdatabase.entity.Audio.Audio_NoUrl;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;

import java.util.List;

public interface AudioService {

    void audiocreate(Audio audio);

    void audioupdate(UpdateRecive updateRecive);

    void audiodelete(String Id);

    List<Audio_NoUrl> getAudios_nourl(GBList gbList);

    List<Audio> getAudios(GBList gbList);

    List<Audio> getAudios_scope(GBList_scope gbList_scope);

    Audio getAudios_byid(String Id);
}
