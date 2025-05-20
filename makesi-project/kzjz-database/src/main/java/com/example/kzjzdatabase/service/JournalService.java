package com.example.kzjzdatabase.service;

import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.Journal.Journal;
import com.example.kzjzdatabase.entity.Journal.Journal_NoUrl;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;

import java.util.List;

public interface JournalService {
    void journalcreate(Journal journal);

    void journalupdate(UpdateRecive updateRecive);

    void journaldelete(String Id);

    List<Journal_NoUrl> getJournals_nourl(GBList gbList);

    List<Journal> getJournals(GBList gbList);

    List<Journal> getJournals_scope(GBList_scope gbList_scope);

    Journal getJournals_byid(String Id);
}
