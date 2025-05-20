package com.example.kzjzdatabase.service;

import com.example.kzjzdatabase.entity.*;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateReciveAll;

import java.util.List;

public interface KssindexService {

    void kssindexcreate(Kssindex kssindex);

    void kssindexdeletebyofname(String ofname);

    void kssindexupdate(UpdateRecive updateRecive);

    void kssindexUpdateAllFieds(UpdateReciveAll updateReciveAll);

    Kssindex getkssindexbyid(String id);

    List<Kssindex> getkssindex(GBList gbList);

    List<Kssindex> getKssIndex_scope(GBList_scope gbList_scope);
}
