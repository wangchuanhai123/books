package com.example.kzjzdatabase.controller;

import com.example.kzjzdatabase.entity.All_max;
import com.example.kzjzdatabase.entity.Audio.Audio;
import com.example.kzjzdatabase.entity.Book.Book;
import com.example.kzjzdatabase.entity.File.File;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.Home.Home;
import com.example.kzjzdatabase.entity.Journal.Journal;
import com.example.kzjzdatabase.entity.Kssindex;
import com.example.kzjzdatabase.entity.Newspaper.Newspaper;
import com.example.kzjzdatabase.entity.Picture.Picture;
import com.example.kzjzdatabase.entity.ReceiveObj.HomeDeleteReceive;
import com.example.kzjzdatabase.entity.RetType.GetAllHomeRet;
import com.example.kzjzdatabase.entity.Special_Index;
import com.example.kzjzdatabase.entity.Video.Video;
import com.example.kzjzdatabase.service.*;
import com.example.kzjzdatabase.util.JsonResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class HomeController {

    @Autowired
    HomeService homeService;

    @Autowired
    BookService bookService;

    @Autowired
    NewspaperService newspaperService;

    @Autowired
    FileService fileService;

    @Autowired
    JournalService journalService;

    @Autowired
    AudioService audioService;

    @Autowired
    PictureService pictureService;

    @Autowired
    VideoService videoService;

    @Autowired
    Special_IndexService special_indexService;

    @Autowired
    KssService kssService;

    @Autowired
    KssindexService kssindexService;

    private void addList(List<Home> target, List<Home> source, List<Integer> lens){
        int len = 0;
        for(Home s : source){
          target.add(s);
          len++;
        }
        lens.add(len);
    }

    @RequestMapping(value = "/home/getAllHomeElem", method = {RequestMethod.POST})
    public JsonResult<Object> getHomeAllElem(){
        GetAllHomeRet getAllHomeRet = new GetAllHomeRet();
        try {
            List<Home> ret = new ArrayList<Home>();
            List<Integer> retLen = new ArrayList<Integer>();
            List<Home> kss = homeService.getFromHome("口述史资料");
            List<Home> documentations = homeService.getFromHome("文献资料");
            List<Home> academicStudy = homeService.getFromHome("学术研究");
            List<Home> specialStudy = homeService.getFromHome("特色专题研究");
            addList(ret, kss, retLen);
            addList(ret, documentations, retLen);
            addList(ret, academicStudy, retLen);
            addList(ret, specialStudy, retLen);
            getAllHomeRet.setHomeList(ret);
            getAllHomeRet.setHomeListLen(retLen);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.error();
        }finally {
            return JsonResult.success(getAllHomeRet);
        }
    }

    @RequestMapping(value = "/home/getHomeElem", method = {RequestMethod.GET})
    public JsonResult<Object> getHomeElem(@Param("ofkind") @Valid String ofkind){
        List<Home> ret = homeService.getFromHome(ofkind);
        return JsonResult.success(ret);
    }

    @RequestMapping(value = "/home/indexUpload", method = {RequestMethod.POST})
    public JsonResult<Object> addHomeElem(@RequestBody HomeDeleteReceive receive){
        try{
             for(Home n : receive.getNewIndexElem()){
                 homeService.addToHome(n);
             }
             for(Home d : receive.getOffIndexElem()){
                 homeService.deleteFromHome(d.getId());
             }
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    private void getAllEntity(List<All_max> allList, GBList gbList, String ofkind) {
        List<Book>  bookList = bookService.getBooks(gbList);
        List<Newspaper>  newspaperList = newspaperService.getNewspapers(gbList);
        List<File>  fileList = fileService.getFiles(gbList);
        List<Journal>  journalList = journalService.getJournals(gbList);
        List<Audio>  audioList = audioService.getAudios(gbList);
        List<Picture>  pictureList = pictureService.getPictures(gbList);
        List<Video>  videoList = videoService.getVideos(gbList);
        for(Book book:bookList){
            if(!book.getOfkind().equals(ofkind))
                continue;
            All_max all = new All_max();
            BeanUtils.copyProperties(book, all);
            all.setOftype("图书");
            allList.add(all);
        }
        for(Newspaper newspaper:newspaperList){
            if(!newspaper.getOfkind().equals(ofkind))
                continue;
            All_max all = new All_max();
            BeanUtils.copyProperties(newspaper, all);
            all.setOftype("报纸");
            allList.add(all);
        }
        for(File file:fileList){
            if(!file.getOfkind().equals(ofkind))
                continue;
            All_max all = new All_max();
            BeanUtils.copyProperties(file, all);
            all.setOftype("档案");
            allList.add(all);
        }
        for(Journal journal:journalList){
            if(!journal.getOfkind().equals(ofkind))
                continue;
            All_max all = new All_max();
            BeanUtils.copyProperties(journal, all);
            all.setOftype("期刊");
            allList.add(all);
        }
        for(Audio audio:audioList){
            if(!audio.getOfkind().equals(ofkind))
                continue;
            All_max all = new All_max();
            BeanUtils.copyProperties(audio, all);
            all.setOftype("音频");
            allList.add(all);
        }
        for(Picture picture:pictureList){
            if(!picture.getOfkind().equals(ofkind))
                continue;
            All_max all = new All_max();
            BeanUtils.copyProperties(picture, all);
            all.setOftype("图片");
            allList.add(all);
        }
        for(Video video:videoList){
            if(!video.getOfkind().equals(ofkind))
                continue;
            All_max all = new All_max();
            BeanUtils.copyProperties(video, all);
            all.setOftype("视频");
            allList.add(all);
        }
    }

    @RequestMapping(value = "/home/indexManageSearch",
            method = {RequestMethod.GET})
    public JsonResult<Object> indexManageSearch(@Param("info") @Valid String info, @Param("ofkind") @Valid String ofkind) {
        System.out.println("hellow");
        //System.out.println(info);
        List<All_max> allList = new ArrayList<>();
        try {
            List<String> infos = new ArrayList<>(Arrays.asList(info.split(" ")));
            GBList gbList = getGBList(infos);

            switch (ofkind) {
                case "口述史资料":
                    List<Kssindex> kssList = kssindexService.getkssindex(gbList);
                    for (Kssindex kss : kssList) {
                        All_max all_max = new All_max();
                        BeanUtils.copyProperties(kss, all_max);
                        all_max.setOftype("口述史");
                        all_max.setOfkind("口述史资料");
                        allList.add(all_max);
                    }
                    break;
                case "特色专题研究":
                    List<Special_Index> specialList = special_indexService.getspecialindex(gbList);
                    for (Special_Index s_index : specialList) {
                        All_max all_max = new All_max();
                        BeanUtils.copyProperties(s_index, all_max);
                        all_max.setOftype("专题库");
                        all_max.setOfkind("特色专题研究");
                        allList.add(all_max);
                    }
                    break;
                case "文献资料":
                    getAllEntity(allList, gbList, "文献资料");
                    break;
                case "学术研究":
                    getAllEntity(allList, gbList, "学术研究");
                    break;
                default:
                    return JsonResult.error();
            }
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.error();
        }

        return JsonResult.success(allList);
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
