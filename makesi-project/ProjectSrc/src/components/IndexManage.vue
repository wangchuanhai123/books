<script setup lang="ts">
import { useStore } from 'vuex'
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { ElTable } from 'element-plus'
import axios from 'axios'
import { ElLoading } from 'element-plus'
import { ServerEntity } from '../tool/classDef/classDef'
import { allOptionsKinds } from '../tool/func/func'
axios.defaults.baseURL = '/api'
/*-----------------------------------------------------*/
async function changeUrl(){
    const uploadIndexLoading = ElLoading.service();
    let newIndexElem : ServerEntity[] = [];
    let offIndexElem : ServerEntity[] = [];
    for(let i = 0; i < rightValue.value.length; i++){
      if(rightValue.value[i] >= rightLength.value)
        newIndexElem.push(optionTypeToseverEntityType(data.value[rightValue.value[i]]));
      else
        originRightValue.add(i);
    }
    for(let i = 0; i < rightLength.value; i++){
      if(originRightValue.has(i) == false)
        offIndexElem.push(optionTypeToseverEntityType(data.value[i]));
    }
    originRightValue.clear();
    if(offIndexElem.length == 0 && newIndexElem.length == 0){
      ElMessage({
        message: '未修改首页展示资料',
        type: 'warning',
      });
      uploadIndexLoading.close();
      dialogVisible.value = false;
      return;
    }
    await axios({
      method: 'post',
      url: '/home/indexUpload',
      data:{
        newIndexElem: newIndexElem,
        offIndexElem: offIndexElem,
      },
    })
    .then(function (response){
      if(response.data.code == 200){
        ElMessage({
          message: '更新首页成功',
        })
        switch(kindSelect.value){
          case allOptionsKinds[0].value: {
            for(let i = 0; i < newIndexElem.length; i++)
            {
              store.commit('pushOriHisUrl', newIndexElem[i]);
            }
            for(let i = 0; i < offIndexElem.length; i++)
            {
              store.commit('deleteOriHisUrl', offIndexElem[i]);
            }
            break;
          }
          case allOptionsKinds[1].value: {
            for(let i = 0; i < newIndexElem.length; i++)
            {
              store.commit('pushLiterUrl', newIndexElem[i]);
            }
            for(let i = 0; i < offIndexElem.length; i++)
            {
              store.commit('deleteLiterUrl', offIndexElem[i]);
            }
            break;
          }
          case allOptionsKinds[2].value: {
            for(let i = 0; i < newIndexElem.length; i++)
            {
              store.commit('pushAcademicUrl', newIndexElem[i]);
            }
            for(let i = 0; i < offIndexElem.length; i++)
            {
              store.commit('deleteAcademicUrl', offIndexElem[i]);
            }
            break;
          }
          case allOptionsKinds[3].value: {
            for(let i = 0; i < newIndexElem.length; i++)
            {
              store.commit('pushSpecialUrl', newIndexElem[i]);
            }
            for(let i = 0; i < offIndexElem.length; i++)
            {
              store.commit('deleteSpecialUrl', offIndexElem[i]);
            }
            break;
          }
          default: break;
        }
      }else{
        ElMessage.error('首页更新失败，服务端返回:' + response.data.code);
      }
    })
    .catch(function (error){
      ElMessage.error('首页更新失败，失败代码:' + error);
    })
    uploadIndexLoading.close();
    //console.log(rightValue.value);
    //console.log(newIndexElem);
    //console.log(offIndexElem);
    dialogVisible.value = false;
    rightValue.value = [];
}

function cancelAddUrl(){
    dialogVisible.value = false;
    rightValue.value = [];
}

const store = useStore();
//let oriHisdata = store.getters.getOri;

function addUrlToIndex(){
    data.value = [];
    generateData(kindSelect.value);
    dialogVisible.value = true;
}
/*--------------------------------------------------*/
//类型选择
const kindSelect = ref('口述史资料')
const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const multipleSelection = ref<ServerEntity[]>([])
const toggleSelection = (rows?: ServerEntity[]) => {
  if (rows) {
    rows.forEach((row) => {
      // TODO: improvement typing when refactor table
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      multipleTableRef.value!.toggleRowSelection(row, undefined);
    })
  } else {
    multipleTableRef.value!.clearSelection();
  }
}
const handleSelectionChange = (val: ServerEntity[]) => {
  multipleSelection.value = val;
}
/*------------------------------------------------------*/
//对话框弹出
const dialogVisible = ref(false);

const handleClose = (done: () => void) => {
  ElMessageBox.confirm('确认关闭?')
    .then(() => {
      rightValue.value = []
      done()
    })
    .catch(() => {
      // catch error
      rightValue.value = []
    })
}
const searchUsrInput = ref('');
const tableData = ref<ServerEntity []>([]);
function selectChange(){
  tableData.value = [];
}
/*------------------------------------------------------*/
//穿梭框
const rightLength = ref(0);
const data = ref<Option[]>([])
const rightValue= ref([])
let   originRightValue = new Set();

interface Option {
  key: number,
  label: string,
  id: number,
  ofkind: string,
  resume: string,
  mainresponsible2: string,
  create_time: string,
  update_time: string,
  picture_url: string,
  file_url: string,
  keywords: string,
  mainresponsible1: string,
  secondresponsible: string,
  publisher: string,
  publishedplace: string,
  publishedtime: string,
  permission: string,
  oftype: string,
  notes: string,  //口述史专有属性
}

function severEntityTypeToOptionType(source: ServerEntity, otherKey: number){
  return {
    key: otherKey,
    label: source.ofname,
    id: source.id,
    ofkind: source.ofkind,
    resume: source.resume,
    mainresponsible2: source.mainresponsible2,
    create_time: source.create_time,
    update_time: source.update_time,
    picture_url: source.picture_url,
    file_url: source.file_url,
    keywords: source.keywords,
    mainresponsible1: source.mainresponsible1,
    secondresponsible: source.secondresponsible,
    publisher: source.publisher,
    publishedplace: source.publishedplace,
    publishedtime: source.publishedtime,
    permission: source.permission,
    oftype: source.oftype,
    notes: source.notes,  //口述史专有属性
  }
}

function optionTypeToseverEntityType(source: Option){
  return {
    ofname: source.label,
    id: source.id,
    ofkind: source.ofkind,
    resume: source.resume,
    mainresponsible2: source.mainresponsible2,
    create_time: source.create_time,
    update_time: source.update_time,
    picture_url: source.picture_url,
    file_url: source.file_url,
    keywords: source.keywords,
    mainresponsible1: source.mainresponsible1,
    secondresponsible: source.secondresponsible,
    publisher: source.publisher,
    publishedplace: source.publishedplace,
    publishedtime: source.publishedtime,
    permission: source.permission,
    oftype: source.oftype,
    notes: source.notes,  //口述史专有属性
  }
}

const generateData = (typeNum: string) => {
  switch(typeNum){
    case allOptionsKinds[0].value:{
        rightLength.value = store.getters.getOri.length;
        for(let i = 0; i < rightLength.value; i++){
            data.value.push(severEntityTypeToOptionType(store.getters.getOri[i], i));
            originRightValue.add(data.value[i].id)
        }
        break;
    }
    case allOptionsKinds[1].value:{
        rightLength.value = store.getters.getLiter.length;
        for(let i = 0; i < rightLength.value; i++){
            data.value.push(severEntityTypeToOptionType(store.getters.getLiter[i], i));
            originRightValue.add(data.value[i].id)
        }
        break;
    }
    case allOptionsKinds[2].value:{
        rightLength.value = store.getters.getAcademic.length;
        for(let i = 0; i < rightLength.value; i++){
            data.value.push(severEntityTypeToOptionType(store.getters.getAcademic[i], i));
            originRightValue.add(data.value[i].id)
        }
        break;
    }
    case allOptionsKinds[3].value:{
        rightLength.value = store.getters.getSpecial.length;
        for(let i = 0; i < rightLength.value; i++){
            data.value.push(severEntityTypeToOptionType(store.getters.getSpecial[i], i));
            originRightValue.add(data.value[i].id)
        }
        break;
    }
    default: break;
  }
  let repeat = 0;
  for(let i = rightLength.value; i < multipleSelection.value.length + rightLength.value; i++){
    if(originRightValue.has(multipleSelection.value[i - rightLength.value].id) == true){
      repeat++;
      continue;
    }
    data.value.push(severEntityTypeToOptionType(multipleSelection.value[i - rightLength.value], i - repeat));
  }
  for(let i = 0; i < rightLength.value; i++)
      rightValue.value.push(i);
}

//搜索
const tableLoading = ref(false);
async function searchFileByName(){
  tableLoading.value = true;
  await axios({
    method: 'get',
    url: '/home/indexManageSearch',
    params: {
      info: searchUsrInput.value,
      ofkind: kindSelect.value,
    }
  })
  .then(function (response){
    if(response.data.code == 200){
      tableData.value = response.data.data;
    }
    else{
      ElMessage.error("服务端返回错误");
    }
  })
  .catch(function (e){
    ElMessage.error("网络错误" + e.code);
  })
  tableLoading.value = false; 
}
/*------------------------------------------------------*/
</script>
<template>
<el-container style="margin-left: 50px; margin-top: 10px;">
      <el-header class="header">
        <el-row style="margin-bottom: 30px;">
            <el-col :span="12">
                <el-input
                v-model="searchUsrInput"
                class="w-50 m-2"
                placeholder="输入文件名"
                :prefix-icon="Search"
                clearable
                />     
            </el-col>
            <el-col :span="4">
                <button @click="searchFileByName">查询</button>
            </el-col>           
            <el-col :span="4" style="padding-top:3px; padding-left:25px;"><span>专题类型选择:</span></el-col>
            <el-col :span="4">
                <el-select v-model="kindSelect" @change="selectChange" class="m-2">
                    <el-option
                    v-for="item in allOptionsKinds"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                    />
                </el-select>
            </el-col>
        </el-row>
        <div class="line"></div>    
      </el-header>
      <el-main class="main">
        <el-table
            v-loading="tableLoading"
            ref="multipleTableRef"
            :data="tableData"
            style="width: 100%"
            height="480px"
            @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" />
            <el-table-column fixed property="ofname" label="标题" width="120" :show-overflow-tooltip="true"/>
            <el-table-column property="keywords" label="关键词" width="120" :show-overflow-tooltip="true"/>
            <el-table-column property="mainresponsible1" label="主要责任者1" width="110" :show-overflow-tooltip="true"/>
            <el-table-column property="mainresponsible2" label="主要责任者2" width="110" :show-overflow-tooltip="true"/>
            <el-table-column property="secondresponsible" label="次要责任者" width="100" :show-overflow-tooltip="true"/>
            <el-table-column property="publisher" label="出版者" width="120" :show-overflow-tooltip="true"/>
            <el-table-column property="publishedplace" label="出版地" width="80" :show-overflow-tooltip="true"/>
            <el-table-column property="publishedtime" label="出版时间" width="110" :show-overflow-tooltip="true"/>
            <el-table-column property="ofkind" label="专题" width="100" :show-overflow-tooltip="true"/>
            <el-table-column fixed="right" property="oftype" label="类型" :show-overflow-tooltip="true" />
        </el-table>
        <el-row style="margin-top: 20px">
            <el-col :span="3"><button @click="toggleSelection()">清空选择</button></el-col>
            <el-col :span="3"><button @click="addUrlToIndex">添加到首页</button></el-col>
        </el-row>
        <el-dialog
        v-model="dialogVisible"
        title="修改首页展示资料"
        width="700px"
        :before-close="handleClose"
        >
        <el-transfer v-model="rightValue"
                     :data="data"
                     :titles="['新选择的资料', '首页资料']"
                     :button-texts="['删除', '增加']" 
                     :format="{
                        noChecked: '${total}',
                        hasChecked: '${checked}/${total}',
                     }"
        />
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="cancelAddUrl">取消</el-button>
            <el-button type="primary" @click="changeUrl">
                    确认
            </el-button>
        </span>
        </template>
        </el-dialog>
    </el-main>
</el-container>
</template>
<style scoped>
.line{
    height:2px;
    border-top:2px solid brown;
}
button{
    display: inline-block;
    background-color:rgb(0, 155, 206);
    border:0;
    color:white;
    border-radius: 4px;
    height: 32px;
    width: 80px;
}

button:hover{
    display: inline-block;
    background-color:brown;
    border:0;
    color:white;
    border-radius: 4px;
    height: 32px;
    width: 80px;
}
.header{
    padding-top: 12px;
    height: 60px;
}

.el-table__header-wrapper{
  --el-table-border: 2px solid brown;
}
.el-table{
  --el-table-border: 1px solid rgb(86, 86, 86);
}
</style>