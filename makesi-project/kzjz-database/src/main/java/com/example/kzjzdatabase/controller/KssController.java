package com.example.kzjzdatabase.controller;
import com.example.kzjzdatabase.entity.*;
import com.example.kzjzdatabase.entity.ReceiveObj.KssIndexRemoveReceive;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateReciveAll;
import com.example.kzjzdatabase.service.*;
import com.example.kzjzdatabase.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;

/**
 * 管理口述史
 */
@Api(tags = "管理口述史")
@RestController
//@RequestMapping("/root")
public class KssController {

    @Autowired
    KssService kssService;

    @Autowired
    KssindexService kssindexService;

    @Autowired
    UploadService uploadService;

    @Autowired
    HomeService homeService;

    /**
     * 添加口述史多文件并且同步创建一个索引
     */
    @ApiOperation(value = "添加口述史多文件并且同步创建一个索引")
    @RequestMapping(value = "/root/ksscreateall",
            method = {RequestMethod.POST})
    public JsonResult<Object> rootksscreateall(
                                          @RequestPart(value = "filelist", required = false) List<MultipartFile> filelist,
                                          @RequestPart(value = "coverlist", required = false) List<MultipartFile> coverlist,
                                          @RequestPart(value = "createRecivelist", required = false) List<CreateRecive> createRecivelist,
                                          @RequestPart Kssindex kssindex_noid,
                                          @RequestPart("indexcover") MultipartFile indexcover
    ) throws Exception
    {
       if(filelist != null && coverlist != null && createRecivelist != null) {
           int length = createRecivelist.size();
           for (int i = 0; i < length; ++i) {
               MultipartFile currentfile = filelist.get(i);
               MultipartFile currentcover = coverlist.get(i);
               CreateRecive currentRecive = createRecivelist.get(i);
               String Null = null;
               FileUrl fileUrl = new FileUrl();
               Kss kss = new Kss();
               BeanUtils.copyProperties(currentRecive, kss);
               kss.setOfkind(kssindex_noid.getOfname());             //ly added
               uploadService.upload(currentfile, currentcover, fileUrl);
               kss.setFile_url(fileUrl.getFileUrl());
               kss.setPicture_url(fileUrl.getCoverUrl());
               if (Objects.equals(kss.getPublishedtime(), Null)) {
                   kss.setPublishedtime("");
               }
               kss.setId(UUID.randomUUID().toString());
               kssService.ksscreate(kss);
           }
       }
       FileUrl coverUrl = new FileUrl();
       Kssindex kssindex = new Kssindex();
       BeanUtils.copyProperties(kssindex_noid, kssindex);
       uploadService.uploadcover(indexcover,coverUrl);
       kssindex.setId(UUID.randomUUID().toString());
       kssindex.setPicture_url(coverUrl.getCoverUrl());
       kssindexService.kssindexcreate(kssindex);

        return JsonResult.success();
    }

    @RequestMapping(value = "/root/kssUpdate", method = { RequestMethod.POST })
    public JsonResult<Object> rootKssUpdate(
            @RequestPart(value = "filelist", required = false) List<MultipartFile> filelist,
            @RequestPart(value = "coverlist", required = false) List<MultipartFile> coverlist,
            @RequestPart(value = "createRecivelist", required = false) List<CreateRecive> createRecivelist,
            @RequestPart Kssindex kssIndexDscb,
            @RequestPart(value = "indexcover", required = false) MultipartFile indexcover,
            @RequestPart(required = false) List<KssIndexRemoveReceive> deleteIdList
    )throws Exception
    {
        //System.out.println(createRecivelist);
        Updateofkind updateofkind = new Updateofkind();
        UpdateReciveAll updateAllFields = new UpdateReciveAll();
        //从现有口述史中移除
        if(deleteIdList != null)
            for(KssIndexRemoveReceive item : deleteIdList) {
                uploadService.deleteFile(item.getPicture_url());
                uploadService.deleteFile(item.getFile_url());
                kssService.kssdeletebyid(item.getId());
            }
        //从现有口述史中添加新的文件
        if(filelist != null && coverlist != null && createRecivelist != null)
            for(int i = 0; i < createRecivelist.size(); i++)
                rootksscreate(filelist.get(i), coverlist.get(i), createRecivelist.get(i));
        //删除原有封面，并上传新封面
        //System.out.println(indexcover);
        if(indexcover != null) {
            uploadService.deleteFile(kssIndexDscb.getPicture_url());
            FileUrl coverUrl = new FileUrl();
            uploadService.uploadcover(indexcover, coverUrl);
            kssIndexDscb.setPicture_url(coverUrl.getCoverUrl());
        }
        //更新口述史内部文件ofkind字段，让内部文件始终与索引对应起来
        String str = kssindexService.getkssindexbyid(kssIndexDscb.getId()).getOfname();
        updateofkind.setOldofkind(str);
        updateofkind.setNewofkind(kssIndexDscb.getOfname());
        kssService.kssupdateofkind(updateofkind);
        //更新索引字段
        BeanUtils.copyProperties(kssIndexDscb, updateAllFields);
        kssindexService.kssindexUpdateAllFieds(updateAllFields);
        homeService.updateHomeKssIndex(updateAllFields);

        return JsonResult.success(kssIndexDscb);
    }

    /**
     * 根据已有口述史索引添加单个口述史文件
     */
    //@ApiOperation(value = "根据已有口述史索引添加单个口述史文件")
    //@RequestMapping(value = "/root/ksscreate",
    //        method = {RequestMethod.POST})
    public void rootksscreate(MultipartFile file, MultipartFile cover, CreateRecive createRecive) throws Exception {
        //createRecive中包含ofkind为索引名字，需要前端先查询所有索引再赋值其ofname到createRecive的ofkind
        String Null = null;
        FileUrl fileUrl = new FileUrl();
        Kss kss = new Kss();
        BeanUtils.copyProperties(createRecive, kss);
        uploadService.upload(file,cover,fileUrl);
        kss.setFile_url(fileUrl.getFileUrl());
        kss.setPicture_url(fileUrl.getCoverUrl());
        if(Objects.equals(kss.getPublishedtime(), Null)){kss.setPublishedtime("");}
        kss.setId(UUID.randomUUID().toString());
        kssService.ksscreate(kss);
    }

    /**
     * 根据ID删除口述史资料表中的一个文件
     */
    @ApiOperation(value = "根据ID删除口述史资料表中的一个文件")
    @RequestMapping(value = "/root/kssdelete",
            method = {RequestMethod.POST})
    public JsonResult<Object> rootkssdelete(@RequestBody @Valid String id) {
        int successflag = 0;
        if(!Objects.equals(id, "")) {
            kssService.kssdeletebyid(id);
            //主表联动删除索引
            String current_ofkind = kssService.getkssbyid(id).getOfkind();
            if(kssService.getkssbyofkind(current_ofkind).size()==0){
                kssindexService.kssindexdeletebyofname(current_ofkind);
            }
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
     * 根据ofname删除口述史资料索引表中的一个索引
     */
    @ApiOperation(value = "根据ofname删除口述史资料索引表中的一个索引")
    @RequestMapping(value = "/root/kssindexdelete",
            method = {RequestMethod.POST})
    public JsonResult<Object> rootkssindexdelete(@RequestBody @Valid DeleteKss deleteKss) {
        int successflag = 0;
        int deleteFail  = 0;
        if(!Objects.equals(deleteKss.getOfname(), "")) {
            String kssPictureUrl = deleteKss.getPicture_url();
            if(!uploadService.deleteFile(kssPictureUrl))
                deleteFail = 1;
            //删除索引联动删除主表元素
            List<Kss> kssAllContent = kssService.getkssbyofkind(deleteKss.getOfname());
            for(Kss content : kssAllContent){
                if(!uploadService.deleteFile(content.getFile_url()) || !uploadService.deleteFile(content.getPicture_url())) {
                    deleteFail = 1;
                    break;
                }
            }
            if(deleteFail != 1) {
                kssindexService.kssindexdeletebyofname(deleteKss.getOfname());
                kssService.kssdeletebyofkind(deleteKss.getOfname());
                homeService.deleteFromHome(deleteKss.getId());
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

    /**
     * 根据类型、ID、修改字段键值对，修改口述史资料表中某文件某列
     */
    @ApiOperation(value = "根据类型、ID、修改字段键值对，修改口述史资料表中某文件某列")
    @RequestMapping(value = "/root/kssupdate",
            method = {RequestMethod.POST})
    public JsonResult<Object> rootkssupdate(@RequestBody @Valid UpdateRecive updateRecive) {
        int successflag = 0;
        //口述史资料的ofkind字段不让改
        if(!Objects.equals(updateRecive.getChangeline(), "ofkind")){
            kssService.kssupdate(updateRecive);
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
     * 根据类型、ID、修改字段键值对，修改口述史资料索引表中某条某列
     */
    @ApiOperation(value = "根据类型、ID、修改字段键值对，修改口述史资料索引表中某条某列")
    @RequestMapping(value = "/root/kssindexupdate",
            method = {RequestMethod.POST})
    public JsonResult<Object> rootkssindexupdate(@RequestBody @Valid UpdateRecive updateRecive) {
        int successflag = 0;
        //口述史资料索引表的picture_url字段不让改
        if(!Objects.equals(updateRecive.getChangeline(), "picture_url")){
            //拿到没修改之前的索引表的ofname
            String str = kssindexService.getkssindexbyid(updateRecive.getId()).getOfname();
            kssindexService.kssindexupdate(updateRecive);
            //联动修改口述史资料表中的ofkind字段
            if(Objects.equals(updateRecive.getChangeline(), "ofname")){
                Updateofkind updateofkind = new Updateofkind();
                updateofkind.setOldofkind(str);
                updateofkind.setNewofkind(updateRecive.getValue());
                kssService.kssupdateofkind(updateofkind);
            }
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
     * 管理界面根据长字符串查询口述史资料表中的所有文件
     */
    @ApiOperation(value = "管理界面根据长字符串查询口述史资料表中的所有文件")
    @RequestMapping(value = "/root/searchkssall",
            method = {RequestMethod.GET})
    public JsonResult<Object> rootsearchkssall( @RequestParam @Valid String searchInput) {
        List<Kss>  all_List = kssService.getkssbyofkind(searchInput);
        return JsonResult.success(all_List);
    }

    /**
     * 管理界面根据长字符串查询口述史资料索引表中的所有索引
     */
    @ApiOperation(value = "管理界面根据长字符串查询口述史资料索引表中的所有索引")
    @RequestMapping(value = "/root/searchkssindexall",
            method = {RequestMethod.GET})
    public JsonResult<Object> rootsearchkssindexall(@RequestParam @Valid String searchInput) {
        List<String> infos = new ArrayList<>(Arrays.asList(searchInput.split(" ")));
        GBList gbList = getGBList(infos);
        List<Kssindex>  kssindexList = kssindexService.getkssindex(gbList);
        return JsonResult.success(kssindexList);
    }

    private static GBList getGBList(List<String> info) {
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

