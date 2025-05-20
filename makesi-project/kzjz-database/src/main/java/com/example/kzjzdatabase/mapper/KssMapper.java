package com.example.kzjzdatabase.mapper;


import com.example.kzjzdatabase.entity.*;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KssMapper {

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
