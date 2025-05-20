<script lang="ts" setup>
import axios from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";

axios.defaults.baseURL = "/api";
const router = useRouter();
/*----------------------------------------------------------------*/
//注册接口
function toRegist() {
  //console.log(flag.value)
  for (let i = 0; i < inputs.value.length; i++) {
    if (inputs.value[i] == "") {
      isComplete.value = true;
      notCompleteItem.value = items[i] + "输入为空";
      return;
    }
  }
  if (inputs.value[2] != inputs.value[3]) {
    isComplete.value = true;
    notCompleteItem.value = "两次输入密码不一致";
    return;
  }
  if (inputs.value[5].length != 11) {
    isComplete.value = true;
    notCompleteItem.value = "请输入11位手机号";
    return;
  }
  isComplete.value = false;
  notCompleteItem.value = "";
  //ElMessage('注册成功,三秒后跳转到登录页面');
  //setTimeout(() => {router.push('/login');}, 3000);
  //return;
  axios
    .post("/regis", {
      account: inputs.value[0],
      username: inputs.value[1],
      password: inputs.value[2],
      certain: inputs.value[3],
      email: inputs.value[4],
      phonenumber: inputs.value[5],
      captcha: inputs.value[6],
    })
    .then(function (response) {
      if (response.data.code == 200) {
        ElMessage({
          message: "注册成功,三秒后跳转到登录页面",
          type: "success",
        });
        setTimeout(() => {
          router.push("/login");
        }, 3000);
      }
      else if (response.data.code == 500)
      {
        const msg = response.data.msg;
        ElMessage({
          message: msg,
          type: "error",
        });
      }
    })
    .catch(function (error) {
      console.log(error);
    });
}
const isCounting =ref(false);
const countdown = ref(120);
const isValidEmail = ref(true);
const emailRegex = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
function togiven(){
  isValidEmail.value = emailRegex.test(inputs.value[4]);
  if (isValidEmail.value) {
    isComplete.value = false;
    axios.post("/yanzhen", {
      email: inputs.value[4],
    });
    startCountdown();
  } else {
    isComplete.value = true;
    notCompleteItem.value = "请输入正确邮箱";
    return;
  }
}
function startCountdown() {
  isCounting.value = true;
  let countdownTimer = setInterval(() => {
    if (countdown.value > 0) {
      countdown.value--;
    } else {
      clearInterval(countdownTimer); // 清除定时器
      isCounting.value = false;
      countdown.value = 120; // 重置倒计时
    }
  }, 1000); // 每秒更新一次倒计时
}
const isComplete = ref(false);
const notCompleteItem = ref("");
/*----------------------------------------------------------------*/
const items = ["账号", "姓名", "密码", "确认密码", "电话", "邮箱", "验证码"];
//---------------账号[0] 名称[1]  密码[2]  确认密码[3] 电子邮箱[4] 电话[5]  验证码[6]
const inputs = ref(["", "", "", "", "", "", ""]);
const flag = ref([false, false, false, false, false, false, false]);
function inputChangeEvent(id) {
  if (id < inputs.value.length && inputs.value[id] == "") flag.value[id] = true;
  else flag.value[id] = false;
}


</script>
<template>
  <div class="parent-back">
    <div class="regsBGND child-back">
      <div class="regsBlock">
        <div class="line" style="margin-left: 25%">
          <h class="regsTitle">欢迎注册</h>
        </div>
        <div class="line">
          <el-row>
            <el-col :span="3" class="label"
              >&emsp;&emsp;<label class="xreq">账号:</label></el-col
            >
            <el-col :span="18"
              ><el-input
                oninput="value=value.replace(/[\u4E00-\u9FA5]/g,'')"
                maxlength="20"
                @blur="inputChangeEvent(0)"
                v-model="inputs[0]"
                placeholder="请输入账号"
            /></el-col>
            <el-col :span="3" v-show="flag[0]" class="nullNote"
              >输入为空</el-col
            >
          </el-row>
        </div>
        <div class="line">
          <el-row>
            <el-col :span="3" class="label"
              >&emsp;&emsp;<label class="xreq">姓名:</label></el-col
            >
            <el-col :span="18"
              ><el-input
                @blur="inputChangeEvent(1)"
                v-model="inputs[1]"
                placeholder="请输入用户名"
            /></el-col>
            <el-col :span="3" v-show="flag[1]" class="nullNote"
              >输入为空</el-col
            >
          </el-row>
        </div>
        <div class="line">
          <el-row>
            <el-col :span="3" class="label"
              >&emsp;&emsp;<label class="xreq">密码:</label></el-col
            >
            <el-col :span="18"
              ><el-input
                type="password"
                @blur="inputChangeEvent(2)"
                show-password
                v-model="inputs[2]"
                placeholder="请输入密码"
            /></el-col>
            <el-col :span="3" v-show="flag[2]" class="nullNote"
              >输入为空</el-col
            >
          </el-row>
        </div>
        <div class="line">
          <el-row>
            <el-col :span="3" class="label"
              ><label class="xreq">确认密码:</label></el-col
            >
            <el-col :span="18"
              ><el-input
                type="password"
                @blur="inputChangeEvent(3)"
                show-password
                v-model="inputs[3]"
                placeholder="请确认密码"
            /></el-col>
            <el-col :span="3" v-show="flag[3]" class="nullNote"
              >输入为空</el-col
            >
          </el-row>
        </div>
        <div class="line">
          <el-row>
            <el-col :span="3" class="label"
              >&emsp;&emsp;<label class="xreq">手机:</label></el-col
            >
            <el-col :span="18"
              ><el-input
                oninput="value=value.replace(/[\u4E00-\u9FA5]/g,'')"
                type="tel"
                maxlength="11"
                @blur="inputChangeEvent(5)"
                v-model="inputs[5]"
                placeholder="请输入手机号码"
            /></el-col>
            <el-col :span="3" v-show="flag[4]" class="nullNote"
              >输入为空</el-col
            >
          </el-row>
        </div>
        <div class="line">
          <el-row>
            <el-col :span="3" class="label"
            >&emsp;&emsp;<label class="xreq">邮箱:</label></el-col
            >
            <el-col :span="14"
              ><el-input
                oninput="value=value.replace(/[\u4E00-\u9FA5]/g,'')"
                maxlength="30"
                @blur="inputChangeEvent(4)"
                v-model="inputs[4]"
                placeholder="请输入邮箱"
            /></el-col>
            <el-col :span="4">
              <el-button
              @click="togiven"
              >
              <span v-if="isCounting">{{ countdown }}s</span> <!-- 显示倒计时 -->
              <span v-else>获取验证码</span> <!-- 正常状态下的文本 -->
              </el-button>
            </el-col>
            <!-- 省略了其他列 -->
          </el-row>
        </div>
        <div class="line" style="margin-bottom: 0px">
          <el-row>
            <el-col :span="3" class="label"
              >&emsp;<label class="xreq">验证码:</label></el-col
            >
            <el-col :span="18"
              ><el-input
                maxlength="6"
                @blur="inputChangeEvent(6)"
                v-model="inputs[6]"
                placeholder="请输入验证码"
            /></el-col>
            <el-col :span="3" v-show="flag[6]" class="nullNote"
              >输入为空</el-col
            >
          </el-row>
        </div>
        <div
          style="
            position: relative;
            height: 25px;
            left: 28%;
            font-size: 0.7em;
            margin: 0;
            padding-top: 10px;
            color: brown;
          "
        >
          <span v-if="isComplete">{{ notCompleteItem }}</span>
        </div>
        <div class="line">
          <button @click="toRegist" class="submit">提交</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.nullNote {
  padding-top: 1.5%;
  color: red;
  padding-left: 1%;
  font-size: 0.7em;
}
.el-button {
  border-radius: 0%;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
}
.submit {
  color: aliceblue;
  border-radius: 6px;
  background-color: brown;
  width: 75%;
  margin-left: 12.5%;
  border: 0;
}

.submit:hover {
  background-color: rgb(90, 90, 90);
}
.line .el-row .el-col .el-input {
  height: 2.8em;
}

.label {
  padding-top: 1%;
}
label.xreq:before {
  content: "*";
  color: red;
}

.line .el-row span {
  margin-right: auto;
}

.regsTitle {
  font-family: Kaiti, serif;
  font-size: 2.5em;
}
.regsBGND {
  height: 700px;
  margin: 0;
  background-color: rgb(238, 238, 238);
}
.regsBlock {
  width: 1203px;
  height: 640px;
  border-radius: 6px;
  box-shadow: 2px 2px 2px 2px rgb(193, 192, 192);
  background-color: white;
  margin-left: 30px;
  margin-top: 15px;
  padding-top: 10px;
}

.line {
  position: relative;
  left: 20%;
  margin-bottom: 2%;
  width: 60%;
}

.line button {
  height: 2.8em;
}
</style>
