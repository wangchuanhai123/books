package com.example.kzjzdatabase.controller;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.kzjzdatabase.entity.DeleteRecive;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.util.JsonResult;
import com.example.kzjzdatabase.entity.User;
import com.example.kzjzdatabase.service.UserService;
import com.example.kzjzdatabase.util.MD5Util;
import com.example.kzjzdatabase.util.PhoneUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


/**
 * 用户管理
 */
@Api(tags = "用户管理")
@RestController
//@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户列表
     */
    @ApiOperation(value = "用户列表")
    @PostMapping("/user/list")
    public JsonResult<Object> list() {
        List<User> userList = userService.findAll();
        return JsonResult.success(userList);
    }

    @ApiOperation(value = "动态修改昵称")
    @RequestMapping(value = "/api/get-username",
            method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult<String> abc(@RequestBody @Valid String username){
        System.out.println(username);
        //把json中的数据取出来
        JSONObject jsonObject = JSONObject.parseObject(username);
        username = jsonObject.getString("username");
        System.out.println(username);
        username=userService.findUserName(username);
        return JsonResult.success(username);
    }

    @ApiOperation(value = "动态修改信息")
    @RequestMapping(value = "/api/get-user-information",
            method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult<User> getUserInformation(@RequestBody @Valid User user){
        System.out.println(user);
        String username= user.getUsername();
        System.out.println(username);
        String phoneNumber=userService.findPhoneNumber(username);
        String email=userService.findEmail(username);
        user.setEmail(email);
        user.setPhonenumber(phoneNumber);
        System.out.println(user);
        //把json中的数据取出来
        return JsonResult.success(user);
    }

    /**
     * 添加用户
     */
    @ApiOperation(value = "添加用户")
    @PostMapping("/user/create")
    public JsonResult<Object> userCreate(@RequestBody @Valid User user) {
        if (StrUtil.isEmpty(user.getPassword())) {
            return JsonResult.error("密码为空，请填写密码！");
        }
        //密码加密存储
        user.setPassword(MD5Util.MD5(user.getPassword()));
        //判断手机号，这里用hutool工具类也可以
        if (!PhoneUtil.checkMobile(user.getPhone())) {
            return JsonResult.error("手机号码格式错误！");
        }
        user.setUserid(UUID.randomUUID().toString());
        userService.createUser(user);
        return JsonResult.success();
    }

    /**
     * 修改用户
     */
    @ApiOperation(value = "修改用户")
    @RequestMapping(value = "/user/update",
            method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult<Object> userUpdate(@RequestBody @Valid UpdateRecive updateRecive) {
        Integer successflag = 0;
        if(updateRecive.getOftype().equals("用户")){
            if(updateRecive.getChangeline().equals("password")){
                if (StrUtil.isEmpty(updateRecive.getValue())) {
                    System.out.println("null");
                    return JsonResult.error("密码为空，请填写密码！");
                }
                //密码加密存储
                updateRecive.setValue(MD5Util.MD5(updateRecive.getValue()));
            }
            if(updateRecive.getChangeline().equals("phone")) {
                //判断手机号，这里用hutool工具类也可以
                if (!PhoneUtil.checkMobile(updateRecive.getValue())) {
                    return JsonResult.error("手机号码格式错误！");
                }
            }
            userService.updateUser(updateRecive);
            successflag = 1;
        }
        if(successflag == 1){
            return JsonResult.success();
        }
        else {
            return JsonResult.error();
        }
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/user/delete",
            method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult<Object> userDelete(@RequestBody @Valid List<DeleteRecive> deletelist) {
        Integer successflag = 0;
        for (DeleteRecive recive : deletelist) {
            DeleteRecive currentrecive = new DeleteRecive();
            currentrecive.setOftype(recive.getOftype());
            currentrecive.setId(recive.getId());
            if (currentrecive.getOftype().equals("用户")) {
                userService.deleteUser(currentrecive.getId());
                successflag = 1;
            }
        }
        if(successflag == 1){
            return JsonResult.success();
        }
        else {
            return JsonResult.error();
        }
    }

}


