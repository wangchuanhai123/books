package com.example.kzjzdatabase.entity.RetType;

import com.example.kzjzdatabase.entity.Home.Home;
import lombok.Data;

import java.util.List;

@Data
public class GetAllHomeRet {

    public List<Home> homeList;

    public List<Integer> homeListLen;


}
