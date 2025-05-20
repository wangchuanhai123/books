package com.example.kzjzdatabase.service;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    /**
     * 查询所有用户列表
     */
    List<User> findAll();

    /**
     * 添加用户
     */
    void createUser(User user);

    /**
     * 修改用户信息
     */
    void updateUser(UpdateRecive updateRecive);

    /**
     * 删除用户
     */
    void deleteUser(String id);

    int checkUser(User user);

    int loginUser(User user);

    int getuserid(String account);

    String getphonenumber(String name);

    String findUserName(@Valid String username);

    String findPhoneNumber(String username);

    String findEmail(String username);
}

