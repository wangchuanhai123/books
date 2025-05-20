package com.example.kzjzdatabase.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Kss {

    /**
     * 主键
     */
    private String id;

    /**
     * 权限
     */
    private Integer permission;

    /**
     * 文件介质类型
     */
    private String oftype;

    /**
     * 名字
     */
    private String ofname;

    /**
     * 所属大类
     */
    private String ofkind;

    /**
     * 关键词组（以,分隔）
     */
    private String keywords;

    /**
     * 简述
     */
    private String resume;

    /**
     * 主要责任者1
     */
    private String mainresponsible1;

    /**
     * 主要责任者2
     */
    private String mainresponsible2;

    /**
     * 次要责任者
     */
    private String secondresponsible;

    /**
     * 出版者
     */
    private String publisher;

    /**
     * 出版地
     */
    private String publishedplace;

    /**
     * 出版时间
     */
    private String publishedtime;

    /**
     * 文件介质的路径
     */
    private String file_url;

    /**
     * 封面图片的路径
     */
    private String picture_url;

    /**
     * 创建时间
     */
    private String create_time;

    /**
     * 更新时间
     */
    private String update_time;

}
