<script setup lang="ts">
import Select from "../components/Select.vue";
import Input from "../components/Input.vue";
import SearchBtn from "../components/SearchBtn.vue";
import SearchKinds from "../components/SearchKinds.vue";
import { RouterLink, useRouter } from "vue-router";
import RecPagination from "../components/RecPagination.vue";
import Carousel from "../components/Carousel.vue";
import { useStore } from "vuex";
import { ref } from "vue";
import { kindsbackMap } from "../tool/func/func";
const store = useStore();
const router = useRouter();
let types = ""; //搜索类型，图书、报纸...
let inputValue = ""; //用户输入搜索值
let searchDepend = "default"; //题名，关键词，责任者...
function enterSearch() {
  searchBtnClick();
}
function searchBtnClick() {
  router.push({
    path: "scv",
    query: { value: inputValue, type: types, according: searchDepend },
  });
}
function inputChange(changeValue) {
  inputValue = changeValue;
  //console.log("input value: " + inputValue)
}
function selectDepd(eValue) {
  searchDepend = eValue;
  //console.log("eValue: " + eValue)
}
function selectKindsChange(e) {
  types = "";
  for (let i = 0; i < e.length; i++) {
    types += String(kindsbackMap.get(e[i]));
  }
  //console.log("types: " + types)
}
const type = ref("pdf");

const literLine = ref(store.getters.getLiter);

const special = ref(store.getters.getSpecial);
</script>

<template>
  <div class="parent-back">
    <el-row class="searchBlock child-back">
      <div class="search">
        <el-row>
          <el-col :span="3"><Select @changes="selectDepd"></Select></el-col>
          <el-col :span="18"
            ><Input
              @keyup.enter.native="enterSearch"
              @change="inputChange"
            ></Input
          ></el-col>
          <el-col :span="2"
            ><SearchBtn @click="searchBtnClick"></SearchBtn
          ></el-col>
        </el-row>
        <el-row style="padding-left: 2.5%">
          <el-col :span="22"
            ><SearchKinds @change="selectKindsChange"
          /></el-col>
        </el-row>
      </div>
    </el-row>
  </div>

  <div class="parent-back">
    <el-row class="gotoBlock child-back">
      <el-row class="gotoPos pos">
        <el-col :span="3"
          ><div class="routerLinkBlock">
            <el-icon><Reading /></el-icon
            ><RouterLink to="/scv?value=&type=0&according=default"
              >图书</RouterLink
            >
          </div></el-col
        >
        <el-col :span="3"
          ><div class="routerLinkBlock">
            <el-icon><Document /></el-icon
            ><RouterLink to="/scv?value=&type=1&according=default"
              >报纸</RouterLink
            >
          </div></el-col
        >
        <el-col :span="3"
          ><div class="routerLinkBlock">
            <el-icon><Folder /></el-icon
            ><RouterLink to="/scv?value=&type=2&according=default"
              >档案</RouterLink
            >
          </div></el-col
        >
        <el-col :span="4"
          ><div class="routerLinkBlock">
            <el-icon><DocumentCopy /></el-icon
            ><RouterLink to="/scv?value=&type=3&according=default"
              >期刊</RouterLink
            >
          </div></el-col
        >
        <el-col :span="3"
          ><div class="routerLinkBlock">
            <el-icon><Headset /></el-icon
            ><RouterLink to="/scv?value=&type=4&according=default"
              >音频</RouterLink
            >
          </div></el-col
        >
        <el-col :span="3"
          ><div class="routerLinkBlock">
            <el-icon><Picture /></el-icon
            ><RouterLink to="/scv?value=&type=5&according=default"
              >图片</RouterLink
            >
          </div></el-col
        >
        <el-col :span="3"
          ><div class="routerLinkBlock">
            <el-icon><VideoPlay /></el-icon
            ><RouterLink to="/scv?value=&type=6&according=default"
              >视频</RouterLink
            >
          </div></el-col
        >
      </el-row>
    </el-row>
  </div>

  <div class="parent-back">
    <el-row class="redColorBlock child-back">
      <div class="pos">
        <el-row>
          <el-col :span="6"><h class="eachLineTitle">口述史资料</h></el-col>
          <el-col :offset="15" :span="3"
            ><button class="moreBtn">
              <RouterLink
                style="color: brown; font-size: 1em"
                to="/scv?value=&type=9&according=default"
                >查看更多</RouterLink
              >
            </button></el-col
          >
        </el-row>
        <div class="dividLink"></div>
        <div class="redColorBooks">
          <div style="margin-top: 4%; margin-left: 4%; margin-right: 4%">
            <Carousel></Carousel>
          </div>
        </div>
      </div>
    </el-row>
  </div>

  <div class="parent-back">
    <el-row class="newBooksBlock child-back">
      <div class="pos">
        <el-row>
          <el-col :span="6"><h class="eachLineTitle">文献资料</h></el-col>
          <el-col :offset="15" :span="3"
            ><button class="moreBtn">
              <RouterLink
                style="color: brown; font-size: 1em"
                to="/scv?value=文献资料&type=8&according=ofkind"
                >查看更多</RouterLink
              >
            </button></el-col
          >
        </el-row>

        <div class="dividLink"></div>
        <div class="newBooks">
          <el-row style="padding-left: 4%; padding-top: 1%">
            <el-col
              :span="4"
              style="height: 240px; margin-left: 15px; margin-right: 8px"
              v-for="newBook in literLine.slice(0, 5)"
            >
              <div class="newBookImgBack">
                <el-image
                  style="height: 170px; width: 135px"
                  :src="'api//' + newBook.picture_url"
                  fit="cover"
                />
              </div>
              <div class="newBookName">
                <span class="newBookTitle" style="display: inline-block">{{
                  newBook.ofname
                }}</span>
              </div>
            </el-col>
          </el-row>
          <el-row style="padding-left: 4%; padding-top: 1%">
            <el-col
              :span="4"
              style="height: 240px; margin-left: 15px; margin-right: 8px"
              v-for="newBook in literLine.slice(5, 10)"
            >
              <div class="newBookImgBack">
                <el-image
                  style="height: 170px; width: 90%"
                  :src="'api//' + newBook.picture_url"
                  fit="cover"
                />
              </div>
              <div class="newBookName">
                <span class="newBookTitle" style="display: inline-block">{{
                  newBook.ofname
                }}</span>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-row>
  </div>

  <div class="parent-back">
    <el-row class="recommandBlock child-back">
      <div class="pos">
        <el-row>
          <el-col :span="6"><h class="eachLineTitle">学术研究</h></el-col>
          <el-col :offset="15" :span="3"
            ><button class="moreBtn">
              <RouterLink
                style="color: brown; font-size: 1em"
                to="/scv?value=学术研究&type=8&according=ofkind"
                >查看更多</RouterLink
              >
            </button></el-col
          >
        </el-row>
        <div class="dividLink"></div>
        <div class="recScroll"><RecPagination></RecPagination></div>
      </div>
    </el-row>
  </div>

  <div class="parent-back">
    <el-row class="specialThemsBlock child-back">
      <div class="pos">
        <el-row>
          <el-col :span="6"><h class="eachLineTitle">特色专题</h></el-col>
          <el-col :offset="15" :span="3"
            ><button class="moreBtn">
              <RouterLink
                style="color: brown; font-size: 1em"
                to="/scv?value=&type=7&according=default"
                >查看更多</RouterLink
              >
            </button></el-col
          >
        </el-row>
        <div class="dividLink"></div>
        <div class="specialThemsBooks">
          <el-row style="height: 50%; width: 100%">
            <el-col :span="12" v-for="item in special.slice(0, 2)">
              <el-row class="specialAllBack">
                <el-col :span="8" class="specialBack">
                  <el-image
                    style="height: 170px; width: 145px"
                    :src="'api//' + item.picture_url"
                    fit="cover"
                  />
                </el-col>
                <el-col :span="14" style="margin-bottom: 15px">
                  <div style="height: 30px">
                    <el-text class="specialTitle" truncated>{{
                      item.ofname
                    }}</el-text>
                  </div>
                  <div style="overflow-y: hidden">
                    <el-text size="large" style="height: 100%; width: 100%">{{
                      item.resume
                    }}</el-text>
                  </div>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-row style="height: 50%; width: 100%">
            <el-row style="height: 50%; width: 100%">
              <el-col :span="12" v-for="item in special.slice(2, 4)">
                <el-row class="specialAllBack">
                  <el-col :span="8" class="specialBack">
                    <el-image
                      style="height: 170px; width: 145px"
                      :src="'api//' + item.picture_url"
                      fit="cover"
                    />
                  </el-col>
                  <el-col :span="14" style="margin-bottom: 15px">
                    <div style="height: 40px">
                      <el-text class="specialTitle" truncated>{{
                        item.ofname
                      }}</el-text>
                    </div>
                    <div style="overflow-y: hidden">
                      <el-text size="large" style="height: 100%; width: 100%">{{
                        item.resume
                      }}</el-text>
                    </div>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </el-row>
        </div>
      </div>
    </el-row>
  </div>
</template>
<style scoped lang="scss">
.specialP {
  overflow: hidden;
}
.specialTitle {
  font-size: x-large;
  overflow: hidden;
}

.specialTitle:hover {
  text-decoration: underline;
  color: chocolate;
}
.specialAllBack {
  background-color: rgba(223, 223, 223, 0.561);
  padding-top: 15px;
  margin-right: 20px;
}

.specialBack {
  padding-left: 20px;
  margin-bottom: 15px;
}

.newBookImgBack {
  background-color: rgb(218, 218, 218);
  padding-left: 9%;
  padding-top: 8%;
  height: 185px;
  margin-bottom: 10px;
  width: 80%;
}

.newBookName {
  display: flex;
  justify-content: center;
  font-size: larger;
}

.newBookTitle:hover {
  text-decoration: underline;
  color: chocolate;
}
.moreBtn {
  background-color: rgb(255, 255, 255);
  border-radius: 16px;
  height: 2.5em;
  width: 90%;
  border: 1px solid brown;
}

.moreBtn a:hover {
  text-decoration: underline;
}
.gotoPos a:hover {
  text-decoration: underline;
  color: chocolate;
}

.gotoPos a {
  color: rgb(43, 34, 14);
}
.recScroll {
  background-color: rgba(234, 234, 234, 0.561);
  padding-top: 1%;
  margin-top: 1%;
  height: 70%;
}
.newBooks {
  background-color: rgba(234, 234, 234, 0.561);
  padding-top: 1%;
  margin-top: 1%;
  height: 80%;
}
.redColorBooks {
  background-color: rgba(234, 234, 234, 0.561);
  padding-top: 1%;
  margin-top: 1%;
  height: 80%;
}
.specialThemsBooks {
  padding-top: 1%;
  margin-top: 1%;
  height: 80%;
}

.eachLineTitle {
  display: inline-block;
  margin-bottom: 1%;
  font-family: Kaiti, serif;
  font-size: 2em;
  color: rgba(38, 29, 19, 0.63);
}

.dividLink {
  height: 2px;
  border-top: 2px solid rgb(37, 27, 27);
}
.routerLinkBlock {
  display: flex;
  background-color: rgba(230, 192, 116, 0.667);
  width: 70%;
  height: 30px;
  border-radius: 4px;
  padding-left: 10%;
  padding-top: 5%;
}

.routerLinkBlock .el-icon {
  display: inline-block;
  margin-right: 20%;
  margin-top: 5%;
}

.routerLinkBlock a {
  display: inline-block;
}
.search {
  margin-top: 5%;
  margin-bottom: 5%;
  margin-left: 14%;
  //margin-right: 20%;
  width: 80%;
  //width: 100%;
  //height: 2em;
}

.searchBlock {
  height: 350px;
  background: url("../assets/backGND.jpg") no-repeat;
  background-size: 100% 100%;
}

.gotoBlock {
  height: 90px;
  color: rgb(43, 34, 14);
}

.pos {
  margin-left: 4%;
  width: 92%;
}

.gotoPos {
  height: 30%;
  margin-top: 2%;
  margin-left: 10%;
  width: 90%;
}

.recommandBlock {
  height: 380px;
}

.newBooksBlock {
  height: 650px;
}

.redColorBlock {
  height: 420px;
  margin-bottom: 20px;
}
.specialThemsBlock {
  height: 600px;
}
</style>
