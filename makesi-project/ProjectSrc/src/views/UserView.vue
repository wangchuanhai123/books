<script setup lang="ts">
import { ref, reactive, onMounted, watch } from "vue";
import UploadFileVue from "../components/UploadFile.vue";
import UserManage from "../components/UserManage.vue";
import indexManage from "../components/IndexManage.vue";
import { Document, Menu as IconMenu } from "@element-plus/icons-vue";
import { ElMessageBox } from "element-plus";
import { useStore } from "vuex";
import SpeicialManageVue from "../components/SpeicialManage.vue";
import axios from "axios";

const store = useStore();
//菜单控制显示
const menuFlags = ref([true, false, false, false]);
const labelPosition = ref("right");

const formLabelAlign = reactive({
  psd1: "",
  psd2: "",
});
const dialogVisible = ref(false);
const selectNav1 = ref("navBackShow");
const selectNav2 = ref("navBackHind");
const selectNav3 = ref("navBackHind");
const selectNav4 = ref("navBackHind");
let curSelectNav = 1;
const flags = ref([true, false, false, false]);

function MenuClick(id) {
  for (let i = 0; i < menuFlags.value.length; i++) {
    menuFlags.value[i] = false;
  }
  menuFlags.value[id] = true;
}

/*-----------------------------------------------------------------*/
//修改密码
async function modefyPasd() {
  dialogVisible.value = true;
  axios.post("/test", { userid: "1" });
}

function certain() {
  dialogVisible.value = false;
}

function cancel() {
  dialogVisible.value = false;
}

const handleClose = (done: () => void) => {
  ElMessageBox.confirm("确认关闭?")
    .then(() => {
      done();
    })
    .catch(() => {
      // catch error
    });
};
/*-----------------------------------------------------------------*/
//确定导航栏是否选择更换CSS
function clickNav(id) {
  if (curSelectNav == id) return;
  flags.value[id - 1] = true;
  flags.value[curSelectNav - 1] = false;
  switch (curSelectNav) {
    case 1:
      selectNav1.value = "navBackHind";
      break;
    case 2:
      selectNav2.value = "navBackHind";
      break;
    case 3:
      selectNav3.value = "navBackHind";
      break;
    case 4:
      selectNav4.value = "navBackHind";
      break;
    default:
      curSelectNav = 1;
      break;
  }
  switch (id) {
    case 1:
      selectNav1.value = "navBackShow";
      break;
    case 2:
      selectNav2.value = "navBackShow";
      break;
    case 3:
      selectNav3.value = "navBackShow";
      break;
    case 4:
      selectNav4.value = "navBackShow";
      break;
    default:
      curSelectNav = 1;
      break;
  }
  curSelectNav = id;
}

function clickNav1() {
  clickNav(1);
}

function clickNav2() {
  clickNav(2);
}

function clickNav3() {
  clickNav(3);
}

function clickNav4() {
  clickNav(4);
}

/*-----------------------------------------------------------------*/
//用户状态
// const username = ref('name')
// const userStateInfo = [{info: '账号状态', value: ''},
//                        {info: '用户等级', value: ''},
//                        {info: '在线时长', value: ''},
//                        {info: '手机',     value: ''},
//                        {info: '邮箱',     value: ''}]
// const userState = ref(['激活', '1级', '0.0小时', '151****3995', '2636058874@qq.com'])

//改
const username = ref("");

const fetchUsername = async () => {
  try {
    const response = await axios.post("/api/get-username", {
      username: localStorage.getItem("account"),
    }); // 替换为你的后端接口地址
    console.log(response.data);
    username.value = response.data.data; // 假设后端返回的数据结构为 { username: 'name' }
  } catch (error) {
    console.error("获取用户名失败", error);
  }
};

onMounted(() => {
  fetchUsername();
});

const userState = ref({
  status: '',
  level: '',
  phone: '',
  email: ''
});

const userStateInfo = [
  { info: "账号状态", value: "" },
  { info: "用户等级", value: "" },
  { info: "手机", value: "" },
  { info: "邮箱", value: "" },
];

const fetchUserInfo = async () => {
  try {
    const response = await axios.post(`/api/get-user-information`, {
      username: localStorage.getItem("account"),
      email: localStorage.getItem("email"),
    });
    console.log(response.data);
    //userState.value = response.data;
    
    userState.value = {
      status: "激活",
      level: "1级",
      phone: response.data.data.phonenumber,
      email: response.data.data.email
    };
  } catch (error) {
    console.error("获取用户信息失败:", error);
  }
};

onMounted(() => {
  fetchUserInfo();
});

// const userStateInfo = [{info: '账号状态', value: ''},
//                        {info: '用户等级', value: ''},
//                        {info: '在线时长', value: ''},
//                        {info: '手机',     value: ''},
//                        {info: '邮箱',     value: ''}]
// const userState = ref([
//   "激活",
//   "1级",
//   "0.0小时",
//   "151****3995",
//   "2636058874@qq.com",
// ]);

// for (let i = 0; i < userStateInfo.length && i < userState.value.length; i++) {
//   userStateInfo[i].value = userState.value[i];
// }

// watch(userState, () => {
//   userStateInfo.forEach((item, index) => {
//     if (index < userState.value.length) {
//       item.value = userState.value[index];
//     }
//   });
// });

/*-----------------------------------------------------------------*/
//用户昵称修改
const isShow = ref(false);
const isHind = ref(true);
const isRead = ref(true);

document.getElementById("name");

function clickChangeName() {
  isRead.value = false;
  isHind.value = false;
  isShow.value = true;
  document.getElementById("name").focus();
}

function changeedName() {
  isRead.value = true;
  isHind.value = true;
  isShow.value = false;
}

/*-----------------------------------------------------------------*/
//用户申请权限表单
const form = reactive({
  name: "",
  region: "",
  date1: "",
  date2: "",
  delivery: false,
  type: [],
  resource: "",
  desc: "",
});

const onSubmit = () => {
  console.log("submit!");
};
//用户历史登录
const data = ref<[Date, Date]>([new Date(), new Date()]);
const usrTableData = ref([]);
</script>

<template>
  <div class="parent-back">
    <el-row class="child-back" style="height: 850px">
      <el-col :span="22" :offset="1">
        <div
          class="back"
          style="
            height: 93%;
            box-shadow: 2px 2px 2px 2px rgb(193, 192, 192);
            margin-left: 1.4%;
            margin-right: 1%;
            margin-top: 1%;
          "
        >
          <el-row style="height: 45px; margin-bottom: 20px">
            <el-col :span="3">
              <button
                @click="clickNav1"
                :class="[selectNav1]"
                style="
                  display: inline-block;
                  height: 100%;
                  width: 100%;
                  font-size: 0.8em;
                "
              >
                个人信息
              </button>
            </el-col>
            <el-col :span="3" v-if="store.getters.getSuper">
              <button
                @click="clickNav4"
                :class="[selectNav4]"
                style="
                  display: inline-block;
                  height: 100%;
                  width: 100%;
                  font-size: 0.8em;
                "
              >
                网站管理
              </button>
            </el-col>
          </el-row>
          <div style="height: 3px; border-top: 3px solid brown"></div>
          <el-container v-show="flags[0]" style="height: 90.2%">
            <el-aside class="usrInfo" width="300px">
              <div class="usrName" style="margin-top: 8%; font-size: 1em">
                <input
                  id="name"
                  style="font-size: 1.2em"
                  v-model="username"
                  show-word-limit
                  maxlength="7"
                  @blur="changeedName"
                  :readonly="isRead"
                  :class="[{ nameHind: isHind }, { nameShow: isShow }]"
                />
              </div>
              <el-row class="usrChange" style="color: brown">
                <el-col :span="3" style="font-size: 0.8em"
                  ><span style="display: inline-block">昵称</span></el-col
                >
                <el-col :span="1" style="font-size: 0.8em">
                  <el-icon @click="clickChangeName" style="margin-top: 2px">
                    <EditPen />
                  </el-icon>
                </el-col>
              </el-row>
              <div class="line-div"></div>
              <div>
                <p style="font-size: 0.9em; margin-bottom: 10px; text-align: center;">
                  <span style="color: #87212e;text-align: center;">账号状态</span>: {{ userState.status }}
                </p>
                <p style="font-size: 0.9em; margin-bottom: 10px; text-align: center;">
                  <span style="color: #87212e;text-align: center;">用户等级</span>: {{ userState.level }}
                </p>
                <p style="font-size: 0.9em; margin-bottom: 10px; text-align: center;">
                  <span style="color: #87212e;text-align: center;">手机</span>: {{ userState.phone }}
                </p>
                <p style="font-size: 0.9em; margin-bottom: 10px; text-align: center;">
                  <span style="color: #87212e;text-align: center;">邮箱</span>: {{ userState.email }}
                </p>
              </div>
              <div style="height: 30px"></div>
              <div style="display: flex; justify-content: center">
                <el-button @click="modefyPasd" type="primary"
                  >修改密码</el-button
                >
              </div>
              <el-dialog
                v-model="dialogVisible"
                title="修改密码"
                width="30%"
                :before-close="handleClose"
              >
                <el-form
                  :label-position="labelPosition"
                  label-width="100px"
                  :model="formLabelAlign"
                  style="max-width: 460px"
                >
                  <el-form-item label="新密码:">
                    <el-input v-model="formLabelAlign.psd1" />
                  </el-form-item>
                  <el-form-item label="确认密码:">
                    <el-input v-model="formLabelAlign.psd2" />
                  </el-form-item>
                </el-form>
                <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="cancel">取消</el-button>
                    <el-button type="primary" @click="certain">
                      确定
                    </el-button>
                  </span>
                </template>
              </el-dialog>
            </el-aside>
            <el-container>
              <el-header class="header">
                <el-form :model="form" label-width="120px">
                  <el-form-item label="申请人:">
                    <el-input style="width: 220px" v-model="form.name" />
                  </el-form-item>
                  <el-form-item label="申请权限类别:">
                    <el-select v-model="form.region" placeholder="选择权限">
                      <el-option label="半公开权限" value="1" />
                      <el-option label="私密权限" value="2" />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="申请理由:">
                    <el-input
                      rows="3"
                      style="width: 800px"
                      v-model="form.desc"
                      type="textarea"
                    />
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="onSubmit">申请</el-button>
                    <el-button>取消</el-button>
                  </el-form-item>
                </el-form>
              </el-header>
              <el-main>
                <el-row style="margin-bottom: 30px">
                  <el-col :span="2" style="padding-left: 20px">
                    <el-icon style="padding-top: 4px">
                      <Avatar />
                    </el-icon>
                  </el-col>
                  <el-col :span="10"><span>历史登录信息</span></el-col>
                </el-row>
                <el-row style="padding-left: 20px">
                  <el-col :span="3" style="padding-top: 3px; font-size: 1em"
                    >选择时间段</el-col
                  >
                  <el-col :span="5">
                    <el-date-picker
                      v-model="data"
                      type="daterange"
                      range-separator="到"
                      start-placeholder="开始时间"
                      end-placeholder="结束时间"
                    />
                  </el-col>
                  <el-col :span="4" :offset="11">
                    <el-row>
                      <el-col :span="12">
                        <el-button type="primary">查询</el-button>
                      </el-col>
                      <el-col :span="11" :offset="1">
                        <el-button>重置</el-button>
                      </el-col>
                    </el-row>
                  </el-col>
                </el-row>
                <div class="line"></div>
                <el-row>
                  <el-table
                    :data="usrTableData"
                    stripe
                    style="width: 95%; margin-left: 20px"
                    table-layout="auto"
                    height="300"
                    border
                    :cell-style="{ textAlign: 'center' }"
                    :header-cell-style="{ textAlign: 'center' }"
                    header-cell-class-name="table-title"
                  >
                    <el-table-column type="index" label="序号" width="160" />
                    <el-table-column prop="name" label="用户名" width="200" />
                    <el-table-column prop="name" label="用户权限" width="160" />
                    <el-table-column prop="address" label="登录时间" />
                  </el-table>
                </el-row>
              </el-main>
            </el-container>
          </el-container>
          <div v-show="flags[3]">
            <el-row style="height: 700px">
              <el-col :span="3">
                <el-menu class="height:100%;" default-active="1">
                  <el-menu-item @click="MenuClick(0)" index="1">
                    <el-icon>
                      <document />
                    </el-icon>
                    <span>文件管理</span>
                  </el-menu-item>
                  <el-menu-item @click="MenuClick(1)" index="2">
                    <el-icon>
                      <UserFilled />
                    </el-icon>
                    <span>用户管理</span>
                  </el-menu-item>
                  <el-menu-item @click="MenuClick(2)" index="3">
                    <el-icon>
                      <icon-menu />
                    </el-icon>
                    <span>首页管理</span>
                  </el-menu-item>
                  <el-menu-item @click="MenuClick(3)" index="4">
                    <el-icon>
                      <Collection />
                    </el-icon>
                    <span>专题管理</span>
                  </el-menu-item>
                </el-menu>
              </el-col>
              <el-col :span="21">
                <UploadFileVue v-show="menuFlags[0]"></UploadFileVue>
                <UserManage v-show="menuFlags[1]"></UserManage>
                <indexManage v-show="menuFlags[2]"></indexManage>
                <SpeicialManageVue v-show="menuFlags[3]" />
              </el-col>
            </el-row>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.usrName {
  display: flex;
  justify-content: center;
}

.usrChange {
  display: flex;
  justify-content: center;
}

.line-div {
  border-bottom: 1px solid rgb(212, 212, 212);
  margin-top: 20px;
  margin-bottom: 25px;
}

.usrInfos {
  color: rgb(181, 18, 18);
}

.line {
  border: 0;
  /*  border-top: 1px solid rgb(147, 147, 147);*/
  width: 95%;
  margin-left: 20px;
  margin-top: 20px;
}

.header {
  height: 255px;
  padding-top: 20px;
  padding-left: 50px;
  border-bottom: 1px solid rgb(191, 191, 191);
}

.back {
  background-color: white;
}

.el-col .el-menu {
  height: 100%;
}

.navBackShow {
  color: white;
  background-color: brown;
  border: 0;
}

.navBackHind {
  color: black;
  border: 0;
  background-color: rgb(255, 255, 255);
}

.usrInfo {
  border-right: 3px solid brown;
}

.nameShow {
  border: 1px solid rgb(179, 179, 179);
  border-radius: 4px;
  height: 1.3em;
  font-size: 0.8em;
  width: 140px;
  text-align: center;
  outline: none;
}

.nameHind {
  border: 0;
  height: 1.3em;
  font-size: 0.8em;
  width: 140px;
  text-align: center;
  outline: none;
}
</style>
<style>
.table-title {
  background: #f2f2f2 !important;
  border: 1px solid rgb(190, 190, 190);
}
</style>