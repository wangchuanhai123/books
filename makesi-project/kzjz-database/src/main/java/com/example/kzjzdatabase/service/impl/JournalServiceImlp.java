package com.example.kzjzdatabase.service.impl;

import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.Journal.Journal;
import com.example.kzjzdatabase.entity.Journal.Journal_NoUrl;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.mapper.JournalMapper;
import com.example.kzjzdatabase.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalServiceImlp implements JournalService {

    @Autowired
    JournalMapper journalMapper;

    @Override
    public void journalcreate(Journal journal) {
        journalMapper.journalcreate(journal);
    }

    @Override
    public void journalupdate(UpdateRecive updateRecive) {
        journalMapper.journalupdate(updateRecive);
    }

    @Override
    public void journaldelete(String Id) {
        journalMapper.journaldelete(Id);
    }

    @Override
    public List<Journal_NoUrl> getJournals_nourl(GBList gbList) {
        List<Journal_NoUrl> journal_noUrlList= journalMapper.getJournals_nourl(gbList);
        return journal_noUrlList;
    }

    @Override
    public List<Journal> getJournals(GBList gbList) {
        List<Journal> journalList= journalMapper.getJournals(gbList);
        return journalList;
    }

    @Override
    public List<Journal> getJournals_scope(GBList_scope gbList_scope) {
        List<Journal> journalList= journalMapper.getJournals_scope(gbList_scope);
        return journalList;
    }

    @Override
    public Journal getJournals_byid(String Id) {
        return journalMapper.getJournals_byid(Id);
    }
}
