<script setup lang="ts">
import { ref } from "vue";
import { ArrowRight } from "@element-plus/icons-vue";
import { useRoute, useRouter } from "vue-router";
import { optionPermission } from "../tool/func/func";
import { kindsbackMap } from "../tool/func/func";
import axios from "axios";

const route = useRoute();
const router = useRouter();
const dscArray = ref([]);
const fileds = [
  "关键字",
  "主要责任者1",
  "主要责任者2",
  "次要责任者",
  "出版者",
  "出版地",
  "出版时间",
  "类型",
  "隐私",
  "专题类型",
  "创建时间",
  "更新时间",
];
dscArray.value.push({
  field1: fileds[0],
  value1: String(route.query.keyword),
  field2: fileds[1],
  value2: String(route.query.mainresponsible1),
});
dscArray.value.push({
  field1: fileds[2],
  value1: String(route.query.mainresponsible2),
  field2: fileds[3],
  value2: String(route.query.secondresponsible),
});
dscArray.value.push({
  field1: fileds[4],
  value1: String(route.query.publisher),
  field2: fileds[5],
  value2: String(route.query.publishedplace),
});
dscArray.value.push({
  field1: fileds[6],
  value1: String(route.query.publishedtime),
  field2: fileds[7],
  value2: String(route.query.oftype),
});
dscArray.value.push({
  field1: fileds[8],
  value1: optionPermission[Number(route.query.permission)].label,
  field2: fileds[9],
  value2: String(route.query.ofkind),
});
dscArray.value.push({
  field1: fileds[10],
  value1: String(route.query.create_time),
  field2: fileds[11],
  value2: String(route.query.update_time),
});

async function downloadFile(){
  let name = route.query.ofname;
  let types = route.query.oftype;
  try {
    // 2. 根据文件名下载文件
    const fileResponse = await axios.post(`/downloadfile`, {filename:name,filetype:types},{
      responseType: 'blob',
    });
    const contentDisposition = fileResponse.headers['content-disposition'];
    const type = ref('');
    const filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
    const matches = filenameRegex.exec(contentDisposition);
    if (matches != null && matches[1]) {
      // 去掉文件名前后的引号
      type.value = matches[1].replace(/['"]/g, '');
    }
    name = name +'.'+type.value;
    // 3. 创建下载链接
    const url = window.URL.createObjectURL(new Blob([fileResponse.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', name);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
  } catch (error) {
    console.error('下载失败', error);
  }
}
function gotoReadingPage() {
  if (
    route.query.ofkind == "口述史资料" ||
    route.query.ofkind == "特色专题研究"
  )
    router.push({
      path: "set",
      query: {
        keyword: route.query.keywords,
        mainresponsible1: route.query.mainresponsible1,
        mainresponsible2: route.query.mainresponsible2,
        secondresponsible: route.query.secondresponsible,
        ofkind: route.query.ofkind,
        create_time: route.query.create_time,
        update_time: route.query.update_time,
        picture_url: route.query.picture_url,
        file_url: route.query.file_url,
        ofname: route.query.ofname,
        publisher: route.query.publisher,
        publishedplace: route.query.publishedplace,
        publishedtime: route.query.publishedtime,
        oftype: route.query.oftype,
        permission: route.query.permission,
        resume: route.query.resume,
      },
    });
  else if (route.query.oftype == "视频" || route.query.oftype == "图片")
    router.push({
      path: "video",
      query: {
        keyword: route.query.keywords,
        mainresponsible1: route.query.mainresponsible1,
        mainresponsible2: route.query.mainresponsible2,
        secondresponsible: route.query.secondresponsible,
        ofkind: route.query.ofkind,
        create_time: route.query.create_time,
        update_time: route.query.update_time,
        picture_url: route.query.picture_url,
        file_url: route.query.file_url,
        ofname: route.query.ofname,
        publisher: route.query.publisher,
        publishedplace: route.query.publishedplace,
        publishedtime: route.query.publishedtime,
        oftype: route.query.oftype,
        permission: route.query.permission,
        resume: route.query.resume,
      },
    });
  else if (route.query.oftype == "音频")
    router.push({
      path: "music",
      query: {
        keyword: route.query.keywords,
        mainresponsible1: route.query.mainresponsible1,
        mainresponsible2: route.query.mainresponsible2,
        secondresponsible: route.query.secondresponsible,
        ofkind: route.query.ofkind,
        create_time: route.query.create_time,
        update_time: route.query.update_time,
        picture_url: route.query.picture_url,
        file_url: route.query.file_url,
        ofname: route.query.ofname,
        publisher: route.query.publisher,
        publishedplace: route.query.publishedplace,
        publishedtime: route.query.publishedtime,
        oftype: route.query.oftype,
        permission: route.query.permission,
        resume: route.query.resume,
      },
    });
  else
    router.push({
      path: "pdf",
      query: {
        keyword: route.query.keywords,
        mainresponsible1: route.query.mainresponsible1,
        mainresponsible2: route.query.mainresponsible2,
        secondresponsible: route.query.secondresponsible,
        ofkind: route.query.ofkind,
        create_time: route.query.create_time,
        update_time: route.query.update_time,
        picture_url: route.query.picture_url,
        file_url: route.query.file_url,
        ofname: route.query.ofname,
        publisher: route.query.publisher,
        publishedplace: route.query.publishedplace,
        publishedtime: route.query.publishedtime,
        oftype: route.query.oftype,
        permission: route.query.permission,
        resume: route.query.resume,
      },
    });
}
</script>

<template>
  <div class="parent-back">
    <div class="child-back" style="margin-left: 25%">
      <el-container>
        <el-header class="header">
          <el-breadcrumb :separator-icon="ArrowRight">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item
              :to="{
                path: '/scv',
                query: {
                  type: String(kindsbackMap.get(route.query.oftype)),
                  value: '',
                  according: 'default',
                },
              }"
              >{{ route.query.oftype }}</el-breadcrumb-item
            >
            <el-breadcrumb-item>{{ route.query.ofname }}</el-breadcrumb-item>
          </el-breadcrumb>
        </el-header>
        <el-container style="height: 650px; margin-bottom: 20px">
          <el-aside class="aside" width="720px">
            <div class="fileInfoLine"><sapn>详细信息</sapn></div>
            <el-row class="fileInfo">
              <el-col :span="7">
                <el-image
                  style="width: 90%; height: 75%"
                  :src="'api\\' + route.query.picture_url"
                  fit="fill"
                />
              </el-col>
              <el-col :span="17">
                <el-row style="margin-bottom: 14px">
                  <el-col :span="18"
                    ><span class="title"
                      >标题：{{ route.query.ofname }}</span
                    ></el-col
                  >
                  <el-col :span="6" style="padding-top: 5px"
                    ><span>下载量：</span></el-col
                  >
                </el-row>
                <el-row v-for="item in dscArray">
                  <el-col :span="12"
                    ><span
                      >{{ item.field1 }}:&nbsp;{{ item.value1 }}</span
                    ></el-col
                  >
                  <el-col :span="12"
                    ><span
                      >{{ item.field2 }}:&nbsp;{{ item.value2 }}</span
                    ></el-col
                  >
                </el-row>
                <el-row style="padding-top: 15px">
                  <el-col :span="5" :offset="12"
                    ><button @click="gotoReadingPage">
                      <el-icon><Document /></el-icon>
                      <span style="display: inline-block; margin-left: 8%"
                        >开始阅读</span
                      >
                    </button></el-col
                  >
                  <el-col :span="5" :offset="1">
                    <button>
                      <el-icon><Download /></el-icon>
                      <span style="display: inline-block; margin-left: 8%"
                        @click="downloadFile">文件下载</span
                      >
                    </button></el-col
                  >
                </el-row>
              </el-col>
            </el-row>
            <div class="fileAbs">
              <span class="fileAbs1">简介</span>
              <p>
                &ensp;&ensp;
                {{ route.query.resume }}
              </p>
            </div>
          </el-aside>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<style scoped>
p {
  word-break: normal;
  white-space: pre-warp;
  word-wrap: break-word;
  margin: 0px;
}
.fileAbs {
  padding-top: 20px;
  font-family: "songti", "Arial Narrow", Arial, sans-serif;
  font-weight: 300;
  font-size: 0.9em;
}
.fileAbs1 {
  font-size: 1.1em;
  font-weight: 500;
  color: brown;
}
.aside button {
  height: 35px;
  width: 100px;
  color: brown;
  border-radius: 3px;
  border: 1px solid brown;
  background-color: rgb(255, 255, 255);
}

.aside button:hover {
  height: 35px;
  width: 100px;
  color: rgb(255, 255, 255);
  border-radius: 3px;
  border: 1px solid brown;
  background-color: brown;
}

.line {
  border-top: 1px solid;
}
.header {
  font-size: 1.2em;
  padding-top: 25px;
  height: 60px;
}
.aside {
  padding: 10px;
  padding-left: 20px;
  background-color: rgb(255, 255, 255);
}
.title {
  font-size: 1.3em;
  font-family: "songti", "Arial Narrow", Arial, sans-serif;
  font-weight: 600;
}
.fileInfo {
  font-size: 0.9em;
  border-bottom: 1px solid rgb(211, 211, 211);
}
.fileInfoLine {
  font-size: 1.4em;
  color: brown;
  margin-bottom: 5px;
}
.fileInfo .el-row {
  margin-bottom: 10px;
}
</style>
