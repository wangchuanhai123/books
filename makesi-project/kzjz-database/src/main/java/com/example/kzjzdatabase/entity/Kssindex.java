package com.example.kzjzdatabase.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Kssindex {

    private String id;

    private Integer showflag;

    //单条口述史的名字，与Kss中ofkind一一对应
    private String ofname;

    private String notes;

    private String resume;

    private String picture_url;

    private String create_time;

    private String update_time;

}
