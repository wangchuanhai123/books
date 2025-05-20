package com.example.kzjzdatabase.util;

import com.example.kzjzdatabase.entity.GBList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GBLUtil {
    public static GBList getGBList(String str) {
        List<String> info = new ArrayList<>(Arrays.asList(str.split(" ")));
        GBList gbList = new GBList();
        gbList.setLen(info.size());
        switch(info.size()) {
            case 1:
                gbList.setStr1('%'+ info.get(0) + '%');
                break;
            case 2:
                gbList.setStr1('%'+ info.get(0) + '%');
                gbList.setStr2('%'+ info.get(1) + '%');
                break;
            case 3:
                gbList.setStr1('%'+ info.get(0) + '%');
                gbList.setStr2('%'+ info.get(1) + '%');
                gbList.setStr3('%'+ info.get(2) + '%');
                break;
            case 4:
                gbList.setStr1('%'+ info.get(0) + '%');
                gbList.setStr2('%'+ info.get(1) + '%');
                gbList.setStr3('%'+ info.get(2) + '%');
                gbList.setStr4('%'+ info.get(3) + '%');
                break;
            default:
                gbList.setStr1('%'+ info.get(0) + '%');
                gbList.setStr2('%'+ info.get(1) + '%');
                gbList.setStr3('%'+ info.get(2) + '%');
                gbList.setStr4('%'+ info.get(3) + '%');
                gbList.setStr5('%'+ info.get(4) + '%');
                break;
        }
        return gbList;
    }
}
