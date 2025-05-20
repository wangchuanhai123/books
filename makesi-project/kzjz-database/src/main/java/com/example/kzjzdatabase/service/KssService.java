package com.example.kzjzdatabase.service;

import com.example.kzjzdatabase.entity.*;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;

import java.util.List;

public interface KssService {

    void ksscreate(Kss kss);

    void kssupdate(UpdateRecive updateRecive);

    void kssupdateofkind(Updateofkind updateofkind);

    void kssdeletebyid(String id);

    void kssdeletebyofkind(String ofkind);

    Kss getkssbyid(String id);

    List<Kss> getkssbyofkind(String ofkind);

    List<Kss> getKsss(GBList gbList);

    List<Kss> getKsss_scope(GBList_scope gbList_scope);

    List<All_NoUrl> getkss_nourl(GBList gbList);

}
