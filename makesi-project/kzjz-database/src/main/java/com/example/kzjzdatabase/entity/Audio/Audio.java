package com.example.kzjzdatabase.entity.Audio;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Audio {
    /**
     * 主键
     */
    private String id;

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

//    /**
//     * 版本
//     */
//    private String version;

//    /**
//     * 页数
//     */
//    private String page;
//
//    /**
//     * 从编提名
//     */
//    private String compilingnominate;
//
//    /**
//     * 从编分册
//     */
//    private String compilingfascicle;
//
//    /**
//     * 丛编分册提名
//     */
//    private String fasciclenominate;
//
//    /**
//     * 语种
//     */
//    private String language;
//
//    /**
//     * 附注项
//     */
//    private String notearea;

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
//    private LocalDateTime update_time;
}
