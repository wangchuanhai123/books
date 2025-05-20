package com.example.kzjzdatabase.service.impl;

import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.Newspaper.Newspaper;
import com.example.kzjzdatabase.entity.Newspaper.Newspaper_NoUrl;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.mapper.NewspaperMapper;
import com.example.kzjzdatabase.service.NewspaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewspaperServiceImpl implements NewspaperService {

    @Autowired
    NewspaperMapper newspaperMapper;

    @Override
    public void newspapercreate(Newspaper newspaper) {
        newspaperMapper.newspapercreate(newspaper);
    }

    @Override
    public void newspaperupdate(UpdateRecive updateRecive) {
        newspaperMapper.newspaperupdate(updateRecive);
    }

    @Override
    public void newspaperdelete(String Id) {
        newspaperMapper.newspaperdelete(Id);
    }

    @Override
    public List<Newspaper_NoUrl> getNewspapers_nourl(GBList gbList) {
        List<Newspaper_NoUrl> newspaper_noUrlList= newspaperMapper.getNewspapers_nourl(gbList);
        return newspaper_noUrlList;
    }

    @Override
    public List<Newspaper> getNewspapers(GBList gbList) {
        List<Newspaper> newspaperList= newspaperMapper.getNewspapers(gbList);
        return newspaperList;
    }

    @Override
    public List<Newspaper> getNewspapers_scope(GBList_scope gbList_scope) {
        List<Newspaper> newspaperList= newspaperMapper.getNewspapers_scope(gbList_scope);
        return newspaperList;
    }

    @Override
    public Newspaper getNewspapers_byid(String Id) {
        return newspaperMapper.getNewspapers_byid(Id);
    }


}
