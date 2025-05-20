<script setup lang="ts">
import { ref } from "vue";
import { RouterLink, useRouter } from "vue-router";
import axios from "axios";
import { useStore } from "vuex";
import {ElMessage} from "element-plus";
import dentify from "./dentify.vue";
const store = useStore();
const router = useRouter();
/*--------------------------------------------------------*/
//登录接口
axios.defaults.baseURL = "/api";
const identifyCode = ref('');
const identifyCodes = "1234567890abcdefjhijklinopqrsduvwxyz";
refreshCode();
function refreshCodes(length = 4){
  let result = '';
  const codesLength = identifyCodes.length;
  for (let i = 0; i < length; i++) {
    const randomIndex = Math.floor(Math.random() * codesLength);
    result += identifyCodes[randomIndex];
  }
  return result;
}
function refreshCode(){
  identifyCode.value = refreshCodes();
}
function usrLogin() {
  if (password.value == "" || account.value == "") {
    if (password.value == "") {
      message.value = "密码输入为空";
    }
    if (account.value == "") {
      message.value = "账号输入为空";
    }
    isError.value = true;
    return;
  }
  if (captcha.value != identifyCode.value)
  {
    message.value = "验证码输入错误，请重新输入";
    isError.value = true;
    return;
  }
  isError.value = false;
  axios
    .post("/login", {
      account: account.value,
      password: password.value,
    })
    .then(function (response) {
      console.log(response);
      if (response.data.code == 200) {
        ElMessage({
          message: "登录成功",
          type: "success",
        });
        console.log(response.data);
        localStorage.setItem('token', response.data);
        localStorage.setItem('account',account.value);
        isError.value = false;
        store.dispatch("setLoginA", true);
        //console.log(store.getters.getLogin);
        if (account.value == 'wang') {
          store.dispatch("setSuperA", true);
        }
        router.push("/");
      } else if (response.data.code == 500) {
        store.dispatch("setLoginA", false);
        isError.value = true;
        message.value = response.data.msg;
      }
    })
    .catch(function (failResponse) {
      console.log(failResponse.response.status);
      isError.value = true;
      message.value =
        "网络错误,错误代码：" + String(failResponse.response.status);
    });
}
/*--------------------------------------------------------*/

const account = ref("");
const password = ref("");
const captcha = ref(""); //验证码
const message = ref(""); //登录失败提示信息
const isError = ref(false);
</script>
<template>
  <div class="parent-back">
    <div class="loginBGND child-back">
      <div class="login">
        <div class="line" style="margin-bottom: 30px">
          <h class="loginTitle">账号登录</h>
        </div>
        <div class="line">
          <el-input v-model="account" placeholder="账号" />
        </div>
        <div class="line">
          <el-input
            type="password"
            v-model="password"
            placeholder="密码"
            show-password
          />
        </div>
        <el-row class="line">
          <el-col :span="20" class="input-and-captcha-container">
            <el-input v-model="captcha" placeholder="验证码" class="put"/>
            <div class="login-code" @click="refreshCode">
              <!-- 验证码组件 -->
              <dentify :identifyCode="identifyCode"/>
            </div>
          </el-col>
        </el-row>
        <div class="line"><button @click="usrLogin">登录</button></div>
        <el-row class="line">
          <el-col :span="3" :offset="13"
            ><RouterLink to="\">IP登录</RouterLink></el-col
          >
          <el-col :span="3"><RouterLink to="\">立即注册</RouterLink></el-col>
          <el-col :span="3" style="margin-left: 2%"
            ><RouterLink to="\">忘记密码</RouterLink></el-col
          >
        </el-row>
        <div v-if="isError" class="line">
          <span style="margin-left: 3%; font-size: 0.8em; color: red"
            >登录失败: {{ message }}</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.line .el-input {
  height: 3em;
}
.loginTitle {
  margin-left: 34%;
  font-family: KaiTi, serif;
  font-size: 2em;
}
.loginBGND {
  height: 643px;
  /* margin: 0; */
  background-color: rgb(238, 238, 238);
}

.login {
  height: 400px;
  top: 120px;
  width: 459px;
  border-radius: 6px;
  box-shadow: 2px 2px 2px 2px rgb(193, 192, 192);
  background-color: white;
  margin-left: 50%;
  margin-top: 4%;
}

.line {
  position: relative;
  left: 7%;
  top: 7%;
  margin-bottom: 3%;
  width: 85%;
}

.line a {
  color: black;
  font-size: 0.5em;
}

.line a:hover {
  color: rgba(127, 8, 8, 0.93);
  text-decoration: underline;
}

.line button {
  width: 100%;
  height: 3em;
  border-radius: 4px;
  border: 1px;
  color: aliceblue;
  background-color: rgba(127, 8, 8, 0.93);
}
.input-and-captcha-container {
  display: flex;
  align-items: center;
}
.put{
  width: 200px;  /* 设置输入框的宽度 */
}
.login-code {
  margin-left: 5px;  /* 添加一些间距 */
  cursor: pointer;  /* 确保点击时有视觉反馈 */
}
</style>
