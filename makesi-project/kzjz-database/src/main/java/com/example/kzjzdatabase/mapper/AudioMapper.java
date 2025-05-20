package com.example.kzjzdatabase.mapper;

import com.example.kzjzdatabase.entity.Audio.Audio;
import com.example.kzjzdatabase.entity.Audio.Audio_NoUrl;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudioMapper {
    void audiocreate(Audio audio);

    void audioupdate(UpdateRecive updateRecive);

    void audiodelete(String id);

    List<Audio_NoUrl> getAudios_nourl(GBList gbList);

    List<Audio> getAudios(GBList gbList);

    List<Audio> getAudios_scope(GBList_scope gbList_scope);

    Audio getAudios_byid(String Id);
}
