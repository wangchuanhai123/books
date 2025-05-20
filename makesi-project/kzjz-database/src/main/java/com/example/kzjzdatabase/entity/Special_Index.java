package com.example.kzjzdatabase.entity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Special_Index {

    private String id;

    private Integer showflag;

    //特色专题名字
    private String ofname;

    private String resume;

    //属于该特色专题的七表内介质的id
    private String ids;

    private String picture_url;

    private String create_time;

    private String update_time;

    private String kinds;

}
