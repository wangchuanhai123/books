<template>
  <el-row class="pdfBlock">
    <el-breadcrumb
      :separator-icon="ArrowRight"
      style="color: white; margin-left: 14%; margin-top: 20px; height: 30px"
    >
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item
        :to="{
          path: 'scv',
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

    <div ref="pdfFull" class="pdfPos">
      <el-container class="pdfBack">
        <el-header class="header">
          <el-row style="font-size: 1.2em">
            <el-col :span="1" :offset="10"
              ><el-icon style="padding-top: 14px" @click="zoomPdf"
                ><Plus /></el-icon
            ></el-col>
            <el-col :span="1"
              ><el-icon style="padding-top: 14px" @click="shrinkPdf"
                ><Minus /></el-icon
            ></el-col>
            <el-col :span="1" style="padding-left: 10px"
              ><el-icon style="padding-top: 14px" @click="subPage"
                ><ArrowLeft /></el-icon
            ></el-col>
            <el-col :span="2" style="font-size: 0.8em">
              <el-input
                v-model="inputPage"
                @change="inputChange"
                style="padding-top: 0px; width: 45px; height: 70%"
              ></el-input>
              <span style="display: inline-block; padding-top: 14px">
                / {{ pageCount }}</span
              >
            </el-col>
            <el-col :span="1"
              ><el-icon style="padding-top: 14px" @click="addPage"
                ><ArrowRight /></el-icon
            ></el-col>
            <el-col :span="1"
              ><el-icon @click="fullScreen" style="padding-top: 14px"
                ><FullScreen /></el-icon
            ></el-col>
          </el-row>
        </el-header>
        <el-container>
          <el-aside class="aside" width="200px">
            <div class="index">
              <span
                style="
                  display: inline-block;
                  margin-top: 3px;
                  margin-bottom: 4px;
                  margin-left: 77px;
                "
                >索引</span
              >
            </div>
            <div class="indexBtn" :style="{ height: indexBtnHeight }">
              <button
                @click="gotoPage(item)"
                class="bar"
                :class="{ barSelect: item == page }"
                v-for="item in pageCount"
              >
                第{{ item }}页
              </button>
            </div>
          </el-aside>
          <el-main
            class="pdfShow"
            :class="{ pdfShow1: pdfShow1F, pdfShow2: isFull }"
            :style="{ height: pdfShowHeight }"
          >
            <vue-pdf-embed
              ref="pdfRef"
              :width="width"
              :height="height"
              :source="pdfSource"
              :page="page"
              :scale="pdfScale"
              @password-requested="handlePasswordRequest"
              @rendered="handleDocumentRender"
            />
          </el-main>
        </el-container>
      </el-container>
    </div>
  </el-row>
</template>

<script>
import VuePdfEmbed from "vue-pdf-embed";
import { ArrowRight } from "@element-plus/icons-vue";
import screenFull from "screenfull";
import { useRoute } from "vue-router";
import { kindsbackMap } from "../tool/func/func";

// OR THE FOLLOWING IMPORT FOR VUE 2
// import VuePdfEmbed from 'vue-pdf-embed/dist/vue2-pdf-embed'

export default {
  components: {
    VuePdfEmbed,
  },
  setup() {
    const route = useRoute();
    return {
      ArrowRight,
      route,
      kindsbackMap,
    };
  },
  data() {
    return {
      pdfShow1F: false,
      isFull: false,
      indexBtnHeight: 665 + "px",
      pdfShowHeight: 700 + "px",
      screenWidth: document.body.clientWidth,
      fullScreenPadding: 200 + "px",
      //screenHeight: document.body.clientHeight,
      pdfMaxWidth: 1920,
      pdfMinWidth: 700,
      pdfFullWidth: 1013,
      pdfAlmostFullWidth: 850,
      width: 500,
      height: 600,
      isLoading: true,
      oldClickCount: 1,
      clickCount: 0,
      inputPage: 1,
      page: 1,
      pageCount: 1,
      pdfScale: 2,
      pdfSource: "api\\" + this.route.query.file_url,
      showAllPages: true,
    };
  },
  watch: {
    showAllPages() {
      this.page = this.showAllPages ? null : 1;
    },
  },
  methods: {
    fullScreen() {
      if (screenFull.isEnabled) {
        this.isFull = false;
        this.pdfShow1F = false;
        this.indexBtnHeight = 930 + "px";
        this.pdfShowHeight = 960 + "px";
        this.pdfMaxWidth = 2000;
        this.pdfMinWidth = 900;
        this.width = 700;
        this.pdfScale = 3;
        this.pdfFullWidth =
          this.screenWidth - 300 > 1013 ? this.screenWidth - 400 : 1013;
        this.pdfAlmostFullWidth =
          this.screenWidth - 750 > 850 ? this.screenWidth - 750 : 850;
        screenFull.request(this.$refs.pdfFull);
      }
      if (screenFull.isFullscreen) {
        screenFull.exit();
        this.backFullScreen();
      }
      screenFull.onchange(this.backFullScreen);
    },
    backFullScreen() {
      if (!screenFull.isFullscreen) {
        this.indexBtnHeight = 663 + "px";
        this.pdfShowHeight = 700 + "px";
        this.width = 500;
        this.pdfScale = 2;
        this.isFull = false;
        this.pdfShow1F = false;
        this.pdfMaxWidth = 1920;
        this.pdfMinWidth = 700;
      }
    },
    handleDocumentRender(args) {
      //console.log(args)
      this.isLoading = false;
      this.pageCount = this.$refs.pdfRef.pageCount;
      //console.log(this.$refs.pdfRef.pageCount)
      //console.log(this.$refs.pdfRef.scale)
    },
    handlePasswordRequest(callback, retry) {
      callback(prompt(retry ? "Enter password again" : "Enter password"));
    },
    subPage() {
      if (this.page > 1) this.page--;
      this.inputPage = this.page;
    },
    addPage() {
      if (this.page < this.pageCount) this.page++;
      this.inputPage = this.page;
    },
    inputChange() {
      while (this.page < this.inputPage) this.page++;
      while (this.page > this.inputPage) this.page--;
    },
    zoomPdf() {
      if (this.oldClickCount > this.clickCount) {
        setTimeout(() => {
          this.oldClickCount += 1;
        }, 500);
        if (this.width <= this.pdfMaxWidth) {
          this.width += 200;
          if (this.pdfScale < 12) this.pdfScale += 1;
          if (this.width >= this.pdfAlmostFullWidth) this.pdfShow1F = true;
          if (this.width >= this.pdfFullWidth) {
            this.isFull = true;
            this.pdfShow1F = false;
          }
        }
        if (this.oldClickCount >= 100) {
          this.oldClickCount = 1;
        }
        this.clickCount = this.oldClickCount;
      }
    },
    shrinkPdf() {
      if (this.oldClickCount > this.clickCount) {
        setTimeout(() => {
          this.oldClickCount += 1;
        }, 500);
        if (this.width >= this.pdfMinWidth) {
          this.width -= 200;
          if (this.pdfScale > 2) this.pdfScale -= 1;
          if (this.width < this.pdfFullWidth) this.isFull = false;
          if (this.width < this.pdfAlmostFullWidth) this.pdfShow1F = false;
        }
        if (this.oldClickCount >= 100) {
          this.oldClickCount = 1;
        }
        this.clickCount = this.oldClickCount;
      }
    },
    gotoPage(dst) {
      this.page = dst;
      this.inputPage = dst;
    },
  },
};
</script>
<style scoped>
.pdfPos {
  height: 760px;
  width: 72%;
  margin-bottom: 30px;
  margin-top: 5px;
  margin-right: 14%;
  margin-left: 14%;
}
.index {
  border-bottom: 1px solid rgb(113, 113, 113);
  color: white;
  background-color: rgb(181, 181, 181);
  height: 30px;
}
.header {
  background-color: rgba(66, 66, 66, 0.968);
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
  border: 2px solid rgb(62, 62, 62);
  border-bottom: 0px;
  box-shadow: 5px;
  color: white;
}
.aside {
  border-left: 2px solid rgb(101, 101, 101);
  border-top: 1px solid rgba(0, 0, 0, 0.972);
  border-bottom: 2px solid rgb(134, 134, 134);
}
.indexBtn {
  overflow-y: auto;
}
.bar {
  border: 0;
  border-bottom: 1px solid;
  border-color: rgb(157, 157, 157);
  height: 30px;
  width: 100%;
  margin: 0;
  padding: 0;
  background-color: white;
}

.barSelect {
  border: 0;
  border-bottom: 1px solid;
  border-color: rgb(157, 157, 157);
  height: 30px;
  width: 100%;
  margin: 0;
  padding: 0;
  background-color: rgb(241, 241, 241);
}
.bar:hover {
  border: 0;
  border-bottom: 1px solid;
  border-color: rgb(157, 157, 157);
  height: 30px;
  width: 100%;
  margin: 0;
  padding: 0;
  background-color: rgb(241, 241, 241);
}

.pdfBlock {
  background-color: rgb(238, 238, 238);
}

.app-header {
  padding: 16px;
  box-shadow: 0 2px 8px 4px rgba(0, 0, 0, 0.1);
  background-color: #555;
  color: #ddd;
}
.pdfShow {
  overflow-y: auto;
  border-right: 2px solid rgb(132, 132, 132);
  border-top: 1px solid;
  border-bottom: 2px solid rgb(128, 128, 128);
  border-left: 1px solid;
  padding: 0px;
  box-shadow: 1 1 0 4px rgb(176, 176, 176);
  padding-left: 20%;
  padding-right: 20%;
}

.pdfShow2 {
  padding-left: 0%;
}
.pdfShow1 {
  padding-left: 10%;
}
.right {
  float: right;
}
.vue-pdf-embed > div {
  box-shadow: 0 2px 8px 4px rgba(0, 0, 0, 0.1);
}
.pdfBack {
  background-color: rgba(66, 66, 66, 0.968);
  border-radius: 8px;
}
</style>
