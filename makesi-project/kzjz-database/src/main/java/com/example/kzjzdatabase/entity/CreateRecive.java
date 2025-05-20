package com.example.kzjzdatabase.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateRecive {

    /**
     * 文件介质类型
     */
    private String oftype;

    /**
     * 权限
     */
    private Integer permission;

    /**
     * 名字
     */
    private String ofname;

    /**
     * 所属大类 (文献资料、学术研究)
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

}
