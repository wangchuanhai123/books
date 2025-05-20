package com.example.kzjzdatabase.service.impl;

import com.example.kzjzdatabase.entity.User;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

/**
 * 存储本次请求的授权信息，适用于各种业务场景，包括分布式部署
 *
 * @author lqd
 */
@Service
public class AuthStorage {




    @ApiModelProperty("请求头token的下标")
    public static final String TOKEN_KEY = "Token";

    /**
     * 模拟session
     */
    private static final HashMap<String, User> JWT_USER = new HashMap<String, User>();

    /**
     * 全局获取用户
     */
    public static User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return JWT_USER.get(request.getHeader(TOKEN_KEY));
    }

    /**
     * 设置用户
     */
    public static void setUser(String token, User user) {
        JWT_USER.put(token, user);
    }

    /**
     * 清除授权
     */
    public static void clearUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        JWT_USER.remove(request.getHeader(TOKEN_KEY));
    }
}
