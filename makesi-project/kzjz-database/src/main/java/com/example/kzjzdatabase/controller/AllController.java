package com.example.kzjzdatabase.controller;
import com.alibaba.fastjson.JSON;
import com.example.kzjzdatabase.entity.*;
import com.example.kzjzdatabase.entity.Audio.Audio;
import com.example.kzjzdatabase.entity.ReceiveObj.SpecialUpdateReceiveAll;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.entity.SpecialindexDeletePragram;
import com.example.kzjzdatabase.entity.Book.Book;
import com.example.kzjzdatabase.entity.File.File;
import com.example.kzjzdatabase.entity.Journal.Journal;
import com.example.kzjzdatabase.entity.Newspaper.Newspaper;
import com.example.kzjzdatabase.entity.Picture.Picture;
import com.example.kzjzdatabase.entity.Video.Video;
import com.example.kzjzdatabase.service.*;
import com.example.kzjzdatabase.service.impl.TokenProvider;
import com.example.kzjzdatabase.service.impl.TokenService;
import com.example.kzjzdatabase.util.JsonResult;
import com.example.kzjzdatabase.entity.ReceiveObj.specialElementDeleteReceive;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
/**
 * 管理7表
 */
@Api(tags = "管理7表")
@RestController
public class AllController {
    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";//验证码字符集
    private static String Password = ""; //验证码
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
    UploadService uploadService;

    @Autowired
    Special_IndexService special_indexService;

    @Autowired
    KssService kssService;

    @Autowired
    KssindexService kssindexService;

    @Autowired
    HomeService homeService;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;
    /**
     * 添加非口述史的单文件
     */
    @ApiOperation(value = "添加非口述史的单文件")
    @RequestMapping(value = "/root/7create",
            method = {RequestMethod.POST})
    public JsonResult<Object> root7create(@RequestPart("file") MultipartFile file,
                                         @RequestPart("cover") MultipartFile cover,
                                         @RequestPart CreateRecive createRecive) throws Exception {

        int successflag = 0;
        String Null = null;
        FileUrl fileUrl = new FileUrl();
        switch (createRecive.getOftype()) {
            case "图书":
                Book book = new Book();
                BeanUtils.copyProperties(createRecive, book);
                uploadService.upload(file,cover,fileUrl);
                book.setFile_url(fileUrl.getFileUrl());
                book.setPicture_url(fileUrl.getCoverUrl());
                if(Objects.equals(book.getPublishedtime(), Null)){book.setPublishedtime("");}
                book.setId(UUID.randomUUID().toString());
                bookService.bookcreate(book);
                successflag = 1;
                break;
            case "报纸":
                Newspaper newspaper = new Newspaper();
                BeanUtils.copyProperties(createRecive, newspaper);
                uploadService.upload(file,cover,fileUrl);
                newspaper.setFile_url(fileUrl.getFileUrl());
                newspaper.setPicture_url(fileUrl.getCoverUrl());
                if(Objects.equals(newspaper.getPublishedtime(), Null)){newspaper.setPublishedtime("");}
                newspaper.setId(UUID.randomUUID().toString());
                newspaperService.newspapercreate(newspaper);
                successflag = 1;
                break;
            case "档案":
                File doc = new File();
                BeanUtils.copyProperties(createRecive, doc);
                uploadService.upload(file,cover,fileUrl);
                doc.setFile_url(fileUrl.getFileUrl());
                doc.setPicture_url(fileUrl.getCoverUrl());
                if(Objects.equals(doc.getPublishedtime(), Null)){doc.setPublishedtime("");}
                doc.setId(UUID.randomUUID().toString());
                fileService.filecreate(doc);
                successflag = 1;
                break;
            case "期刊":
                Journal journal = new Journal();
                BeanUtils.copyProperties(createRecive, journal);
                uploadService.upload(file,cover,fileUrl);
                journal.setFile_url(fileUrl.getFileUrl());
                journal.setPicture_url(fileUrl.getCoverUrl());
                if(Objects.equals(journal.getPublishedtime(), Null)){journal.setPublishedtime("");}
                journal.setId(UUID.randomUUID().toString());
                journalService.journalcreate(journal);
                successflag = 1;
                break;
            case "音频":
                Audio audio = new Audio();
                BeanUtils.copyProperties(createRecive, audio);
                uploadService.upload(file,cover,fileUrl);
                audio.setFile_url(fileUrl.getFileUrl());
                audio.setPicture_url(fileUrl.getCoverUrl());
                if(Objects.equals(audio.getPublishedtime(), Null)){audio.setPublishedtime("");}
                audio.setId(UUID.randomUUID().toString());
                audioService.audiocreate(audio);
                successflag = 1;
                break;
            case "图片":
                Picture picture = new Picture();
                BeanUtils.copyProperties(createRecive, picture);
                uploadService.upload(file,cover,fileUrl);
                picture.setFile_url(fileUrl.getFileUrl());
                picture.setPicture_url(fileUrl.getCoverUrl());
                if(Objects.equals(picture.getPublishedtime(), Null)){picture.setPublishedtime("");}
                picture.setId(UUID.randomUUID().toString());
                pictureService.picturecreate(picture);
                successflag = 1;
                break;
            case "视频":
                Video video = new Video();
                BeanUtils.copyProperties(createRecive, video);
                uploadService.upload(file,cover,fileUrl);
                video.setFile_url(fileUrl.getFileUrl());
                video.setPicture_url(fileUrl.getCoverUrl());
                if(Objects.equals(video.getPublishedtime(), Null)){video.setPublishedtime("");}
                video.setId(UUID.randomUUID().toString());
                videoService.videocreate(video);
                successflag = 1;
                break;
        }
        if(successflag == 1){
            return JsonResult.success();
        }
        else {
            return JsonResult.error();
        }
    }

    @ApiOperation(value = "下载文件")
    @RequestMapping(value = "/downloadfile",
            method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<Resource> downloadFile(@RequestBody @Valid String filename) {
        com.alibaba.fastjson.JSONObject result = JSON.parseObject(filename);
        String filekey = result.getString("filename");
        String filetype = result.getString("filetype");
        System.out.println(filekey);
        System.out.println(filetype);

        String Filename = null;
        if (filekey != null) {
            if (filetype.equals("图片")) {
                Filename = fileService.selectidbyimage(filekey);
            }
            if (filetype.equals("图书")) {
                Filename = fileService.selectidbyofname(filekey);
            }
        }
        System.out.println(Filename);
        if (Filename == null) {
            return ResponseEntity.notFound().build();
        }
        System.out.println(Filename);
        try {
            // 根据文件名获取文件路径
            Path filePath = Paths.get("/" + Filename).toAbsolutePath().normalize();

            System.out.println("Resolved file path: " + filePath);

            Resource resource = new UrlResource(filePath.toUri());

            // 检查文件是否存在
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String type = getFileType(Filename);

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", type);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    public static String getFileType(String fileName) {
        // 查找最后一个 '.' 的位置
        int lastDotIndex = fileName.lastIndexOf('.');

        // 如果存在 '.'，则截取文件类型
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        }

        // 如果没有 '.'，则返回空字符串
        return "";
    }


    /**
     * 根据类型、ID、修改字段键值对，修改7个介质表中某文件某列
     */
    @ApiOperation(value = "根据类型、ID、修改字段键值对，修改7个介质表中某文件某列")
    @RequestMapping(value = "/root/7update",
            method = {RequestMethod.POST})
    public JsonResult<Object> root7update(@RequestBody @Valid UpdateRecive updateRecive) {
        int successflag = 0;
        switch (updateRecive.getOftype()) {
            case "图书":
                bookService.bookupdate(updateRecive);
                homeService.updateHomeById(updateRecive);
                successflag = 1;
                break;
            case "报纸":
                newspaperService.newspaperupdate(updateRecive);
                homeService.updateHomeById(updateRecive);
                successflag = 1;
                break;
            case "档案":
                fileService.fileupdate(updateRecive);
                homeService.updateHomeById(updateRecive);
                successflag = 1;
                break;
            case "期刊":
                journalService.journalupdate(updateRecive);
                homeService.updateHomeById(updateRecive);
                successflag = 1;
                break;
            case "音频":
                audioService.audioupdate(updateRecive);
                homeService.updateHomeById(updateRecive);
                successflag = 1;
                break;
            case "图片":
                pictureService.pictureupdate(updateRecive);
                homeService.updateHomeById(updateRecive);
                successflag = 1;
                break;
            case "视频":
                videoService.videoupdate(updateRecive);
                homeService.updateHomeById(updateRecive);
                successflag = 1;
                break;
        }
        if(successflag == 1){
            return JsonResult.success();
        }
        else {
            return JsonResult.error();
        }
    }

    /**
     * 删除文件时对特色专题研究索引表的所有相关行的ids列联动操作
     */
    public void delete_util(String id){
        List<Updateids> allididsList = special_indexService.getspecialindexidids();
        for (Updateids x : allididsList) {
            if(x.getIds() == null)
                continue;
            List<String> x_ids = new ArrayList<>(Arrays.asList(x.getIds().split("%")));
            x_ids.removeIf(next -> next.equals(id));
            //重新按%拼接起来成字符串
            String newids = String.join("%", x_ids);
            //若更改了ids，重新插入id下的ids
            if(!newids.equals(x.getIds())) {
                Updateids updateids = new Updateids();
                updateids.setId(x.getId());
                updateids.setIds(newids);
                special_indexService.specialindexupdateids(updateids);
            }
        }
    }

    public static void sendVerificationEmail(String recipientEmail, String verificationCode) throws MessagingException {
        // QQ 邮箱 SMTP 服务器地址
        String smtpHost = "smtp.qq.com";
        // QQ 邮箱地址（发送者）
        String senderEmail = "1902273162@qq.com";
        // QQ 邮箱授权码（不是登录密码，需要在 QQ 邮箱设置中申请）
        String senderPassword = "wqwkveeknkisdfca";

        // 配置邮件服务器的属性
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", "465"); // 或 587，如果使用 STARTTLS 则为 25（不推荐）
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true"); // 使用 SSL 加密

        // 创建会话对象
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        };
        Session session = Session.getInstance(properties, auth);

        // 创建邮件消息对象
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("验证邮件");
        message.setText("您的验证码是: " + verificationCode);

        // 发送邮件
        Transport.send(message);

        System.out.println("验证邮件已发送至 " + recipientEmail);
    }
    public static String generateCaptcha(int length) {
        StringBuilder captcha = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            // 从字符集中随机选择一个字符
            int index = random.nextInt(CHAR_SET.length());
            captcha.append(CHAR_SET.charAt(index));
        }
        return captcha.toString();
    }

    @RequestMapping(value = "/test",
            method = {RequestMethod.POST})
    public String getInfo() {
        // 从全局环境中获取用户id
        return "用户："+"请求成功";
    }

    @RequestMapping(value = "/yanzhen",
                method = {RequestMethod.POST})
    public JsonResult<Object> yanzhen(@RequestBody @Valid String emaildata) {
        int startIndex = emaildata.indexOf("\"email\":\"") + "\"email\":\"".length();
        int endIndex = emaildata.indexOf("\"", startIndex);
        // 提取邮箱地址
        String email = emaildata.substring(startIndex, endIndex);
        Password = generateCaptcha(6);
        try {
            sendVerificationEmail(email, Password);
        } catch (MessagingException e) {
            e.printStackTrace();
            return JsonResult.error("邮件发送失败");
        }
        return JsonResult.success();
    }


    @RequestMapping(value = "/login",
            method = {RequestMethod.POST})
    public JsonResult<Object> login(@RequestBody @Valid User user) {
        int key = userService.loginUser(user); //判断登录状态关键  0代表账户不存在
        if (key == 0)
        {
            return JsonResult.error("账户不存在，请注册账号");
        } else if (key == 2) {
            return JsonResult.error("密码输入错误，请重新输入");
        }
        user.setUserid(String.valueOf(userService.getuserid(user.getAccount())));
        String token = TokenProvider.createToken(user.getUserid(),"web",user.getRole());
        tokenService.saveToken(user.getUserid(),token);
        return JsonResult.success(token);
    }



    @RequestMapping(value = "/token/validate",
            method = {RequestMethod.POST})
    public User validate(@RequestHeader("token")  String token) {
        System.out.println(token);
        return TokenProvider.checkToken(token);
    }

    @RequestMapping(value = "/regis",
            method = {RequestMethod.POST})
    public JsonResult<Object> regis(@RequestBody @Valid User user) {
        String captcha = user.getCaptcha();
        if (!captcha.equals(Password)) {
            return JsonResult.error("验证码输入错误");
        }
        int key = userService.checkUser(user);//检查数据库状态
        if(key == 0){
            userService.createUser(user);
            return JsonResult.success();
        }
        else if (key == 1){
            return JsonResult.error("账户已存在");
        }
        else if (key == 2){
            return JsonResult.error("用户名已存在");
        }
        else if (key == 3){
            return JsonResult.error("手机号已存在");
        }
        else if (key == 4){
            return JsonResult.error("邮箱已存在");
        }
        return JsonResult.success();
    }
    /**
     * 根据一个或多个类型和ID删除7个介质表中的文件
     */
    @ApiOperation(value = "根据一个或多个类型和ID删除7个介质表中的文件")
    @RequestMapping(value = "/root/7delete",
            method = {RequestMethod.POST})
    public JsonResult<Object> root7delete(@RequestBody @Valid List<DeleteRecive> deletelist) {
        int successflag = 0;
        for (DeleteRecive recive : deletelist) {
            if(!uploadService.deleteFile(recive.getFile_url()))
              break;
            if(!uploadService.deleteFile(recive.getPicture_url()))
              break;
            switch (recive.getOftype()) {
                case "图书":
                    bookService.bookdelete(recive.getId());
                    delete_util(recive.getId());
                    homeService.deleteFromHome(recive.getId());
                    successflag = 1;
                    break;
                case "报纸":
                    newspaperService.newspaperdelete(recive.getId());
                    delete_util(recive.getId());
                    homeService.deleteFromHome(recive.getId());
                    successflag = 1;
                    break;
                case "档案":
                    fileService.filedelete(recive.getId());
                    delete_util(recive.getId());
                    homeService.deleteFromHome(recive.getId());
                    successflag = 1;
                    break;
                case "期刊":
                    journalService.journaldelete(recive.getId());
                    delete_util(recive.getId());
                    homeService.deleteFromHome(recive.getId());
                    successflag = 1;
                    break;
                case "音频":
                    audioService.audiodelete(recive.getId());
                    delete_util(recive.getId());
                    homeService.deleteFromHome(recive.getId());
                    successflag = 1;
                    break;
                case "图片":
                    pictureService.picturedelete(recive.getId());
                    delete_util(recive.getId());
                    homeService.deleteFromHome(recive.getId());
                    successflag = 1;
                    break;
                case "视频":
                    videoService.videodelete(recive.getId());
                    delete_util(recive.getId());
                    homeService.deleteFromHome(recive.getId());
                    successflag = 1;
                    break;
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
     * 管理界面根据长字符串查询7个介质表中的所有文件
     */
    @ApiOperation(value = "管理界面根据长字符串查询7个介质表中的所有文件")
    @RequestMapping(value = "/root/7searchall",
            method = {RequestMethod.GET})
    public JsonResult<Object> root7searchall(@Param("info") @Valid String info) {
        //System.out.println(info);
        List<String> infos = new ArrayList<>(Arrays.asList(info.split(" ")));
        GBList gbList = getGBList(infos);

        List<Book>  bookList = bookService.getBooks(gbList);
        List<Newspaper>  newspaperList = newspaperService.getNewspapers(gbList);
        List<File>  fileList = fileService.getFiles(gbList);
        List<Journal>  journalList = journalService.getJournals(gbList);
        List<Audio>  audioList = audioService.getAudios(gbList);
        List<Picture>  pictureList = pictureService.getPictures(gbList);
        List<Video>  videoList = videoService.getVideos(gbList);

        List<All_max> allList = new ArrayList<All_max>();
        for(Book book:bookList){
            All_max all = new All_max();
            BeanUtils.copyProperties(book, all);
            all.setOftype("图书");
            allList.add(all);
        }
        for(Newspaper newspaper:newspaperList){
            All_max all = new All_max();
            BeanUtils.copyProperties(newspaper, all);
            all.setOftype("报纸");
            allList.add(all);
        }
        for(File file:fileList){
            All_max all = new All_max();
            BeanUtils.copyProperties(file, all);
            all.setOftype("档案");
            allList.add(all);
        }
        for(Journal journal:journalList){
            All_max all = new All_max();
            BeanUtils.copyProperties(journal, all);
            all.setOftype("期刊");
            allList.add(all);
        }
        for(Audio audio:audioList){
            All_max all = new All_max();
            BeanUtils.copyProperties(audio, all);
            all.setOftype("音频");
            allList.add(all);
        }
        for(Picture picture:pictureList){
            All_max all = new All_max();
            BeanUtils.copyProperties(picture, all);
            all.setOftype("图片");
            allList.add(all);
        }
        for(Video video:videoList){
            All_max all = new All_max();
            BeanUtils.copyProperties(video, all);
            all.setOftype("视频");
            allList.add(all);
        }
        return JsonResult.success(allList);
    }

    /**
     * 添加空的特色专题索引
     */
    @ApiOperation(value = "添加空的特色专题索引")
    @RequestMapping(value = "/root/specialindexcreate",
            method = {RequestMethod.POST})
    public JsonResult<Object> rootspecialindexcreate(@RequestPart Special_Index specialindex_noid,
                                            @RequestPart("indexcover") MultipartFile indexcover) throws Exception {
        FileUrl coverUrl = new FileUrl();
        Special_Index special_index = new Special_Index();
        BeanUtils.copyProperties(specialindex_noid, special_index);
        uploadService.uploadcover(indexcover,coverUrl);
        special_index.setId(UUID.randomUUID().toString());
        special_index.setPicture_url(coverUrl.getCoverUrl());
        special_indexService.specialindexcreate(special_index);
        return JsonResult.success();
    }

    //更新专题库字段和封面
    @RequestMapping(value = "/root/specialIndexUpdate",
            method = {RequestMethod.POST})
    public JsonResult<Object> rootspecialIndexUpdate(@RequestPart SpecialUpdateReceiveAll receive,
                                                     @RequestPart(value = "indexcover", required = false) MultipartFile indexcover) throws Exception {
        if(indexcover != null) {
            uploadService.deleteFile(receive.getPicture_url());
            FileUrl coverUrl = new FileUrl();
            uploadService.uploadcover(indexcover, coverUrl);
            receive.setPicture_url(coverUrl.getCoverUrl());
        }

        special_indexService.specialIndexUpdateAllFieds(receive);
        homeService.updateHomeSpecialIndex(receive);
        return JsonResult.success(receive);
    }
    /**
     * 根据一个或多个ID,索引ID,添加7个介质表中的文件到已有特色专题索引
     */
    @ApiOperation(value = "根据一个或多个ID,索引ID,添加7个介质表中的文件到已有特色专题索引")
    @RequestMapping(value = "/root/add2special",
            method = {RequestMethod.POST})
    public JsonResult<Object> rootadd2special(@RequestBody @Valid Add2special add2special) {
        Special_Index oldIndex = special_indexService.getspecialindexbyid(add2special.getIndexid());
        String oldids = oldIndex.getIds();
        String oldKinds = oldIndex.getKinds();
        List<String> newidlist = new ArrayList<>();
        List<String> newKindsList = new ArrayList<>();
        if(oldids != null) {
            for(String id : add2special.getIdList()){
                if(oldids.contains(id)){
                    return JsonResult.success("repeat"); //已经添加过了，返回repeat
                }
            }
            List<String> oldidlist = new ArrayList<>(Arrays.asList(oldids.split("%")));
            newidlist.addAll(oldidlist);
        }
        if(oldKinds != null){
            List<String> oldKindsList = new ArrayList<>(Arrays.asList(oldKinds.split("%")));
            newKindsList.addAll(oldKindsList);
        }
        newidlist.addAll(add2special.getIdList());
        newKindsList.addAll(add2special.getKindList());
        String newIds = String.join("%",newidlist);
        String newKinds = String.join("%", newKindsList);
        Updateids updateids = new Updateids();
        updateids.setId(add2special.getIndexid());
        updateids.setIds(newIds);
        updateids.setKinds(newKinds);
        special_indexService.specialindexupdateids(updateids);
        return JsonResult.success();
    }

    /**
     * 根据ofname删除特色专题索引表中的一个索引
     */
    @ApiOperation(value = "根据ofname删除特色专题索引表中的一个索引")
    @RequestMapping(value = "/root/specialindexdelete",
            method = {RequestMethod.POST})
    public JsonResult<Object> rootspecialindexdelete(@RequestBody @Valid SpecialindexDeletePragram receive) {
        int successflag = 0;
        //System.out.println(receive.getOfname());
        if(!Objects.equals(receive.getOfname(), "")) {
            special_indexService.specialindexdeletebyofname(receive.getOfname());
            homeService.deleteFromHome(receive.getId());
            String pictureUrl = receive.getPicture_url();  //ly added
            if(uploadService.deleteFile(pictureUrl))//ly added
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
     * 根据类型、ID、修改字段键值对，修改特色专题索引表中某条某列
     */
    @ApiOperation(value = "根据类型、ID、修改字段键值对，修改特色专题索引表中某条某列")
    @RequestMapping(value = "/root/specialindexupdate",
            method = {RequestMethod.POST})
    public JsonResult<Object> rootspecialindexupdate(@RequestBody @Valid UpdateRecive updateRecive) {
        int successflag = 0;
        //特色专题索引表的picture_url、ids字段在此方法中不让改
        if(!Objects.equals(updateRecive.getChangeline(), "picture_url")&&
                !Objects.equals(updateRecive.getChangeline(), "ids")){
            special_indexService.specialindexupdate(updateRecive);
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
     * 删除索引内文件
     */
    @RequestMapping(value = "/root/specialElementDel",
            method = {RequestMethod.POST})
    public JsonResult<Object> specialElementDeleteInIndex(@RequestBody @Valid specialElementDeleteReceive id) {
        Updateids idAndKind = special_indexService.getSpecialElementIdAndKind(id.getSpecialId());
        if(idAndKind.getIds() == null || idAndKind.getKinds() == null){
            return JsonResult.error();
        };
        List<String> ids = new ArrayList<>(Arrays.asList(idAndKind.getIds().split("%")));
        List<String> kinds = new ArrayList<>(Arrays.asList(idAndKind.getKinds().split("%")));

        String newIds = new String();
        String newKinds = new String();

        for(int i = 0; i < ids.size(); i++){
            if(ids.get(i).equals(id.getElementId())) continue;
            newIds += ids.get(i);
            newKinds += kinds.get(i);
            if(i != ids.size() - 1){
                newIds += "%";
                newKinds += "%";
            }
        }

        if(newIds.equals("") || newKinds.equals("")){
            newIds = null;
            newKinds = null;
        }
        Updateids updateids = new Updateids();
        updateids.setId(id.getSpecialId());
        updateids.setIds(newIds);
        updateids.setKinds(newKinds);
        special_indexService.specialindexupdateids(updateids);

        return JsonResult.success();
    }
    /**
     * 查询索引表中某一项内含有的所有实体文件（图书/图片/视频资料等）
     */
    @RequestMapping(value = "/root/searchAllElementInIndex", method = { RequestMethod.GET })
    public JsonResult<Object> searchAllElementInIndex(@Param("specialId") @Valid String specialId){
        Updateids idAndKind = special_indexService.getSpecialElementIdAndKind(specialId);
        if(idAndKind.getIds() == null || idAndKind.getKinds() == null){
            return JsonResult.success();
        };
        List<String> ids = new ArrayList<>(Arrays.asList(idAndKind.getIds().split("%")));
        List<String> kinds = new ArrayList<>(Arrays.asList(idAndKind.getKinds().split("%")));
        List<All_max> ret = new ArrayList<>();
        for(int i = 0; i < ids.size() && i < kinds.size(); i++)
            ret.add(getMediumTableElement(ids.get(i), kinds.get(i)));
        return JsonResult.success(ret);
    }

    //根据类型和id从7种介质表获得表项
    public All_max getMediumTableElement(String id, String type){
        All_max ret = new All_max();
        switch (type){
            case "0" :
                Book books = bookService.getBooks_byid(id);
                BeanUtils.copyProperties(books, ret);
                ret.setOftype("图书");
                break;
            case "1" :
                Newspaper nes = newspaperService.getNewspapers_byid(id);
                BeanUtils.copyProperties(nes, ret);
                ret.setOftype("报纸");
                break;
            case "2" :
                File f = fileService.getFiles_byid(id);
                BeanUtils.copyProperties(f, ret);
                ret.setOftype("档案");
                break;
            case "3" :
                Journal jnl = journalService.getJournals_byid(id);
                BeanUtils.copyProperties(jnl, ret);
                ret.setOftype("期刊");
                break;
            case "4" :
                Audio ado = audioService.getAudios_byid(id);
                BeanUtils.copyProperties(ado, ret);
                ret.setOftype("音频");
                break;
            case "5" :
                Picture p = pictureService.getPictures_byid(id);
                BeanUtils.copyProperties(p, ret);
                ret.setOftype("图片");
                break;
            case "6":
                Video v = videoService.getVideos_byid(id);
                BeanUtils.copyProperties(v, ret);
                ret.setOftype("视频");
                break;
            default:
                break;
        }
        return ret;
    }

    /**
     * 管理界面根据长字符串查询特色专题索引表中的所有索引
     */
    @ApiOperation(value = "管理界面根据长字符串查询特色专题索引表中的所有索引")
    @RequestMapping(value = "/root/searchspecialindexall",
            method = {RequestMethod.GET})
    public JsonResult<Object> rootsearchspecialindexall(@Param("searchReciveR") @Valid String searchReciveR) {
        List<String> infos = new ArrayList<>(Arrays.asList(searchReciveR.split(" ")));
        GBList gbList = getGBList(infos);
        List<Special_Index>  special_indexList = special_indexService.getspecialindex(gbList);
        return JsonResult.success(special_indexList);
    }


    /**
     * 首页根据介质范围、类型和长字符串查询所有
     */
    @ApiOperation(value = "首页根据介质范围、类型和长字符串查询所有")
    @RequestMapping(value = "/home/search",
            method = {RequestMethod.GET})
    public JsonResult<Object> homesearch(@Param("scope") @Valid String scope,
                                         @Param("types") @Valid String types,
                                         @Param("infos") @Valid String infos) {
        List<String> info = new ArrayList<>(Arrays.asList(infos.split(" ")));
        GBList gbList = getGBList(info);

        List<Book>  bookList = new ArrayList<>();
        List<Newspaper>  newspaperList = new ArrayList<>();
        List<File>  fileList = new ArrayList<>();
        List<Journal>  journalList = new ArrayList<>();
        List<Audio>  audioList = new ArrayList<>();
        List<Picture>  pictureList = new ArrayList<>();
        List<Video>  videoList = new ArrayList<>();
        List<Kssindex> kssList = new ArrayList<>();
        List<Special_Index> specialList = new ArrayList<>();
        if(scope.equals("default")){
            if(types.equals("8")){
                bookList = bookService.getBooks(gbList);
                newspaperList = newspaperService.getNewspapers(gbList);
                fileList = fileService.getFiles(gbList);
                journalList = journalService.getJournals(gbList);
                audioList = audioService.getAudios(gbList);
                pictureList = pictureService.getPictures(gbList);
                videoList = videoService.getVideos(gbList);
                //kssList = kssService.getKsss(gbList);
                kssList   = kssindexService.getkssindex(gbList);
                specialList = special_indexService.getspecialindex(gbList);
            }
            if(types.contains("0")){
                bookList = bookService.getBooks(gbList);
            }
            if(types.contains("1")){
                newspaperList = newspaperService.getNewspapers(gbList);
            }
            if(types.contains("2")){
                fileList = fileService.getFiles(gbList);
            }
            if(types.contains("3")){
                journalList = journalService.getJournals(gbList);
            }
            if(types.contains("4")){
                audioList = audioService.getAudios(gbList);
            }
            if(types.contains("5")){
                pictureList = pictureService.getPictures(gbList);
            }
            if(types.contains("6")){
                videoList = videoService.getVideos(gbList);
            }
            if(types.contains("7")){
                specialList = special_indexService.getspecialindex(gbList);
            }
            if(types.contains("9")){
                //kssList = kssService.getKsss(gbList);
                kssList = kssindexService.getkssindex(gbList);
            }
        }else if(scope.equals("ofname")||scope.equals("ofkind")||scope.equals("keywords")||scope.equals("mainresponsible1"))
        {
            GBList_scope gbList_scope = new GBList_scope();
            BeanUtils.copyProperties(gbList, gbList_scope);
            gbList_scope.setScope(scope);
            if(types.equals("8")){
                bookList = bookService.getBooks_scope(gbList_scope);
                newspaperList = newspaperService.getNewspapers_scope(gbList_scope);
                fileList = fileService.getFiles_scope(gbList_scope);
                journalList = journalService.getJournals_scope(gbList_scope);
                audioList = audioService.getAudios_scope(gbList_scope);
                pictureList = pictureService.getPictures_scope(gbList_scope);
                videoList = videoService.getVideos_scope(gbList_scope);
                //kssList = kssService.getKsss_scope(gbList_scope);
                if(!scope.equals("ofkind")) {
                    kssList = kssindexService.getKssIndex_scope(gbList_scope);
                    specialList = special_indexService.getSpecialIndexScope(gbList_scope);
                }
            }
            if(types.contains("0")){
                bookList = bookService.getBooks_scope(gbList_scope);
            }
            if(types.contains("1")){
                newspaperList = newspaperService.getNewspapers_scope(gbList_scope);
            }
            if(types.contains("2")){
                fileList = fileService.getFiles_scope(gbList_scope);
            }
            if(types.contains("3")){
                journalList = journalService.getJournals_scope(gbList_scope);
            }
            if(types.contains("4")){
                audioList = audioService.getAudios_scope(gbList_scope);
            }
            if(types.contains("5")){
                pictureList = pictureService.getPictures_scope(gbList_scope);
            }
            if(types.contains("6")){
                videoList = videoService.getVideos_scope(gbList_scope);
            }
            if(types.contains("7") && !scope.equals("ofkind")){
                specialList = special_indexService.getSpecialIndexScope(gbList_scope);
            }
            if(types.contains("9") && !scope.equals("ofkind")) {
                //kssList = kssService.getKsss_scope(gbList_scope);
                kssList = kssindexService.getKssIndex_scope(gbList_scope);
            }
        }
        List<All_max> all_maxList = new ArrayList<>();
        for(Book book:bookList){
            All_max all_max = new All_max();
            BeanUtils.copyProperties(book, all_max);
            all_max.setOftype("图书");
            all_maxList.add(all_max);
        }
        for(Newspaper newspaper:newspaperList){
            All_max all_max = new All_max();
            BeanUtils.copyProperties(newspaper, all_max);
            all_max.setOftype("报纸");
            all_maxList.add(all_max);
        }
        for(File file:fileList){
            All_max all_max = new All_max();
            BeanUtils.copyProperties(file, all_max);
            all_max.setOftype("档案");
            all_maxList.add(all_max);
        }
        for(Journal journal:journalList){
            All_max all_max = new All_max();
            BeanUtils.copyProperties(journal, all_max);
            all_max.setOftype("期刊");
            all_maxList.add(all_max);
        }
        for(Audio audio:audioList){
            All_max all_max = new All_max();
            BeanUtils.copyProperties(audio, all_max);
            all_max.setOftype("音频");
            all_maxList.add(all_max);
        }
        for(Picture picture:pictureList){
            All_max all_max = new All_max();
            BeanUtils.copyProperties(picture, all_max);
            all_max.setOftype("图片");
            all_maxList.add(all_max);
        }
        for(Video video:videoList){
            All_max all_max = new All_max();
            BeanUtils.copyProperties(video, all_max);
            all_max.setOftype("视频");
            all_maxList.add(all_max);
        }
        for(Special_Index s_index : specialList){
            All_max all_max = new All_max();
            BeanUtils.copyProperties(s_index, all_max);
            all_max.setOftype("专题库");
            all_max.setOfkind("特色专题研究");
            all_maxList.add(all_max);
        }
        for(Kssindex kss:kssList){
            All_max all_max = new All_max();
            BeanUtils.copyProperties(kss, all_max);
            all_max.setOftype("口述史");
            all_max.setOfkind("口述史资料");
            all_maxList.add(all_max);
        }
        Home_Search_Return home_search_return = new Home_Search_Return();
        home_search_return.setSum(all_maxList.size());
        home_search_return.setAll_maxList(all_maxList);
        List<Integer> allYearList = new ArrayList<>();
        for(All_max all_max:all_maxList){
            Object Null = null;
            if((Objects.equals(all_max.getPublishedtime(), ""))||(Objects.equals(all_max.getPublishedtime(), Null))){
                continue;
            }
            else {
                Integer intYear = Integer.valueOf(all_max.getPublishedtime().substring(0, 4));
                allYearList.add(intYear);
            }
        }
        Collections.sort(allYearList);
        Map<Integer, Integer> yearMap = calculate(allYearList);
        List<Integer> ofYearList = new ArrayList<>(yearMap.keySet());
        List<Integer> ofNumberList = new ArrayList<>(yearMap.values());
        home_search_return.setOfyearList(ofYearList);
        home_search_return.setOfnumberList(ofNumberList);

        return JsonResult.success(home_search_return);

    }

    public static Map<Integer, Integer> calculate(List<Integer> list){
        Map<Integer, Integer> occur = new LinkedHashMap<>(); //有序MAP
        for (Integer x : list) {
            occur.put(x, occur.getOrDefault(x, 0) + 1);
        }
        return occur;
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
    ///**
    // * 管理界面根据长字符串查询7个介质表中的所有文件
    // */
    //@ApiOperation(value = "管理界面根据长字符串查询7个介质表中的所有文件")
    //@RequestMapping(value = "/root/7searchall",
    //        method = {RequestMethod.GET})
    //public JsonResult<Object> root7searchall(@Param("info") @Valid String info) {
    //    System.out.println(info);
    //    List<String> infos = new ArrayList<>(Arrays.asList(info.split(" ")));
    //    GBList gbList = getGBList(infos);

    //    List<Book_NoUrl>  book_noUrlList = bookService.getBooks_nourl(gbList);
    //    List<Newspaper_NoUrl>  newspaper_noUrlList = newspaperService.getNewspapers_nourl(gbList);
    //    List<File_NoUrl>  file_noUrlList = fileService.getFiles_nourl(gbList);
    //    List<Journal_NoUrl>  journal_noUrlList = journalService.getJournals_nourl(gbList);
    //    List<Audio_NoUrl>  audio_noUrlList = audioService.getAudios_nourl(gbList);
    //    List<Picture_NoUrl>  picture_noUrlList = pictureService.getPictures_nourl(gbList);
    //    List<Video_NoUrl>  video_noUrlList = videoService.getVideos_nourl(gbList);

    //    List<All_NoUrl> all_noUrlList = new ArrayList<>();
    //    for(Book_NoUrl book_noUrl:book_noUrlList){
    //        All_NoUrl all_noUrl = new All_NoUrl();
    //        BeanUtils.copyProperties(book_noUrl, all_noUrl);
    //        all_noUrl.setOftype("图书");
    //        all_noUrlList.add(all_noUrl);
    //    }
    //    for(Newspaper_NoUrl newspaper_noUrl:newspaper_noUrlList){
    //        All_NoUrl all_noUrl = new All_NoUrl();
    //        BeanUtils.copyProperties(newspaper_noUrl, all_noUrl);
    //        all_noUrl.setOftype("报纸");
    //        all_noUrlList.add(all_noUrl);
    //    }
    //    for(File_NoUrl file_noUrl:file_noUrlList){
    //        All_NoUrl all_noUrl = new All_NoUrl();
    //        BeanUtils.copyProperties(file_noUrl, all_noUrl);
    //        all_noUrl.setOftype("档案");
    //        all_noUrlList.add(all_noUrl);
    //    }
    //    for(Journal_NoUrl journal_noUrl:journal_noUrlList){
    //        All_NoUrl all_noUrl = new All_NoUrl();
    //        BeanUtils.copyProperties(journal_noUrl, all_noUrl);
    //        all_noUrl.setOftype("期刊");
    //        all_noUrlList.add(all_noUrl);
    //    }
    //    for(Audio_NoUrl audio_noUrl:audio_noUrlList){
    //        All_NoUrl all_noUrl = new All_NoUrl();
    //        BeanUtils.copyProperties(audio_noUrl, all_noUrl);
    //        all_noUrl.setOftype("音频");
    //        all_noUrlList.add(all_noUrl);
    //    }
    //    for(Picture_NoUrl picture_noUrl:picture_noUrlList){
    //        All_NoUrl all_noUrl = new All_NoUrl();
    //        BeanUtils.copyProperties(picture_noUrl, all_noUrl);
    //        all_noUrl.setOftype("图片");
    //        all_noUrlList.add(all_noUrl);
    //    }
    //    for(Video_NoUrl video_noUrl:video_noUrlList){
    //        All_NoUrl all_noUrl = new All_NoUrl();
    //        BeanUtils.copyProperties(video_noUrl, all_noUrl);
    //        all_noUrl.setOftype("视频");
    //        all_noUrlList.add(all_noUrl);
    //    }
    //    return JsonResult.success(all_noUrlList);
    //}