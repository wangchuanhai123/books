package com.example.kzjzdatabase.mapper;


import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.Journal.Journal;
import com.example.kzjzdatabase.entity.Journal.Journal_NoUrl;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalMapper {
    void journalcreate(Journal journal);

    void journalupdate(UpdateRecive updateRecive);

    void journaldelete(String id);

    List<Journal_NoUrl> getJournals_nourl(GBList gbList);

    List<Journal> getJournals(GBList gbList);

    List<Journal> getJournals_scope(GBList_scope gbList_scope);

    Journal getJournals_byid(String Id);
}
