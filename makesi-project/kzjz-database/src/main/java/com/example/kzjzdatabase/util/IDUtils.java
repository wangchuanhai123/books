package com.example.kzjzdatabase.util;

import java.util.UUID;

public class IDUtils {

    /**
     * 唯一ID生成器，可以生成唯一ID
     * @return
     */
    public static String generateUniqueId() {
        return UUID.randomUUID().toString()+System.currentTimeMillis();
    }
}