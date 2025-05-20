package com.example.kzjzdatabase.mapper;


import com.example.kzjzdatabase.entity.*;
import com.example.kzjzdatabase.entity.ReceiveObj.SpecialUpdateReceiveAll;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Special_IndexMapper {

    void specialindexcreate(Special_Index special_index);

    void specialindexdeletebyofname(String ofname);

    void specialindexupdate(UpdateRecive updateRecive);

    void specialIndexUpdateAllFieds(SpecialUpdateReceiveAll specialUpdateReceiveAll);

    void specialindexupdateids(Updateids updateids);

    List<Updateids> getspecialindexidids();

    Special_Index getspecialindexbyid(String id);

    List<Special_Index> getspecialindex(GBList gbList);

    List<Special_Index> getSpecialIndexScope(GBList_scope gbList_scope);

    Updateids getSpecialElementIdAndKind(String id);
}
