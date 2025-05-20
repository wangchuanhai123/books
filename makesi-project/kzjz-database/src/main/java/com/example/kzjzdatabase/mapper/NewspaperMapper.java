package com.example.kzjzdatabase.mapper;

import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.Newspaper.Newspaper;
import com.example.kzjzdatabase.entity.Newspaper.Newspaper_NoUrl;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NewspaperMapper {

    void newspapercreate(Newspaper newspaper);

    void newspaperupdate(UpdateRecive updateRecive);

    void newspaperdelete(String id);

    List<Newspaper_NoUrl> getNewspapers_nourl(GBList gbList);

    List<Newspaper> getNewspapers(GBList gbList);

    List<Newspaper> getNewspapers_scope(GBList_scope gbList_scope);

    Newspaper getNewspapers_byid(String Id);
}
