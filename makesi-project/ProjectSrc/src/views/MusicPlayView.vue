<script setup lang="ts">
import AudioPlayer from "vue3-audio-player";
import "vue3-audio-player/dist/style.css";
import { ArrowRight } from "@element-plus/icons-vue";
import { ref } from "vue";
import { useRoute } from "vue-router";
import { kindsbackMap } from "../tool/func/func";
import { ElMessage } from "element-plus";

const route = useRoute();
const audioTitle = ref(route.query.ofname);
const file_url = "api\\" + route.query.file_url;
const cover_url = "api\\" + route.query.picture_url;
function start() {
  ElMessage.error("播放发生错误");
}
</script>
<template>
  <div class="parent-back">
    <div class="child-back" style="height: 600px; padding-top: 15px">
      <el-breadcrumb
        :separator-icon="ArrowRight"
        style="color: white; height: 30px; margin-left: 13%"
      >
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
          >音频</el-breadcrumb-item
        >
        <el-breadcrumb-item>{{ audioTitle }}</el-breadcrumb-item>
      </el-breadcrumb>

      <div class="audioPlayBlock">
        <el-container>
          <el-header class="audioHeader">{{ audioTitle }}</el-header>
          <el-container>
            <el-aside width="150px" class="audioAside"></el-aside>
            <el-main class="audioMain">
              <AudioPlayer
                :option="{
                  src: file_url,
                  title: String(route.query.ofname),
                  coverImage: cover_url,
                }"
                @play-error="start"
              />
            </el-main>
          </el-container>
          <el-footer class="audioFooter">
            <span>音频简介</span>
            <p>{{ route.query.resume }}</p>
          </el-footer>
        </el-container>
      </div>
    </div>
  </div>
</template>
<style scoped>
p {
  word-break: normal;
  white-space: pre-warp;
  word-wrap: break-word;
  margin: 0px;
  font-size: 0.8em;
  font-weight: 300;
}
.audioMain {
  background-color: rgb(180, 180, 180);
  margin: 35px;
}
.audioFooter {
  border-bottom-left-radius: 8x;
  border-bottom-right-radius: 8px;
  border-top: 1px solid rgb(195, 195, 195);
  height: 150px;
  padding-top: 5px;
}
.audioHeader {
  height: 40px;
  padding-top: 5px;
}
.audioAside {
  border-right: 1px solid rgb(222, 222, 222);
}
.audioHeader {
  border-top: 1px solid rgb(230, 230, 230);
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
  border-bottom: 1px solid rgb(208, 208, 208);
}
.audioPlayBlock {
  width: 1050px;
  background-color: white;
  border-radius: 8px;
  margin-left: 12%;
}
</style>
