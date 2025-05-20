package com.example.kzjzdatabase.entity;

import lombok.Data;

import java.util.List;

@Data
public class Add2special {

    //根据介质表中数据的IDs（表间也不重复，是唯一的），决定哪些介质表中的介质加入到特色专题表
    private List<String> idList;

    //和id实体相互对应的类型字段
    private List<String> kindList;

    //待加入特色专题表中特色专题的ID
    private String indexid;

}
