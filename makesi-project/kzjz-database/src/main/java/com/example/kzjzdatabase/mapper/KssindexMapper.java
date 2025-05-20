package com.example.kzjzdatabase.mapper;

import com.example.kzjzdatabase.entity.*;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateReciveAll;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KssindexMapper {

    void kssindexcreate(Kssindex kssindex);

//    void index1createTSZT(Kssindex kssindex);
//
//    void index1deleteKSS(String ofname);
//
    void kssindexdeletebyofname(String ofname);

    void kssindexupdate(UpdateRecive updateRecive);

    void kssindexUpdateAllFieds(UpdateReciveAll updateReciveAll);

    Kssindex getkssindexbyid(String id);

    List<Kssindex> getkssindex(GBList gbList);

    List<Kssindex> getKssIndex_scope(GBList_scope gbList_scope);
//
//    List<Kssindex> getIndex1_allKSS(GBList gbList);
//
//    List<Kssindex> getIndex1_allTSZT(GBList gbList);

}
