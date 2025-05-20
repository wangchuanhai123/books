package com.example.kzjzdatabase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void saveToken(String key, String token) {
        redisTemplate.opsForValue().set(key, token , 10*60 , TimeUnit.SECONDS);
    }

    public String getToken(String key) {
        return  redisTemplate.opsForValue().get(key);
    }

    public void deleteToken(String key) {
        redisTemplate.delete(key);
    }

    public boolean isTokenExists(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}