package com.example.kzjzdatabase.service;

import com.example.kzjzdatabase.entity.Home.Home;
import com.example.kzjzdatabase.entity.ReceiveObj.SpecialUpdateReceiveAll;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateReciveAll;

import java.util.List;

public interface HomeService {

    void addToHome(Home home);

    List<Home> getFromHome(String oftype);

    void deleteFromHome(String id);

    void updateHomeById(UpdateRecive updateRecive);

    void updateHomeKssIndex(UpdateReciveAll updateReciveAll);

    void updateHomeSpecialIndex(SpecialUpdateReceiveAll specialUpdateReceiveAll);
}
