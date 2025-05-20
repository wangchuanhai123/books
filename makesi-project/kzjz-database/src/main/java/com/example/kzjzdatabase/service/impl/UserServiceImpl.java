package com.example.kzjzdatabase.service.impl;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.User;
import com.example.kzjzdatabase.mapper.UserMapper;
import com.example.kzjzdatabase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;


    @Override
    public List<User> findAll() {
        List<User> userList = userMapper.findAll();
        return userList;
    }

    @Override
    public void createUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(UpdateRecive updateRecive) {
        userMapper.update(updateRecive);
    }

    @Override
    public void deleteUser(String id) {
        userMapper.delete(id);
    }

    @Override
    public int checkUser(User user) {
        String account = user.getAccount();
        if (userMapper.checkuserbyaccount(account))
        {
            return 1;
        }
        String username = user.getUsername();
        if (userMapper.checkuserbyname(username))
        {
            return 2;
        }
        String phonenumber = user.getPhonenumber();
        if (userMapper.checkuserbyphonenumber(phonenumber))
        {
            return 3;
        }
        String email = user.getEmail();
        if (userMapper.checkuserbyemail(email)){
            return 4;
        }
        return 0;
    }

    @Override
    public int loginUser(User user) {
        if (!userMapper.checkuserbyaccount(user.getAccount()))
        {
            return 0;
        }
        String userpassword = userMapper.loginuser(user.getAccount());//获得用户密码
        if (userpassword.equals(user.getPassword()))
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    @Override
    public int getuserid(String account) {
        return userMapper.getuserid(account);
    }

    @Override
    public String getphonenumber(String name) {
        return userMapper.getphonenumber(name);
    }

    @Override
    public String findUserName(String username) {
        return userMapper.findUserName(username);
    }

    @Override
    public String findPhoneNumber(String username) {
        return userMapper.findPhoneNumber(username);
    }

    @Override
    public String findEmail(String username) {
        return userMapper.findEmail(username);
    }
}

