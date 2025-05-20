package com.example.kzjzdatabase.entity;

import lombok.Data;

@Data
public class DeleteRecive {

    //待删除的七个介质表中介质的id
    private String id;

    //待删除的七个介质表中介质的类型
    private String oftype;

    private String file_url; //ly added

    private String picture_url;//ly added

}
