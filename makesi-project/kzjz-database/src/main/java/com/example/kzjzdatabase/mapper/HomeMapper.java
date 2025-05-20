package com.example.kzjzdatabase.mapper;

import com.example.kzjzdatabase.entity.Home.Home;
import com.example.kzjzdatabase.entity.ReceiveObj.SpecialUpdateReceiveAll;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateReciveAll;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeMapper {

    void addToHome(Home home);

    List<Home> getHomeByOfKind(String ofkind);

    void deleteFromHome(String id);

    void updateHomeById(UpdateRecive updateRecive);

    void updateHomeKssIndex(UpdateReciveAll updateReciveAll);

    void updateHomeSpecialIndex(SpecialUpdateReceiveAll specialUpdateReceiveAll);
}
