package com.example.kzjzdatabase.entity.ReceiveObj;

import com.example.kzjzdatabase.entity.Home.Home;
import lombok.Data;

import java.util.List;

@Data
public class HomeDeleteReceive {

    private List<Home> newIndexElem;

    private List<Home> offIndexElem;
}
