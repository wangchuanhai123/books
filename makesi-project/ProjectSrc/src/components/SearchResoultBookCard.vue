<script setup lang="ts">
import { ServerEntity } from '../tool/classDef/classDef'
import type { PropType } from 'vue';
import { useRouter } from 'vue-router';
import { optionPermission } from '../tool/func/func';
const router = useRouter();
const props = defineProps({
    article: Object as PropType<ServerEntity>,
})

function gotoDetailPage(){
    if(props.article.ofkind == '口述史资料' || props.article.ofkind == '特色专题研究')
        router.push({
            path: 'set',
            query: { notes: props.article.notes, create_time: props.article.create_time, update_time: props.article.update_time,
                     picture_url: props.article.picture_url, ofname: props.article.ofname,permission: props.article.permission,
                     resume: props.article.resume,oftype: props.article.oftype}
        });
    else
        router.push({
            path: 'detail',
            query: { keyword: props.article.keywords, mainresponsible1: props.article.mainresponsible1,
                    mainresponsible2: props.article.mainresponsible2, secondresponsible: props.article.secondresponsible,
                    ofkind: props.article.ofkind, create_time: props.article.create_time, update_time: props.article.update_time,
                    picture_url: props.article.picture_url, file_url: props.article.file_url, ofname: props.article.ofname,
                    publisher: props.article.publisher, publishedplace: props.article.publishedplace, publishedtime: props.article.publishedtime,
                    oftype: props.article.oftype, permission: props.article.permission, resume: props.article.resume},
        });
}

function gotoReadingPage(){
    if(props.article.ofkind == '口述史资料' || props.article.ofkind == '特色专题研究')
        router.push({
            path: 'set',
            query: { keyword: props.article.keywords, mainresponsible1: props.article.mainresponsible1,
                    mainresponsible2: props.article.mainresponsible2, secondresponsible: props.article.secondresponsible,
                    ofkind: props.article.ofkind, create_time: props.article.create_time, update_time: props.article.update_time,
                    picture_url: props.article.picture_url, file_url: props.article.file_url, ofname: props.article.ofname,
                    publisher: props.article.publisher, publishedplace: props.article.publishedplace, publishedtime: props.article.publishedtime,
                    oftype: props.article.oftype, permission: props.article.permission, resume: props.article.resume},
        });
    else if(props.article.oftype == '视频'|| props.article.oftype == '图片')
        router.push({
            path: 'video',
            query: { keyword: props.article.keywords, mainresponsible1: props.article.mainresponsible1,
                    mainresponsible2: props.article.mainresponsible2, secondresponsible: props.article.secondresponsible,
                    ofkind: props.article.ofkind, create_time: props.article.create_time, update_time: props.article.update_time,
                    picture_url: props.article.picture_url, file_url: props.article.file_url, ofname: props.article.ofname,
                    publisher: props.article.publisher, publishedplace: props.article.publishedplace, publishedtime: props.article.publishedtime,
                    oftype: props.article.oftype, permission: props.article.permission, resume: props.article.resume},
        });
    else if(props.article.oftype == '音频')
        router.push({
            path: 'music',
            query: { keyword: props.article.keywords, mainresponsible1: props.article.mainresponsible1,
                    mainresponsible2: props.article.mainresponsible2, secondresponsible: props.article.secondresponsible,
                    ofkind: props.article.ofkind, create_time: props.article.create_time, update_time: props.article.update_time,
                    picture_url: props.article.picture_url, file_url: props.article.file_url, ofname: props.article.ofname,
                    publisher: props.article.publisher, publishedplace: props.article.publishedplace, publishedtime: props.article.publishedtime,
                    oftype: props.article.oftype, permission: props.article.permission, resume: props.article.resume},
        });
    else
        router.push({
            path: 'pdf',
            query: { keyword: props.article.keywords, mainresponsible1: props.article.mainresponsible1,
                    mainresponsible2: props.article.mainresponsible2, secondresponsible: props.article.secondresponsible,
                    ofkind: props.article.ofkind, create_time: props.article.create_time, update_time: props.article.update_time,
                    picture_url: props.article.picture_url, file_url: props.article.file_url, ofname: props.article.ofname,
                    publisher: props.article.publisher, publishedplace: props.article.publishedplace, publishedtime: props.article.publishedtime,
                    oftype: props.article.oftype, permission: props.article.permission, resume: props.article.resume},
        });
}
</script>

<template>
<div class="dividHorizontal"></div>
<el-row class="list" style="height: 230px; width: 94%;">
    <el-col :span="5">
        <el-image style="height:80%; width: 90%;" :src="'api\\' + article.picture_url" fit="fill" />
    </el-col>
    <el-col :span="19">
        <el-row style="height: 14%;">
            <el-col :span="19" style="font-weight:bold;">{{article.ofname}}</el-col>
            <el-col :span="5">
                <el-row>
                    <el-col :span="20"><span  style="font-weight:bold; display: inline-block; font-size: 0.7em; color:brown">
                        下载量: 
                    </span></el-col>
                    <el-col :span="4">
                    <el-icon size="1.3em" style="color:brown; margin-top: 2%;"><Reading /></el-icon>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
        <el-row style="font-weight:bold; height: 14%;">
            <span>关键词: {{article.keywords}}</span>
        </el-row>
        <el-row style="height: 14%; font-size:0.8em;">
            <el-col :span="13">主要责任者: {{article.mainresponsible1}}</el-col>
            <el-col :span="11">次要责任者: {{article.secondresponsible == "" ? "--" : article.secondresponsible}}</el-col>
        </el-row>
        <el-row style="height: 14%; font-size:0.8em;">
            <el-col :span="13">出版者: {{article.publisher}}</el-col>
            <el-col :span="11">出版地: {{article.publishedplace}}</el-col>
        </el-row>
        <el-row style="height: 14%; font-size:0.8em;">
            <el-col :span="13">出版时间: {{article.publishedtime}}</el-col>
            <el-col :span="11">隐私: {{optionPermission[Number(article.permission)].label}}</el-col>
        </el-row>
        <el-row style="height: 14%; font-size:0.8em;">
            <el-col :span="13">所述专题: {{article.ofkind}}</el-col>
            <el-col :span="11">类型: {{article.oftype}}</el-col>
        </el-row>
        <el-row style="height: 18%; margin-bottom: 3%;">
            <el-col :span="4" :offset="16">
            <button @click="gotoDetailPage">
                <el-icon><ZoomIn /></el-icon>
                <span style="display: inline-block; margin-left: 8%;">查看详情</span>
            </button></el-col>
            <el-col :span="4">
            <button @click="gotoReadingPage">
                <el-icon><Document /></el-icon>
                <span style="display: inline-block; margin-left: 8%;">开始阅读</span>
            </button></el-col>
        </el-row>
    </el-col>
</el-row>
</template>
<style scoped>
.list button{
    height: 80%;
    width: 90%;
    color: brown;
    border-radius: 3px;
    border: 1px solid brown;
    background-color: rgb(255, 255, 255);
}

.list button:hover{
    height: 90%;
    width: 90%;
    color: rgb(255, 255, 255);
    border-radius: 3px;
    border: 1px solid brown;
    background-color:brown;
}

.dividHorizontal{
    height: 1px;
    border-top: 1px solid rgb(200, 200, 200);
    width: 94%;
    margin-top: 9px;
    margin-bottom: 15px;
}
</style>