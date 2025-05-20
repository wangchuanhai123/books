package com.example.kzjzdatabase.entity;


import lombok.Data;

import java.util.List;

@Data
public class Home_Search_Return {

    private Integer sum;

    private List<Integer> ofyearList;

    private List<Integer> ofnumberList;

    private List<All_max> all_maxList;

}
