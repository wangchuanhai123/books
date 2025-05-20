package com.example.kzjzdatabase.service.impl;

import com.example.kzjzdatabase.entity.Home.Home;
import com.example.kzjzdatabase.entity.ReceiveObj.SpecialUpdateReceiveAll;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateReciveAll;
import com.example.kzjzdatabase.mapper.HomeMapper;
import com.example.kzjzdatabase.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    HomeMapper homeMapper;

    @Override
    public void addToHome(Home home){
        homeMapper.addToHome(home);
    }

    @Override
    public List<Home> getFromHome(String ofkind){
        return homeMapper.getHomeByOfKind(ofkind);
    }

    @Override
    public void deleteFromHome(String id){
        homeMapper.deleteFromHome(id);
    }

    @Override
    public void updateHomeById(UpdateRecive updateRecive){
        homeMapper.updateHomeById(updateRecive);
    }

    @Override
    public void updateHomeKssIndex(UpdateReciveAll updateReciveAll){
        homeMapper.updateHomeKssIndex(updateReciveAll);
    }

    @Override
    public void updateHomeSpecialIndex(SpecialUpdateReceiveAll specialUpdateReceiveAll){
        homeMapper.updateHomeSpecialIndex(specialUpdateReceiveAll);
    }

}
