<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage, ElTable } from 'element-plus'
import { ElMessageBox } from 'element-plus'
import axios from 'axios'
import { ElLoading } from 'element-plus'
import { options, optionPermission, optionsKinds } from '../tool/func/func'
axios.defaults.baseURL = '/api'
const props = defineProps({
    isMul: Boolean,
});
//多文件上传
interface fileUpMsg{
  upFile: File,
  cover: File,
  keywords: string,  
  ofname: string,        
  ofkind: string,
  mainresponsible1: string, 
  secondresponsible: string,
  publisher: string,
  publishedplace: string,
  publishedtime: string,
  mainresponsible2: string,
  type: string,
  resume: string,
  permission: number,
}
const multiFileTable = ref<fileUpMsg[]>([]);
//const isMul = ref(false);
const formLabelAlign = reactive({
  ofname: '',
  inscriptions: '',
  brief: '',
})
const deleteRow = (index: number) => {
  multiFileTable.value.splice(index, 1)
}
const onAddItem = async () => {
  //isMul.value = true;
  dialogVisible.value = true;
}
const dialogVisible2 = ref(false);
function clickUploadFiles(){
  dialogVisible2.value = true;
}
//文件上传
const upFileRef      = ref()
const upCoverFileRef = ref()
let upFileCover      = null
let upFile           = null   //数据文件
function clickUploadFile(){
  dialogVisible.value   = true
  keyword.value         = ['','','','','']
  name.value            = ''
  mainResp.value        = ''
  minorRsp.value        = ''
  publisher.value       = ''
  publisherPlace.value  = ''
  publishTime.value     = ''
  mainResp2.value       = ''
  uploadFileType.value  = ''
  uploadFileSpec.value  = ''
  uploadPermisson.value = null  //恢复权限字段下拉框
  //upFileRef.value.clearFiles()
  //upCoverFileRef.value.clearFiles()
  upFile      = null
  upFileCover = null
}
function getCoverFile(file){
  upFileCover = file.raw
}
function deleteCoverFile(){
  upFileCover = null
}
function getFile(file){
    upFile = file.raw
    //console.log(upFile)
}
function deleteFile(){
    upFile = null
}
function cancelFile(){
  dialogVisible.value = false
  upFileRef.value.clearFiles()
  upCoverFileRef.value.clearFiles()
}
function cancelFiles(){
  //isMul.value = false;
  dialogVisible2.value = false;
}
async function uploadFiles(){}
async function uploadFile(){
    //console.log(upFileCover)
    //console.log(publishTime.value)
    if(upFile == null || upFileCover == null){
      ElMessage.error('未选择文件或封面')
      return;
    }
    if(name.value == '' || keyword.value[0] == '' || mainResp.value == '' ||
        uploadFileType.value == '' || uploadFileSpec.value == '' || uploadPermisson.value == -1){
        ElMessage.error('请将必填项填写完整')
        return;
    }
    let keywordString:string = '';
    for(const index in keyword.value){
      if(keyword.value[index] != ''){
        keywordString += keyword.value[index];
        keywordString += ' ';
      }
    }
    //文件描述返回
    if(props.isMul){
      multiFileTable.value.push({
        upFile: upFile,
        cover: upFileCover,
        keywords: keywordString,  //string
        ofname: name.value,         //string
        ofkind: uploadFileSpec.value, //string
        mainresponsible1: mainResp.value, //string
        secondresponsible: minorRsp.value, //string
        publisher: publisher.value,     //string
        publishedplace: publisherPlace.value, //string
        publishedtime: publishTime.value, //string
        mainresponsible2: mainResp2.value, //string
        type: uploadFileType.value,
        resume: resume.value,
        permission: uploadPermisson.value,
      });
    }else{
      let desc = {
            keywords: keywordString,  //string
            ofname: name.value,         //string
            ofkind: uploadFileSpec.value, //string
            mainresponsible1: mainResp.value, //string
            secondresponsible: minorRsp.value, //string
            publisher: publisher.value,     //string
            publishedplace: publisherPlace.value, //string
            publishedtime: publishTime.value, //string
            mainresponsible2: mainResp2.value, //string
            type: uploadFileType.value,
            resume: resume.value,
            permission: uploadPermisson.value,
      };
      await upFileInterface(upFile, upFileCover, desc);
    } 
    dialogVisible.value = false 
    upFileRef.value.clearFiles()
    upCoverFileRef.value.clearFiles()
}
async function upFileInterface(upFileP, upFileCoverP, descP){
    const upFileLoading = ElLoading.service();
    let form = new FormData();
    //console.log(upFile);
    form.append('file', upFileP);
    form.append('cover', upFileCoverP);
    let descJson = JSON.stringify(descP); 
    let descBob  = new Blob([descJson], {
      type: 'application/json',
    });
    form.append('createRecive', descBob);
    let config = {
        headers: {'Content-Type': 'multipart/form-data'}
    }
    await axios.post('/root/create', form, config)
    .then(function (response){
      if(response.data.code == 200){
        ElMessage({
          message: '上传成功'
        })
      }else{
        ElMessage.error('上传失败，服务端返回:' + response.data.code)
      }
      upFileLoading.close()
    })
    .catch(function (error){
      ElMessage.error('上传失败, 失败代码:' + error)
      upFileLoading.close()
    })
}
//上传文件摘要信息填写
const keyword = ref(['','','','',''])
const name    = ref('')
const mainResp  = ref('')
const minorRsp  = ref('')
const publisher = ref('')
const publisherPlace = ref('')
const publishTime    = ref('')
const mainResp2      = ref('')
const uploadFileType = ref('')
const uploadFileSpec = ref('')
const resume         = ref('')
const uploadPermisson = ref<number>()

/*-----------------------------------------------------------------*/
//上传文件弹出对话框
const dialogVisible = ref(false)

const handleClose = (done: () => void) => {
  ElMessageBox.confirm('确认关闭?',{confirmButtonText: '确认',
      cancelButtonText: '取消',})
    .then(() => {
      upFileRef.value.clearFiles()
      upCoverFileRef.value.clearFiles()
      upFile = null
      upFileCover = null
      dialogVisible2.value = false;
      //console.log(upFile)
      done()
    })
    .catch(() => {
      // catch error
    })
}
//文件类型选择
</script>
<template>
<el-button v-if="!isMul" type="primary"  style="width:100px;" @click="clickUploadFile">
    文件上传
</el-button >
<el-button v-if="isMul" type="primary"  style="width:100px;" @click="clickUploadFiles">多文件上传</el-button>
<el-dialog
    v-model="dialogVisible2"
    width="70%"
    title="多文件上传"
    :before-close="handleClose"
    class="dialog"
    align-center
>
    <el-form
    label-width="60px"
    :model="formLabelAlign"
    style="max-width: 98%;"
    >
    <el-form-item label="标题:">
        <el-input placeholder="输入标题" v-model="formLabelAlign.ofname" />
    </el-form-item>
    <el-form-item label="题记:">
        <el-input
        v-model="formLabelAlign.inscriptions"
        :rows="2"
        type="textarea"
        placeholder="输入题记"
        />
    </el-form-item>
    <el-form-item label="简介:">
        <el-input
        v-model="formLabelAlign.brief"
        :rows="4"
        type="textarea"
        placeholder="输入简介"
        />
    </el-form-item>
    </el-form>
    <div style="margin-left:1%; margin-bottom: 20px;"><span>文件:</span></div>
    <div style="border-bottom: 1px solid; width:90%; margin-left:5%;"></div>
    <el-table :data="multiFileTable" style="width: 90%; margin-left:5%;" max-height="250">
    <el-table-column fixed property="ofname" label="标题" width="200" />
    <el-table-column property="keywords" label="关键词" width="144" />
    <el-table-column property="mainresponsible1" label="主要责任者1" width="110" />
    <el-table-column property="mainresponsible2" label="主要责任者2" width="110" />
    <el-table-column property="secondresponsibleminrRes" label="次要责任者" width="100" />
    <el-table-column property="publisher" label="出版者" width="120" />
    <el-table-column property="publishedplace" label="出版地" width="80" />
    <el-table-column property="publishedtime" label="出版时间" width="110" />
    <el-table-column property="ofkind" label="专题" width="100" />
    <el-table-column property="type" label="类型" width="80" />
    <el-table-column fixed="right">
        <template #default="scope">
        <el-button type="primary" 
            link
            size="small"
            @click.prevent="deleteRow(scope.$index)"
        >
            删除文件
        </el-button  >
        </template>
    </el-table-column>
    </el-table>
    <el-button type="primary"   style="width: 90%; margin-left: 5%;" @click="onAddItem"
    >点击添加文件</el-button>
    <template #footer>
    <span class="dialog-footer">
    <el-button  @click="cancelFiles">取消</el-button>
    <el-button type="primary"  @click="uploadFiles" >
        确认
    </el-button >
    </span>
</template>
</el-dialog>
<el-dialog
    v-model="dialogVisible"
    width="51%"
    title="文件上传"
    :before-close="handleClose"
    class="dialog g"
    align-center
>
<el-upload
    ref="upFileRef"
    class="upload-demo"
    action="fake action"
    :auto-upload="false"
    drag
    limit="1"
    :on-change="getFile"
    :on-remove="deleteFile"
>
<el-icon class="el-icon--upload"><upload-filled /></el-icon>
<div class="el-upload__text">
    拖入文件或者/<em>点击此处选择文件</em><br/>
    <span style="color:brown; font-size:0.7em;">(支持PDF、图片、音频、视频文件)</span>
</div>
</el-upload>
<el-row>
    <el-col>
    <el-upload
        ref="upCoverFileRef"
        class="upload-demo"
        action="fake upload"
        :auto-upload="false"
        :on-change="getCoverFile"
        :on-remove="deleteCoverFile"
        accept=".png, .jpg, .jpeg, .bmp"
    >
        <template #trigger>
        <el-button type="primary" >选择封面</el-button>
        </template>
    </el-upload>
    </el-col>
</el-row>
<el-row style="margin-bottom: 10px; font-size:0.8em;">
<el-col :span="3" style="padding-left:34px;"><label class="xreq">标题:</label></el-col>
<el-col :span="20">
    <el-input v-model="name" clearable></el-input>
</el-col>
</el-row>
<el-row style="margin-bottom: 10px; font-size:0.8em;" class="wkeyWord">
<el-col :span="3" style="padding-left:18px;"><label class="xreq">&ensp;关键词:</label></el-col>
<el-col :span="3" ><el-input v-model="keyword[0]" clearable></el-input></el-col>
<el-col :span="3" :offset="1" ><el-input v-model="keyword[1]" clearable></el-input></el-col>
<el-col :span="3" :offset="1" ><el-input v-model="keyword[2]" clearable></el-input></el-col>
<el-col :span="3" :offset="1" ><el-input v-model="keyword[3]" clearable></el-input></el-col>
<el-col :span="3" :offset="1" ><el-input v-model="keyword[4]" clearable></el-input></el-col>
</el-row>
            
<el-row>
    <el-col :span="8">
        <el-row>
            <el-col :span="7" style="font-size: 1.1em;"><label class="xreq">主要责任者:</label></el-col>
            <el-col :span="11" :offset="2"><el-input v-model="mainResp" clearable></el-input></el-col>
            </el-row>
    </el-col>
    <el-col :span="8">
        <el-row>
            <el-col :span="7" style="font-size: 1.1em;"><label>次要责任者:</label></el-col>
            <el-col :span="11" :offset="2"><el-input v-model="minorRsp" clearable></el-input></el-col>
            </el-row>
    </el-col>
    <el-col :span="8">
        <el-row>
            <el-col :span="7" style="padding-left:0px;"><label>主要责任者2:</label></el-col>
            <el-col :span="11" :offset="2"><el-input v-model="mainResp2" clearable></el-input></el-col>
            </el-row>
    </el-col>
</el-row>
<el-row>
    <el-col :span="8">
        <el-row>
            <el-col :span="7" style="padding-left:25px;"><label>出版地:</label></el-col>
            <el-col :span="11" :offset="2"><el-input v-model="publisherPlace" clearable></el-input></el-col>
            </el-row>
    </el-col>
    <el-col :span="8">
        <el-row>
            <el-col :span="7" style="padding-left:14px;"><label>出版时间:</label></el-col>
            <el-col :span="11" :offset="2">
                <el-date-picker
                v-model="publishTime"
                type="date"
                placeholder="选择时间"
                format="YYYY/MM/DD"
                value-format="YYYY-MM-DD"
                style="width:100%;"
                />
                </el-col>
        </el-row>
    </el-col>
    <el-col :span="8">
        <el-row>
            <el-col :span="7" style="padding-left:25px;"><label>出版者:</label></el-col>
            <el-col :span="11" :offset="2"><el-input v-model="publisher" clearable></el-input></el-col>
        </el-row>
    </el-col>
</el-row>
<el-row>
    <el-col :span="8">
    <el-row>
        <el-col :span="7" style="padding-left:32px;"><label style="margin-top: 12%;" class="xreq" >类型:</label></el-col>
        <el-col :span="11" :offset="2">
        <el-select v-model="uploadFileType" class="m-2" placeholder="请选择类型">
            <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
            />
        </el-select>
        </el-col>
    </el-row>
    </el-col>
    <el-col :span="8">
        <el-row>
            <el-col :span="7" style="padding-left:32px;"><label style="margin-top: 12%;" class="xreq" >专题:</label></el-col>
            <el-col :span="11" :offset="2">
            <el-select v-model="uploadFileSpec" class="m-2" placeholder="请选择类型">
                <el-option
                v-for="item in optionsKinds"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                />
            </el-select>
            </el-col>
        </el-row>
        </el-col>
        <el-col :span="8">
        <el-row>
            <el-col :span="7" style="padding-left:32px;"><label style="margin-top: 12%;" class="xreq" >隐私:</label></el-col>
            <el-col :span="11" :offset="2">
            <el-select v-model="uploadPermisson" class="m-2" placeholder="请选择权限">
                <el-option
                v-for="item in optionPermission"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                />
            </el-select>
            </el-col>
        </el-row>
        </el-col> 
</el-row>
<el-row>
    <el-col :span="22" :offset="1">
    <el-input
        v-model="resume"
        :rows="3"
        type="textarea"
        placeholder="文件简介"
    />
    </el-col>
</el-row>
<template #footer>
    <span class="dialog-footer">
    <el-button  @click="cancelFile">取消</el-button>
    <el-button type="primary"  @click="uploadFile" >
        确认
    </el-button >
    </span>
</template>
</el-dialog>
</template>