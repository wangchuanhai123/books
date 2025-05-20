<template>
  <div class="parent-back">
    <div class="child-back" style="height: 800px">
      <el-breadcrumb
        :separator-icon="ArrowRight"
        style="color: white; height: 30px; margin-left: 13%; padding-top: 10px"
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
          >{{ isPicture ? "图片" : "视频" }}</el-breadcrumb-item
        >
        <el-breadcrumb-item>{{ route.query.ofname }}</el-breadcrumb-item>
      </el-breadcrumb>

      <div class="videoPlayBlock">
        <el-container>
          <el-header class="videoHeader">{{ videoTitle }}</el-header>
          <el-container>
            <el-aside width="150px" class="videoAside"></el-aside>
            <el-main class="videoMain">
              <vue3VideoPlay
                v-if="!isPicture"
                ref="video"
                v-bind="options"
                :poster="picture_url"
              />
              <el-image
                style="height: 90%; width: 90%"
                v-if="isPicture"
                :src="file_url"
                fit="contain"
              />
            </el-main>
          </el-container>
          <el-footer class="videoFooter">
            <span>视频简介</span>
            <p>{{ resume }}</p></el-footer
          >
        </el-container>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import { ArrowRight } from "@element-plus/icons-vue";
import { ref, computed } from "vue";
import { useRoute } from "vue-router";
import { kindsbackMap } from "../tool/func/func";

const route = useRoute();

const videoTitle = ref(route.query.ofname);
const picture_url = ref("api\\" + route.query.picture_url);
const file_url = ref("api\\" + route.query.file_url);
const resume = ref(route.query.resume);
const isPicture = computed(() => {
  return route.query.oftype == "图片";
});
const options = reactive({
  width: "860px", //播放器高度
  height: "500px", //播放器高度
  color: "#409eff", //主题色
  title: route.query.ofname, //视频名称
  src: file_url.value, //视频源
  muted: false, //静音
  webFullScreen: false,
  speedRate: ["0.75", "1.0", "1.25", "1.5", "2.0"], //播放倍速
  autoPlay: false, //自动播放
  loop: false, //循环播放
  mirror: false, //镜像画面
  ligthOff: false, //关灯模式
  volume: 0.3, //默认音量大小
  control: true, //是否显示控制
  controlBtns: [
    "audioTrack",
    "quality",
    "speedRate",
    "volume",
    "setting",
    "pip",
    "pageFullScreen",
    "fullScreen",
  ], //显示所有按钮,
});
</script>

<style scoped>
p {
  word-break: normal;
  white-space: pre-warp;
  word-wrap: break-word;
  margin: 0px;
  font-size: 0.8em;
  font-weight: 300;
}
.videoFooter {
  border-bottom-left-radius: 8x;
  border-bottom-right-radius: 8px;
  border-top: 1px solid rgb(195, 195, 195);
  height: 150px;
}
.videoHeader {
  height: 40px;
  padding-top: 5px;
}
.videoAside {
  border-right: 1px solid rgb(222, 222, 222);
}
.back {
  background-color: rgb(238, 238, 238);
  padding-top: 15px;
}
.videoHeader {
  border-top: 1px solid rgb(230, 230, 230);
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
  border-bottom: 1px solid rgb(208, 208, 208);
}
.videoPlayBlock {
  width: 1050px;
  background-color: white;
  border-radius: 8px;
  margin-left: 12%;
}
</style>
