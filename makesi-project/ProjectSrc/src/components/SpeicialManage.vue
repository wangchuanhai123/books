<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useStore } from "vuex"
import { Search } from '@element-plus/icons-vue'
import { ServerEntity, SpecialStruct } from '../tool/classDef/classDef'
import { ElMessageBox, ElMessage, ElLoading } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import type { UploadInstance } from 'element-plus'
import axios from 'axios'

const upload = ref<UploadInstance>()
const modifyForm = reactive({
  ofname: '',
  brief: '',
  cover: null,
});
const dialog2ShowTitle = ref('专题名称');
const dialog1ShowTitle = ref('');
const imageUrl = ref('');
const dialogVisible = ref(false);
const dialogVisible2 = ref(false);
const select = ref('图书');
const mod = ref('');
const tableLoading = ref(false)
const tableData = ref<SpecialStruct []>([]);
const specialContentList = ref<ServerEntity []>([]);
const inputValue = ref('');

let   currentSeletected = null;
const store = useStore();

//查询所有专题
async function findSpecial(){
  tableLoading.value = true;
  await axios({
    method: 'get',
    url:    '/root/searchspecialindexall',
    params: {
      searchReciveR: inputValue.value,
    },
  })
  .then(function (response){
    if(response.data.code == 200)
      tableData.value = response.data.data;
    else
      ElMessage.error("服务端返回错误");
  })
  .catch(function (error){
    ElMessage.error("网络错误");
  })
  tableLoading.value = false;
}

function getFile(file){
  modifyForm.cover = file.raw;
  imageUrl.value = URL.createObjectURL(file.raw!);
  //console.log(file.raw);
  upload.value!.clearFiles();
}
//删除专题
async function deleteSpec(index){
  tableLoading.value = true;
  await axios({
    method: "post",
    url: "/root/specialindexdelete",
    data: {
      id: tableData.value[index].id,
      ofname: tableData.value[index].ofname,
      picture_url: tableData.value[index].picture_url,
    }
  })
  .then(function (response){
    if(response.data.code == 200){
      ElMessage.success("删除成功");
      tableData.value.splice(Number(index), 1);
      store.commit('setAskIndexState', false);
    }
    else
      ElMessage.error("删除失败，服务端错误");
  })
  .catch(function (){
    ElMessage.error("删除失败，网络错误");
  })
  tableLoading.value = false;
}
function modify(index){
  currentSeletected = index;
  mod.value = 'modify';
  dialog1ShowTitle.value = "专题修改";
  modifyForm.ofname = tableData.value[index].ofname;
  modifyForm.brief  = tableData.value[index].resume;
  imageUrl.value    = "api\\" + tableData.value[index].picture_url;
  dialogVisible.value = true;
}
function createSpecial(){
  mod.value = 'create';
  modifyForm.brief  = '';
  modifyForm.ofname = '';
  modifyForm.cover  = null;
  imageUrl.value    = '';
  dialog1ShowTitle.value = "专题创建";
  dialogVisible.value = true;
}

//修改/创建专题
async function certain(){
  const loading = ElLoading.service();
  dialogVisible.value = false;
  let config = {
    headers: {'Content-Type': 'multipart/form-data'}
  }
  if(mod.value == 'modify'){
    const loading = ElLoading.service();
    let updateForm = new FormData();
    if(modifyForm.cover != null)
      updateForm.append("indexcover", modifyForm.cover);
    let dataJson = JSON.stringify({
      ofname: modifyForm.ofname,
      resume: modifyForm.brief,
      picture_url: tableData.value[currentSeletected].picture_url,
      id: tableData.value[currentSeletected].id,
    }); 
    let dataBlob = new Blob([dataJson], { type: 'application/json' });
    updateForm.append("receive", dataBlob);
    await axios.post('/root/specialIndexUpdate', updateForm, config)
    .then(function (response){
      if(response.data.code == 200){
        tableData.value[currentSeletected].ofname = response.data.data.ofname;
        tableData.value[currentSeletected].resume = response.data.data.resume;
        tableData.value[currentSeletected].picture_url = response.data.data.picture_url;
        store.commit('setAskIndexState', false);
        ElMessage({message: "修改成功"});
      }
      else
        ElMessage.error("服务端返回错误");
    })
    .catch(function (error){
      ElMessage.error("网络错误");
    })
    loading.close();
  }
  if(mod.value == 'create'){
    let form = new FormData();
    form.append('indexcover', modifyForm.cover);
    let dataJson = JSON.stringify({
      ofname: modifyForm.ofname,
      resume: modifyForm.brief,
    });
    let dataBlob = new Blob([dataJson],{type: 'application/json'});
    form.append('specialindex_noid', dataBlob);
    await axios.post('/root/specialindexcreate', form, config)
    .then(function (response){
      if(response.data.code == 200)
        ElMessage({message: "新建成功"});
      else
        ElMessage.error("服务端返回错误");
    })
    .catch(function (error){
      ElMessage.error("网络错误");
    })
    await findSpecial();
  }
  modifyForm.cover = null;
  loading.close();
}
function certainList(){
  dialogVisible2.value = false;
}
function cancel(){
  dialogVisible.value = false;
  dialogVisible2.value = false;
  mod.value = '';
}
const handleClose = (done: () => void) => {
  ElMessageBox.confirm('确认关闭?')
    .then(() => {
      done()
    })
    .catch(() => {
      // catch error
    })
}

async function fileList(index){
  currentSeletected = index;
  const loading = ElLoading.service();
  dialog2ShowTitle.value = "文献列表: " + tableData.value[index].ofname;
  await axios({
    method: "get",
    url: "/root/searchAllElementInIndex",
    params: { 
     specialId: tableData.value[index].id
    }
  })
  .then(function (response){
    if(response.data.code == 200){
      specialContentList.value = response.data.data;
      dialogVisible2.value = true;
    }
    else{
      ElMessage.error("服务端返回错误代码");
    }
  })
  .catch(function (error){
    ElMessage.error("网络错误");
  });
  loading.close(); 
}

async function tableDeleteRow(index){
  const loading = ElLoading.service();
  await axios({
    method: "post",
    url: "/root/specialElementDel",
    data: { 
     specialId: tableData.value[currentSeletected].id,
     elementId: specialContentList.value[index].id
    }
  })
  .then(function (response){
    if(response.data.code == 200){
      specialContentList.value.splice(index, 1);
      ElMessage.success("删除成功");
    }
    else{
      ElMessage.error("删除失败，服务端返回错误代码");
    }
  })
  .catch(function (error){
    ElMessage.error("网络错误");
  });
  loading.close(); 
}
</script>
<template>
<div style="padding-left:45px;"> 
    <el-row class="btn" style="margin-bottom: 30px; margin-top:40px;">
        <el-col :span="14">
            <el-input
            v-model="inputValue"
            class="w-50 m-2"
            placeholder="请输入用户名"
            :prefix-icon="Search"
            clearable
            />     
        </el-col>
        <el-col :span="4">
            <button @click="findSpecial">查询</button>
        </el-col>
        <el-col :span="4" :offset="2">
          <button @click="createSpecial">新建专题</button>
        </el-col>           
    </el-row>
    <div class="line"></div>    
    <el-table 
      :data="tableData" 
      height="550" 
      style="width: 100%"
      v-loading="tableLoading"
    >
        <el-table-column fixed prop="ofname" label="专题名称" width="270" :show-overflow-tooltip="true"/>
        <el-table-column prop="resume" label="专题简介" width="460" />
        <el-table-column fixed="right" label="操作" width="170" >
          <template #default="scope">
            <el-button link type="primary" size="small" @click="deleteSpec(scope.$index)">删除</el-button>
            <el-button link type="primary" size="small" @click="modify(scope.$index)">修改</el-button>
            <el-button link type="primary" size="small" @click.prevent="fileList(scope.$index)">
              文献列表
            </el-button>
          </template>
        </el-table-column>
    </el-table>
    <el-dialog
      v-model="dialogVisible"
      :title="dialog1ShowTitle"
      width="600px"
      :before-close="handleClose"
    >
        <el-form
          :model="modifyForm"
          status-icon
          label-width="120px"
          class="demo-ruleForm"
        >
        <el-form-item label="专题名称">
          <el-input v-model="modifyForm.ofname" clearable />
        </el-form-item>
        <el-form-item label="专题简介">
          <el-input
            v-model="modifyForm.brief"
            clearable
            type="textarea"
            :rows="4"
          />
        </el-form-item>
        <el-form-item label="专题封面">
          <el-upload
            ref="upload"
            class="avatar-uploader"
            action="fake"
            :show-file-list="false"
            :on-change="getFile"
            :limit="1"
            :auto-upload="false"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
        </el-form-item>
      </el-form>
    <template #footer>
    <span class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="certain">
          确认
        </el-button>
    </span>
    </template>
    </el-dialog>

    <el-dialog
      v-model="dialogVisible2"
      :title="dialog2ShowTitle"
      width="700px"
      :before-close="handleClose"
      border
    >
    <el-table 
      :data="specialContentList" 
      max-height="250" 
      border
      style="width: 100%; border:2px solid black;"
      :cell-style="{ borderColor: 'black'}"
      :header-cell-style="{ borderColor: 'black'}"
      stripe
    >
        <el-table-column prop="ofname" label="名称" width="138" :show-overflow-tooltip="true"/>
        <el-table-column prop="mainresponsible1" label="主要责任人1" width="110" :show-overflow-tooltip="true" />
        <el-table-column prop="mainresponsible2" label="主要责任人2" width="110" :show-overflow-tooltip="true" />
        <el-table-column prop="oftype" label="类型" width="100" :show-overflow-tooltip="true" />
        <el-table-column prop="keywords" label="关键字" width="100" :show-overflow-tooltip="true" />
        <el-table-column fixed="right" label="操作" width="99" >
          <template #default="scope">
            <el-button type="primary" 
              link
              size="small"
              @click.prevent="tableDeleteRow(scope.$index)"
            >
              移除
            </el-button  >
          </template>
        </el-table-column>
    </el-table>
    <template #footer>
    <span class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="certainList">
          确认
        </el-button>
    </span>
    </template>
    </el-dialog>
</div>
</template>
<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.line{
    height:2px;
    border-top:2px solid brown;
}
.btn button{
    display: inline-block;
    background-color:rgb(0, 155, 206);
    border:0;
    color:white;
    border-radius: 4px;
    height: 32px;
    width: 80px;
}

.btn button:hover{
    display: inline-block;
    background-color:brown;
    border:0;
    color:white;
    border-radius: 4px;
    height: 32px;
    width: 80px;
}

.btn{
  display: flex;
  justify-content: center;
}
.el-table{
  --el-table-border: 1px solid rgb(86, 86, 86);
}

</style>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>