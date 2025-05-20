<script setup lang="ts">
import ResultBookCard from "../components/SearchResoultBookCard.vue"
import { ref, computed } from 'vue';
import { useRoute } from "vue-router";
import { Article } from '../tool/classDef/classDef'
import { ArrowRight } from '@element-plus/icons-vue'
import { ElLoading } from 'element-plus'
import { kindsbackMap } from '../tool/func/func';
import axios from 'axios'
axios.defaults.baseURL = '/api'

const route = useRoute();
const articleAbstracts = ref<Article[]>([])
const inscription = ref(route.query.notes); //题记
const hasMoreText = computed(() => {
    return brief.value.length > 144;
});
const brief  = ref(route.query.resume); //简介

const isMore   = ref(false);
const isOriHis = ref(String(route.query.oftype) == "口述史");
function isMoreChange(){
    isMore.value = !isMore.value;
}
async function sortByPubTime(){
    const loadingDOMobj = document.getElementById('loading'); 
    const loading       = ElLoading.service({
        target: loadingDOMobj
    });
    await articleAbstracts.value.sort((v1, v2) => {
        const yearV1 = Number(v1.publishedtime.substring(0, 4));
        const yearV2 = Number(v2.publishedtime.substring(0, 4));
        let mv1: number, mv2: number;
        let d1: number, d2: number;
        if(v1.publishedtime[6] == '-'){
            mv1 = Number(v1.publishedtime.substring(5, 6));
            d1 = Number(v1.publishedtime.substring(7, 9));
        }
        else{
            mv1 = Number(v1.publishedtime.substring(5, 7));
            d1 = Number(v1.publishedtime.substring(8, 10));
        }
        if(v2.publishedtime[6] == '-'){
            mv2 = Number(v2.publishedtime.substring(5, 6));
            d2 = Number(v2.publishedtime.substring(7, 9));
        }
        else{
            mv2 = Number(v2.publishedtime.substring(5, 7));
            d2 = Number(v2.publishedtime.substring(8, 10));
        }
        if(yearV1 > yearV2)
            return -1;
        else if(yearV1 == yearV2){
            if(mv1 > mv2)
                return -1;
            else if(mv1 == mv2){
                if(d1 > d2)
                    return -1;
                else
                    return 1;
            }
            else
                return 1;
        }
        else
            return 1;
    });
    loading.close();
}
async function sortByUpTime(){
    const loadingDOMobj = document.getElementById('loading'); 
    const loading       = ElLoading.service({
        target: loadingDOMobj
    });
    await articleAbstracts.value.sort((v1, v2) => {
        const yearV1 = Number(v1.update_time.substring(0, 4));
        const yearV2 = Number(v2.update_time.substring(0, 4));
        let mv1: number, mv2: number;
        let d1: number, d2: number;
        if(v1.update_time[6] == '-'){
            mv1 = Number(v1.update_time.substring(5, 6));
            d1 = Number(v1.update_time.substring(7, 9));
        }
        else{
            mv1 = Number(v1.update_time.substring(5, 7));
            d1 = Number(v1.update_time.substring(8, 10));
        }
        if(v2.update_time[6] == '-'){
            mv2 = Number(v2.update_time.substring(5, 6));
            d2 = Number(v2.update_time.substring(7, 9));
        }
        else{
            mv2 = Number(v2.update_time.substring(5, 7));
            d2 = Number(v2.update_time.substring(8, 10));
        }
        if(yearV1 > yearV2)
            return -1;
        else if(yearV1 == yearV2){
            if(mv1 > mv2)
                return -1;
            else if(mv1 == mv2){
                if(d1 > d2)
                    return -1;
                else
                    return 1;
            }
            else
                return 1;
        }
        else
            return 1;
    });
    loading.close();
}
async function sortByDownTimes(){
    const loadingDOMobj = document.getElementById('loading'); 
    const loading       = ElLoading.service({
        target: loadingDOMobj
    });
    loading.close();
}
</script>
<template>
<div class="parent-back">
    <div class="child-back">
    <div style="margin-left: 21%; margin-top: 20px;">
        <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/scv', query: { type: String(kindsbackMap.get(route.query.oftype)), value:'', according: 'default', }}">{{ route.query.oftype }}</el-breadcrumb-item>
        <el-breadcrumb-item>{{ route.query.ofname }}</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <div class="back">
        <el-row style="margin-bottom: 60px;">
            <el-col :span="6"></el-col>
            <el-col :span="18">
                <div :class="[!isOriHis ? 'title' : '']" style="font-size: 1.2em; font-weight: 500;">
                    <span>{{ route.query.ofname }}</span>
                </div>
                <div v-if="isOriHis">
                    <p>&ensp;&ensp;&ensp;&ensp;{{inscription}}</p>
                </div>
                <div v-if="isOriHis" class="title2">
                    <span>——题记</span>
                </div>
                <div class='more-wrapper'>
                    <div :class="[isMore ? 'no-more-text' : 'more-text']">
                        <div v-if="hasMoreText" :class="[isMore ? 'no-more-btn' : 'more-btn' ]"><button @click="isMoreChange" class="btn">{{ isMore ? '收起' : '展开' }}</button></div>
                        <span class="info">简介:&ensp;&ensp;</span>{{brief}}
                    </div>
                </div>
                <el-row>
                    <el-col :span="8"><span>隐私: {{route.query.permission}}</span></el-col>
                    <el-col :span="8"><span>更新时间: {{route.query.update_time}}</span></el-col>
                    <el-col :span="8"><span>创建时间:{{route.query.create_time}}</span></el-col>
                </el-row>
            </el-col>
        </el-row>
        <div class="contentInfo"><span>{{isOriHis ? '口述史资料内容' : '特色专题内容' }}</span></div>
        <div class="file">
            <div class="listResultShow" v-if="!isOriHis">
                <button @click="sortByPubTime">按照出版时间</button>
                <div class="dividLine"></div>
                <button @click="sortByUpTime">按照上传时间</button>
                <div class="dividLine"></div>
                <button @click="sortByDownTimes">按照下载次顺序</button>
            </div>
            <ResultBookCard v-for="item in articleAbstracts" :article="item" />
        </div>
    </div>
    </div>
</div>
</template>
<style scoped>
p{
    word-break:normal;
    white-space:pre-warp;
    word-wrap:break-word;
}
.contentInfo{
    padding-left: 8px;
    font-size: 1.3em;
    color: brown;
    padding-bottom: 10px;
    border-bottom: 2px solid brown;
}
.file{
    margin-top: 15px;
    margin-bottom: 40px;
}
.listResultShow{
    width: 100%;
    border-bottom: 1px solid rgb(222, 222, 222);
    padding-bottom: 10px;
}
.listResultShow button{
    border: 0;
    background-color:rgba(255, 255, 255, 0.988);
}

.listResultShow button:hover{
    color:brown;
}
.info{
    font-size: 0.9em;
    font-weight: 500;
    color: brown;
}
.btn{
    border:0;
    height: 18px;
    font-size: 0.7em;
    background-color: rgba(9, 142, 220, 0.917);
    color: white;
    border-radius: 4px;
}
.more-wrapper{
    display: flex;
    width: 100%;
    margin-bottom: 15px;
}
.more-btn{
    float: right;
    clear: both;
     /*其他装饰样式*/
}
.dividLine{
    display: inline-block;
    height: 12px;
    width: 2px;
    border-right: 2px solid black;
    margin-left: 15px;
    margin-right: 15px;
}
.no-more-btn{
    float: right;
    clear: both;
}

.more-text {
    overflow: hidden;
    line-height: 1.5;
    max-height: 4.5em;
 }
 .no-more-text{
    overflow: hidden;
    line-height: 1.5;
    max-height: none;
 }
 .more-text::before {
    content: '';
    float: right;
    width: 0;
    height: calc(100% - 20px); 
    /*100%减去一个按钮的高度即可*/
    background: red;
}
.no-more-text::before{
    content: '';
    float: right;
    width: 0;
    height: calc(100% - 20px); 
    /*100%减去一个按钮的高度即可*/
    background: red;
}
 .more-btn::before{
    content: '…';
    color: rgb(209, 22, 22);
    font-size: 14px;
    left: -10px;
    transform: translateX(-100%);
}
.title2{
    float: right;
    display: flex;
    justify-content: right;
}
.title{
    margin-bottom: 15px;
}
.info{
    min-height: 5.9em;
}
.back{
    margin-top: 10px;
    margin-left: 20%;
    margin-right: 20%;
    padding: 15px;
    padding-left: 40px;
    padding-top: 20px;
    background-color: white;
    font-size: 0.9em;
    font-weight: 300;
    font-family: 'songti', Arial, sans-serif;
    padding-right: 30px;
    margin-bottom: 30px;
    min-height: 600px;
}
</style>