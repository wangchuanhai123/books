package com.example.kzjzdatabase.service.impl;


import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.Kssindex;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateReciveAll;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.mapper.KssindexMapper;
import com.example.kzjzdatabase.service.KssindexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KssindexServiceImpl implements KssindexService {

    @Autowired
    KssindexMapper kssindexMapper;

    @Override
    public void kssindexcreate(Kssindex kssindex) {
        kssindexMapper.kssindexcreate(kssindex);
    }

    @Override
    public void kssindexdeletebyofname(String ofname) {
        kssindexMapper.kssindexdeletebyofname(ofname);
    }

    @Override
    public void kssindexupdate(UpdateRecive updateRecive) {
        kssindexMapper.kssindexupdate(updateRecive);
    }

    @Override
    public Kssindex getkssindexbyid(String id) {
        return kssindexMapper.getkssindexbyid(id);
    }

    @Override
    public List<Kssindex> getkssindex(GBList gbList) {
        return kssindexMapper.getkssindex(gbList);
    }

    @Override
    public List<Kssindex> getKssIndex_scope(GBList_scope gbList_scope){ return kssindexMapper.getKssIndex_scope(gbList_scope); };

    @Override
    public void kssindexUpdateAllFieds(UpdateReciveAll updateReciveAll){
        kssindexMapper.kssindexUpdateAllFieds(updateReciveAll);
    }
}
