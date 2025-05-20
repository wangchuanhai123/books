package com.example.kzjzdatabase.mapper;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserMapper {

    /**
     * 查询全部用户信息
     */
    List<User> findAll();

    /**
     * 添加用户
     */
    void insert(User user);

    /**
     * 更新用户
     */
    void update(UpdateRecive updateRecive);

    /**
     * 删除用户
     */
    int delete(String id);
    boolean checkuserbyaccount(String account);
    boolean checkuserbyname(String name);
    boolean checkuserbyphonenumber(String phonenumber);
    boolean checkuserbyemail(String email);
    String loginuser(String account);
    int getuserid(String account);
    String getphonenumber(String name);

    String findUserName(String username);

    String findPhoneNumber(String username);

    String findEmail(String username);
}
