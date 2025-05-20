package com.example.kzjzdatabase.service.impl;

import com.example.kzjzdatabase.entity.*;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.mapper.KssMapper;
import com.example.kzjzdatabase.service.KssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KssServiceImpl implements KssService {

    @Autowired
    KssMapper kssMapper;

    @Override
    public void ksscreate(Kss kss) {
        kssMapper.ksscreate(kss);
    }

    @Override
    public void kssupdate(UpdateRecive updateRecive) {
        kssMapper.kssupdate(updateRecive);
    }

    @Override
    public void kssupdateofkind(Updateofkind updateofkind) {
        kssMapper.kssupdateofkind(updateofkind);
    }

    @Override
    public void kssdeletebyid(String id) {
        kssMapper.kssdeletebyid(id);
    }

    @Override
    public void kssdeletebyofkind(String ofkind) {
        kssMapper.kssdeletebyofkind(ofkind);
    }

    @Override
    public Kss getkssbyid(String id) {
        return kssMapper.getkssbyid(id);
    }

    @Override
    public List<Kss> getkssbyofkind(String ofkind) {
        return kssMapper.getkssbyofkind(ofkind);
    }

    @Override
    public List<Kss> getKsss(GBList gbList) {
        return kssMapper.getKsss(gbList);
    }

    @Override
    public List<Kss> getKsss_scope(GBList_scope gbList_scope) {
        return kssMapper.getKsss_scope(gbList_scope);
    }

    @Override
    public List<All_NoUrl> getkss_nourl(GBList gbList) {
        return kssMapper.getkss_nourl(gbList);
    }

}
