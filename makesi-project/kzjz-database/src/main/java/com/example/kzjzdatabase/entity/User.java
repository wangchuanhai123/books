package com.example.kzjzdatabase.entity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Accessors(chain = true)
public class User {

    private boolean valid;

    /**
     * 主键id
     */
    private String userid;

    private String account;


    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 半公开文件查阅权限的截止时间
     */
    private LocalDateTime permissiontime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;
    private String phonenumber;
    private String captcha;

//    /**
//     * 昵称
//     */
//    private String nickname;

    /**
     * 创建时间
     */
    private String create_time;

    /**
     * 更新时间
     */
    private String update_time;

    private String role;

    public User() {
        this.valid = false;
    }


}
