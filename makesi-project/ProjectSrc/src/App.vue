<script lang="ts" setup>
import { RouterView, RouterLink } from "vue-router";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { ElLoading } from "element-plus";
import { onBeforeMount } from "vue";
import axios from "axios";
axios.defaults.baseURL = "/api";

onBeforeMount(async () => {
  if (store.getters.getAskIndexState == true) return;
  store.commit("clearOriHis");
  store.commit("clearLiter");
  store.commit("clearAcademic");
  store.commit("clearSpecial");
  //store.commit('setAskIndexState', true);
  const indexLoading = ElLoading.service();
  await axios({
    method: "POST",
    url: "/home/getAllHomeElem",
    params: "",
    headers: {
      "Cache-Control": "no-cache, no-store, must-revalidate", // 禁用缓存
      "Pragma": "no-cache",
      "Expires": "0",
    },
  })
    .then(function (response) {
      if (response.data.code == 200) {
        let count = 0;
        let offset = 0;
        for (let i = 0; i < response.data.data.homeListLen[0]; i++) {
          store.commit("pushOriHisUrl", response.data.data.homeList[i]);
          count++;
        }
        offset = count;
        for (
          let i = offset;
          i < response.data.data.homeListLen[1] + offset;
          i++
        ) {
          store.commit("pushLiterUrl", response.data.data.homeList[i]);
          count++;
        }
        offset = count;
        for (
          let i = offset;
          i < response.data.data.homeListLen[2] + offset;
          i++
        ) {
          store.commit("pushAcademicUrl", response.data.data.homeList[i]);
          count++;
        }
        offset = count;
        for (
          let i = offset;
          i < response.data.data.homeListLen[3] + offset;
          i++
        ) {
          store.commit("pushSpecialUrl", response.data.data.homeList[i]);
        }
      } else {
        ElMessage.error("首页加载失败，服务端返回：" + response.data.code);
      }
      store.commit("setAskIndexState", true);
    })
    .catch(function (error) {
      ElMessage.error("加载网络资源失败,出现网络错误，错误代码: " + error);
    });
  indexLoading.close();
});

const router = useRouter();
const store = useStore();
function logOut() {
  store.commit("setLogin", false);
  store.commit("setSuper", false);
}

function personalClick() {
  if (store.getters.getLogin) {
    router.push("usr");
  } else {
    ElMessage({
      message: "请先登录",
      type: "warning",
    });
  }
}
</script>

<template>
  <div class="titleBack">
    <el-row class="titleBlock">
      <el-col :span="17" :offset="3" class="titleCol"
        ><h class="titleFont">抗震救灾精神口述史数据库建设</h></el-col
      >
      <el-col :span="1"
        ><RouterLink to="/"
          ><h class="titleLogRegis">首页</h></RouterLink
        ></el-col
      >
      <el-col :span="2" v-if="store.getters.getLogin"
        ><RouterLink to="/usr"
          ><h class="titleLogRegis">个人中心</h></RouterLink
        ></el-col
      >
      <el-col :span="1" v-if="store.getters.getLogin"
        ><RouterLink to="/" @click="logOut"
          ><h class="titleLogRegis">退出</h></RouterLink
        ></el-col
      >
      <el-col :span="1" v-if="!store.getters.getLogin"
        ><RouterLink to="/login"
          ><h class="titleLogRegis">登录</h></RouterLink
        ></el-col
      >
      <el-col :span="1" v-if="!store.getters.getLogin"
        ><RouterLink to="/regis"
          ><h class="titleLogRegis">注册</h></RouterLink
        ></el-col
      >
    </el-row>
  </div>
  <RouterView />
  <div class="bottomBack">
    <div class="bottomMsgBlock">
      <el-row style="height: 100%; width: 100%; padding-top: 2%">
        <el-col :span="9" style="padding-left: 21%">
          <img src="./assets/myInfo.jpg" style="height: 150px" />
          <div class="verticalLine"></div>
        </el-col>
        <el-col :span="14" class="linkFriend">
          <el-row>
            <el-col :span="3"><RouterLink to="/">首页</RouterLink></el-col>
            <el-col :span="3"><h>相关链接</h></el-col>
            <el-col :span="3"></el-col>
          </el-row>
          <el-row>
            <el-col :span="3"
              ><button>
                <a @click="personalClick">个人中心</a>
              </button></el-col
            >
            <el-col :span="3"><RouterLink to="usr">test</RouterLink></el-col>
            <el-col :span="3"><a>link4</a></el-col>
          </el-row>
          <el-row>
            <el-col :span="3"></el-col>
            <el-col :span="3"><a>link2</a></el-col>
            <el-col :span="3"><a>link5</a></el-col>
          </el-row>
          <el-row>
            <el-col :span="3"></el-col>
            <el-col :span="3"><a>link3</a></el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped lang="scss">
.titleLogRegis:hover {
  text-decoration: underline;
  color: chocolate;
}
.linkFriend .el-row {
  margin-bottom: 2%;
  font-family: Kaiti, serif;
  font-size: 1em;
  color: white;
}
.linkFriend a:hover {
  text-decoration: underline;
  color: chocolate;
}
.verticalLine {
  display: inline-block;
  margin-left: 12%;
  border-left: 1px solid white;
  width: 1px;
  height: 160px;
}

.titleBlock {
  padding-top: 0.7%;
  height: 70px;
  width: 1250px;
}
.titleBack {
  display: flex;
  background-color: rgba(127, 8, 8, 0.93);
  width: 100%;
  min-width: 1250px;
  justify-content: center;
}
.titleFont {
  font-family: Kaiti, serif;
  font-size: 1.6em;
  color: aliceblue;
}

.titleLogRegis {
  font-family: Kaiti, serif;
  font-size: 1.1em;
  color: aliceblue;
}

.bottomMsgBlock {
  height: 200px;
  width: 1250px;
}
.bottomBack {
  display: flex;
  background-color: rgba(17, 75, 96, 0.811);
  justify-content: center;
  min-width: 1250px;
  width: 100%;
}
.bottomMsgBlock button {
  all: initial;
  font-size: 0.8em;
}
</style>
