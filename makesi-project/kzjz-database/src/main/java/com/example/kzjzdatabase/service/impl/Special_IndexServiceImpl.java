package com.example.kzjzdatabase.service.impl;

import com.example.kzjzdatabase.entity.*;
import com.example.kzjzdatabase.entity.ReceiveObj.SpecialUpdateReceiveAll;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.mapper.Special_IndexMapper;
import com.example.kzjzdatabase.service.Special_IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Special_IndexServiceImpl implements Special_IndexService {

    @Autowired
    Special_IndexMapper special_indexMapper;

    @Override
    public void specialindexcreate(Special_Index special_index) {
        special_indexMapper.specialindexcreate(special_index);
    }

    @Override
    public void specialIndexUpdateAllFieds(SpecialUpdateReceiveAll specialUpdateReceiveAll){
        special_indexMapper.specialIndexUpdateAllFieds(specialUpdateReceiveAll);
    }

    @Override
    public void specialindexdeletebyofname(String ofname) {
        special_indexMapper.specialindexdeletebyofname(ofname);
    }

    @Override
    public void specialindexupdate(UpdateRecive updateRecive) {
        special_indexMapper.specialindexupdate(updateRecive);
    }

    @Override
    public void specialindexupdateids(Updateids updateids) {
        special_indexMapper.specialindexupdateids(updateids);
    }

    @Override
    public List<Updateids> getspecialindexidids() {
        return special_indexMapper.getspecialindexidids();
    }


    @Override
    public Special_Index getspecialindexbyid(String id) {
        return special_indexMapper.getspecialindexbyid(id);
    }

    @Override
    public List<Special_Index> getspecialindex(GBList gbList) {
        return special_indexMapper.getspecialindex(gbList);
    }

    @Override
    public List<Special_Index> getSpecialIndexScope(GBList_scope gbList_scope) {
        return special_indexMapper.getSpecialIndexScope(gbList_scope);
    }

    @Override
    public Updateids getSpecialElementIdAndKind(String id){
        return special_indexMapper.getSpecialElementIdAndKind(id);
    }
}
