<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage, ElTable } from 'element-plus'
import { ElMessageBox } from 'element-plus'
import axios from 'axios'
import { ElLoading } from 'element-plus'
import { HostOriEntity, OriStruct, ServerEntity } from '../tool/classDef/classDef'
import type { UploadInstance } from 'element-plus'
import { kindsbackMap, optionPermission, optionsKinds} from '../tool/func/func'
import { useStore } from "vuex"

axios.defaults.baseURL = '/api'
const store = useStore();
//多文件管理
const uploadOriCover = ref<UploadInstance>()
const multiFile = ref<OriStruct[]>([]);
async function deleteMulFile(index){
  findOriLoading.value = true;
  //console.log(index);
  await axios({
    method: 'post',
    url: "/root/kssindexdelete",
    data: {
      id: multiFile.value[index].id,
      ofname: multiFile.value[index].ofname,
      picture_url: multiFile.value[index].picture_url,  
    }
  })
  .then(function (response){
    if(response.data.code == 200){
      ElMessage({
        message: "删除成功",
      });
      multiFile.value.splice(index, 1);
      store.commit('setAskIndexState', false);
    }else{
      ElMessage.error("删除失败，服务端返回: " + response.data.code);
    }
  })
  .catch(function (error){
    ElMessage.error("删除失败，错误代码：" + error);
  })
  .finally(function (){
    findOriLoading.value = false;
    return;
  })
}
const formLabelAlign = reactive({
  ofname: '',
  notes: '',
  resume: '',
  picture_url: '',
  id: null,
})
const oriCover = ref<File>(null);
function getOriCoverFile(file){
  oriCover.value = file.raw;
  formLabelAlign.picture_url = URL.createObjectURL(file.raw!);
  //console.log(file.raw);
  uploadOriCover.value!.clearFiles();
}

//点击口述史修改,弹出对话框并交互
const isOriUpdateFlag = ref(false);
let   picture_urlList = [];
let   file_urlList    = [];
let   kssCoverPicture_url;   //保存未修改前的图片路径
async function modefyMulFile(index){
    currentKssSeletcedIndex = index;
    findOriLoading.value = true;
    await axios({
      method: 'get',
      url: "/root/searchkssall",
      params: {
        searchInput: multiFile.value[index].ofname
      }
    })
    .then(function (response){
      if(response.data.code == 200){
        multiFileTable.value = [];
        multiFileTableCopy   = [];
        for(let i = 0; i < response.data.data.length; i++){
          multiFileTable.value.push({cover: null, upFile: null, desc: response.data.data[i]});
          multiFileTableCopy.push({cover: null, upFile: null, desc: response.data.data[i]});
          picture_urlList.push(response.data.data[i].picture_url);
          file_urlList.push(response.data.data[i].file_url);
        }
        dialogVisible2.value  = true;
        formLabelAlign.ofname = multiFile.value[index].ofname;
        formLabelAlign.notes  = multiFile.value[index].notes;
        formLabelAlign.resume = multiFile.value[index].resume;
        formLabelAlign.id     = multiFile.value[index].id;
        formLabelAlign.picture_url = "api\\" + multiFile.value[index].picture_url;
        kssCoverPicture_url   = multiFile.value[index].picture_url;
        isOriUpdateFlag.value = true;
      }
      else{
        ElMessage.error("发生网络错误，服务端返回: " + response.data.code);
      }
    })
    .catch(function (error){
      ElMessage.error("发生网络错误，错误代码：" + error);
    });
    findOriLoading.value = false;
}
//口述史上传和修改
let multiFormData           = new FormData();       //上传
let multiUpdateFormDate     = new FormData();       //修改
var currentKssSeletcedIndex = null;                 //记录当前选中的口述史
async function uploadFiles(){
    const upFileLoading = ElLoading.service();
    if(isOriUpdateFlag.value){                 //修改
      let multiUpdateNewItemList : fileUpMsg[] = [];         //新增加的口述史内文件
      let multiUpdateDelItemList = [];         //现有的口述史的文件内删除的文件
      for(let i = 0; i < multiFileTable.value.length; i++){
        if(multiFileTable.value[i].cover != null){
          multiFileTable.value[i].desc.ofkind = formLabelAlign.ofname;
          multiUpdateNewItemList.push(multiFileTable.value[i]);
        }
      }
      for(let i = 0; i < multiFileTableCopy.length; i++){
        if(multiFileTable.value.find((item) => { return JSON.stringify(item.desc) == JSON.stringify(multiFileTableCopy[i].desc); }) == undefined){
          multiUpdateDelItemList.push({ id: multiFileTableCopy[i].desc.id,
                                        picture_url: picture_urlList[i],
                                        file_url: file_urlList[i]});
        }
      }
      await dataExchange(multiUpdateNewItemList, multiUpdateFormDate);
      if(oriCover.value != null)
        multiUpdateFormDate.append("indexcover", oriCover.value);
      let formLabelAlignCopy = { ...formLabelAlign };
      formLabelAlignCopy.picture_url = kssCoverPicture_url;
      let kssindexJson = JSON.stringify(formLabelAlignCopy);
      let kssindexBlob = new Blob([kssindexJson],{
        type: 'application/json',
      });
      multiUpdateFormDate.append("kssIndexDscb", kssindexBlob);
      if(multiUpdateDelItemList.length > 0){
        let deleteIdListJSON = JSON.stringify(multiUpdateDelItemList);
        let deleteIdListBlob = new Blob([deleteIdListJSON], { type: 'application/json' });
        multiUpdateFormDate.append("deleteIdList", deleteIdListBlob);
      }
      let config = {
        headers: {'Content-Type': 'multipart/form-data'}
      }
      await axios.post("/root/kssUpdate", multiUpdateFormDate, config)
      .then(function (response){
        if(response.data.code == 200){
          ElMessage({
            message: "修改成功",
          });
          multiFile.value[currentKssSeletcedIndex].ofname = response.data.data.ofname;
          multiFile.value[currentKssSeletcedIndex].notes  = response.data.data.notes;
          multiFile.value[currentKssSeletcedIndex].resume = response.data.data.resume;
          multiFile.value[currentKssSeletcedIndex].picture_url = response.data.data.picture_url;
          store.commit('setAskIndexState', false);
          upFileLoading.close();
          dialogVisible2.value = false; //关闭对话框
        }else{
          ElMessage.error("修改失败，服务端返回: " + response.data.code);
          upFileLoading.close();
        }
      })
      .catch(function (error){
        ElMessage.error("修改失败，失败代码: " + error);
        upFileLoading.close();
      });
    }
    else{                                       //上传
      await dataExchange(multiFileTable.value, multiFormData);
      multiFormData.append("indexcover", oriCover.value);
      let kisindexJson = JSON.stringify(formLabelAlign);
      let kisindexBlob = new Blob([kisindexJson],{
        type: 'application/json',
      });
      multiFormData.append("kssindex_noid", kisindexBlob);
      let config = {
          headers: {'Content-Type': 'multipart/form-data'}
      }
      let closeControlFlag = 1;
      await axios.post("/root/ksscreateall", multiFormData, config)
      .then(function (response){
        if(response.data.code == 200){
          ElMessage({
            message: "上传成功",
          });
          closeControlFlag = 0;
        }else{
          ElMessage.error("上传失败，服务端返回: " + response.data.code);
        }
      })
      .catch(function (error){
        ElMessage.error("上传失败，失败代码: " + error);
      });
      await findFileByName();
      upFileLoading.close();
      if(closeControlFlag != 1) dialogVisible2.value = false; //关闭对话框
    }
    oriCover.value = null;
    multiFormData = new FormData();
    multiUpdateFormDate = new FormData();
}
//将表格内文件以及描述等数据(src)，转换为Blob并填充到表单(dst)
async function dataExchange(src, dst){
  if(src.length == 0)                               //为空返回
    return;
  let descArray = [];
  for(let i = 0; i < src.length; i++){
    dst.append('coverlist', src[i].cover);           //文件数组
    dst.append("filelist", src[i].upFile);
    descArray.push(src[i].desc);                     //对象数组
  }
  let descJson = JSON.stringify(descArray);          //对象数组转换为JSON
  let descBob  = new Blob([descJson], {              //对象数组转换为Blob
    type: 'application/json',
  });
  dst.append('createRecivelist', descBob);           //对象数组添加到表单
}
/*-----------------------------------------------------------------*/
//多文件上传 标签相关变量
interface fileUpMsg{
  upFile: File,
  cover: File,
  desc: HostOriEntity,
}
const multiFileTable = ref<fileUpMsg[]>([]);
let   multiFileTableCopy : fileUpMsg [] = [];
const isMul = ref(false);
const deleteRow = (index: number) => {
  multiFileTable.value.splice(index, 1)
}
const onAddItem = async () => {
  //isMul.value = true;
  dialogVisible.value = true;
  //multiFileTable.value.push();
}
const dialogVisible2 = ref(false);
function clickUploadFiles(){
  isOriUpdateFlag.value = false; //关闭文件修改，开启文件上传
  multiFileTable.value = [];
  dialogVisible2.value = true;
  formLabelAlign.ofname = '';
  formLabelAlign.resume = '';
  formLabelAlign.notes = '';
  formLabelAlign.picture_url = '';
}
//单文件上传 相关变量
const upFileRef      = ref()
const upCoverFileRef = ref()
let upFileCover      = null
let upFile           = null   //数据文件
function clickUploadFile(){
  dialogVisible.value   = true
  clearUploadFied();
}
//清空文件上传框所有字段值
function clearUploadFied(){
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
  uploadPermisson.value = ''  //恢复权限字段下拉框
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
//单文件上传
async function uploadFile(){
    if(upFile == null || upFileCover == null){
      ElMessage.error('未选择文件或封面')
      return;
    }
    if(isMul.value) uploadFileSpec.value = "口述史资料";
    if(name.value == '' || keyword.value[0] == '' || mainResp.value == '' ||
        uploadFileType.value == '' || uploadFileSpec.value == '' || uploadPermisson.value === ''){
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
    if(isMul.value){
      multiFileTable.value.push({
        upFile: upFile,
        cover: upFileCover,
        desc: {
        id: 0,
        keywords: keywordString,           //string
        ofname: name.value,                //string
        ofkind: uploadFileSpec.value,      //string
        mainresponsible1: mainResp.value, //string
        secondresponsible: minorRsp.value, //string
        publisher: publisher.value,        //string
        publishedplace: publisherPlace.value, //string
        publishedtime: publishTime.value, //string
        mainresponsible2: mainResp2.value, //string
        oftype: uploadFileType.value,
        resume: resume.value,
        permission: uploadPermisson.value,
        },
      });
      //console.log(multiFileTable.value);
    }else{
      let desc = {
            id: 0,
            keywords: keywordString,  //string
            ofname: name.value,         //string
            ofkind: uploadFileSpec.value, //string
            mainresponsible1: mainResp.value, //string
            secondresponsible: minorRsp.value, //string
            publisher: publisher.value,     //string
            publishedplace: publisherPlace.value, //string
            publishedtime: publishTime.value,  //string
            mainresponsible2: mainResp2.value, //string
            oftype: uploadFileType.value,
            resume: resume.value,
            permission: uploadPermisson.value,
            //permission: optionPermission[uploadPermisson.value].label,
      };
      const upFileLoading = ElLoading.service();
      await upFileInterface(upFile, upFileCover, desc);
      await findFileByName();
      upFileLoading.close();
    } 
    dialogVisible.value = false 
    upFileRef.value.clearFiles()
    upCoverFileRef.value.clearFiles()
    clearUploadFied();
}
async function upFileInterface(upFileP:File, upFileCoverP:File, descP:HostOriEntity){
    let form = new FormData();
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
    await axios.post('/root/7create', form, config)
    .then(function (response){
      if(response.data.code == 200){
        ElMessage({
          message: '上传成功'
        })
      }else{
        if(!isMul.value)
          ElMessage.error('上传失败，服务端返回:' + response.data.code)
      }
    })
    .catch(function (error){
      if(!isMul.value)
        ElMessage.error('上传失败, 失败代码:' + error)
    })
}
//文件查询
const findFileName = ref('')
const findFileLoading = ref(false)
const findOriLoading  = ref(false);
async function findFileByName(){
  if(isMul.value == false){
      findFileLoading.value = true
      await axios(
      {
        method: 'get',
        url: '/root/7searchall',
        params: {
          info: findFileName.value //get参数用户输入值 string
        },
      })
      .then(function (response){
        if(response.data.code == 200){
          tableData.value = response.data.data //期望返回值是一个数组
          for(let i = 0; i < tableData.value.length; i++){
            tableData.value[i].permission = optionPermission[tableData.value[i].permission].label //转换为汉字显示
          }
        }
        else{
          ElMessage.error('查询失败,服务端返回:' + response.status)
        }
      })
      .catch(function (error){
          ElMessage.error("查询出错! 错误代码:" + error.response.status)
      })
      findFileLoading.value = false
  }else{
      findOriLoading.value = true;
      await axios({
        method: 'get',
        url: '/root/searchkssindexall',
        params: {
          searchInput:  findFileName.value  //get参数用户输入值 string
        },
      })
      .then(function (response){
        if(response.data.code == 200){
          multiFile.value = response.data.data;
          //console.log(response.data.data);
        }else{
          ElMessage.error('查询失败,服务端返回:' + response.status)
        }
      })
      .catch(function (error){
          ElMessage.error("查询出错! 错误代码:" + error.response.status)
      });
      findOriLoading.value = false;
  }
}
//文件删除
interface deleteType{
  id: Number,
  oftype: string,
  file_url: string,
  picture_url: string,
}
//发送的是数组，数组内每个元素都是一个对象，拥有两个成员，id:string type:string
//通过这两个值可以确定表内要删除的表项
//返回值也只需要失败还是成功
async function deleteFileToSev(){
    if(multipleSelection.value.length == 0){
      ElMessage.error('未选中删除项')
      return;
    }
    let postDataArray:Array<deleteType> = []
    for(const index in multipleSelection.value){
      //console.log(index)
      postDataArray.push({id: multipleSelection.value[index].id,
                          oftype: multipleSelection.value[index].oftype,
                          file_url: multipleSelection.value[index].file_url,
                          picture_url: multipleSelection.value[index].picture_url}) 
    }
    findFileLoading.value = true
    await axios({
      method: 'post',
      url: '/root/7delete',
      data: postDataArray          
    })
    .then(function (response){
      //console.log(response.data)
      if(response.data.code == 200){
        for(let index = tableData.value.length; index >= 0; index--){
          if(multipleSelection.value.indexOf(tableData.value[index]) != -1){
            tableData.value.splice(Number(index), 1)
          }
        }
        store.commit('setAskIndexState', false);
      }else{
        ElMessage.error('删除失败, 服务端返回:' + response.data.code)
      }
    })
    .catch(function (error){
      ElMessage.error('删除失败, 网络返回代码:' + error.response.status)
    })
    findFileLoading.value = false
}

//文件修改
const fileModefySelector = ref('');
const kindFlag = ref(0);
const iskindSelect = ref(false);
const notkindSelect = ref(false);
const isSelectedFied = ref(false);       //是否选择了文件字段下拉框
const modefiyFileFlag = ref(false);      
const selectedFileId = ref<ServerEntity>(null);  //选择修改的文件
const selectedFileField = ref(''); //选择修改的文件字段
const modefileFileValue = ref(''); //用户输入的新的值（修改后的值）
function returnOption(){
  if(kindFlag.value == 0)
    return optionsKinds;
  else if(kindFlag.value == 2)
    return optionPermission;
  else 
    return null
}
function selectedFieldChange(){
  modefileFileValue.value = ''
  fileModefySelector.value = ''
  //selectedFileField.value = ''
  if(selectedFileId.value == null)
  {
    ElMessage.error('请先选择文件再选择字段');
    return;
  }
  isSelectedFied.value = true;
  if(selectedFileField.value == 'ofkind' || selectedFileField.value == 'permission'){
    iskindSelect.value = true;
    notkindSelect.value = false;
    if(selectedFileField.value == 'ofkind'){
      kindFlag.value = 0;
    }
    else{
      kindFlag.value = 2;
    }
  }else{
    notkindSelect.value = true;
    iskindSelect.value = false;
  }
}
async function modifyFile(){
  //selectedFileId.value.title = modefileFileValue.value
  //console.log(multipleSelection.value)
  while(true){
    if(selectedFileId.value == null){
      ElMessage({
        message: "修改失败，未选择修改的文件", 
        type: 'warning',
      });
      break;
    }
    if(selectedFileField.value == '' && modefileFileValue.value == ''){
      if(selectedFileField.value == ''){
        ElMessage({
          message: "修改失败，未选择字段",
          type: 'warning',
        })
        break;
      }
      if(modefileFileValue.value == ''){
        ElMessage({
          message: "修改失败，未写入新的修改值",
          type: 'warning',
        })
        break;
      }
    }
    //break;
    const loadingIns = ElLoading.service();
    await axios.post('/root/7update', {
      id: selectedFileId.value.id,     //需要修改的表项 id
      oftype: selectedFileId.value.oftype, //需要修改的表项 type
      changeline: selectedFileField.value,  //需要修改的字段 type keyword等等
      value: modefileFileValue.value + fileModefySelector.value, //用户输入的字段（type/keyword等等）新的值
    })
    //需要返回是否修改成功
    .then(function (response){
      if(response.data.code == 200){
        selectedFileId.value[selectedFileField.value] = modefileFileValue.value + fileModefySelector.value
        if(selectedFileField.value == 'permission'){
          selectedFileId.value[selectedFileField.value] = optionPermission[fileModefySelector.value].label
        } 
        ElMessage({
          message: "修改成功",
        })
        store.commit('setAskIndexState', false);
      }
      else{
        ElMessage.error('修改文件失败, 服务端返回' + response.data.code);
      }
      loadingIns.close();
    })
    .catch(function (error){
      ElMessage.error("出现错误，错误代码:" + error.response.status);
      loadingIns.close();
    })
    break;
  }
  //modefiyFileFlag.value = false; //对话框消失
  isSelectedFied.value = false;  //恢复字段选择下拉框初始值
  selectedFileId.value = null;
  selectedFileField.value = '';
  modefileFileValue.value = '';
  iskindSelect.value = false;
  notkindSelect.value = false;
}
/*----------------------------------------------------------------*/
//添加到专题
const specialSelectorOption = ref([]);
const specialSelectedValue  = ref('');
const add2SpecialDilogFlag  = ref(false);

async function cilckAdd2Special(){
  specialSelectorOption.value = [];
  specialSelectedValue.value  = '';
  const loading = ElLoading.service();
  await axios({
    method: 'get',
    url:    '/root/searchspecialindexall',
    params: {
      searchReciveR: '',
    },
  })
  .then(function (response){
    if(response.data.code == 200){
      for(let i = 0; i < response.data.data.length; i++){
        specialSelectorOption.value.push({
          key: response.data.data[i].id,
          value: response.data.data[i].id,
          label: response.data.data[i].ofname,
        });
      }
      add2SpecialDilogFlag.value = true;
    }else{
      ElMessage.error("服务端返回错误代码");
    }
  })
  .catch(function (error){
    ElMessage.error("网络错误");
  });
  loading.close();
}
function add2SpecialCancel(){
  add2SpecialDilogFlag.value = false;
}
async function add2SpecialOK(){
  const loading = ElLoading.service();
  let ids = [];
  let kinds = [];
  for(let i = 0; i < multipleSelection.value.length; i++){
    ids.push(multipleSelection.value[i].id);
    kinds.push(kindsbackMap.get(multipleSelection.value[i].oftype));
  }
  await axios({
    method: 'post',
    url: '/root/add2special',
    data: {
      idList: ids,
      indexid: specialSelectedValue.value,
      kindList: kinds,
    },
  })
  .then(function (response){
    if(response.data.code == 200){
      if(response.data.data == "repeat")
        ElMessage.warning("添加失败, 该资料已在专题中");
      else
        ElMessage({
          message: "添加成功"
        });
    }else{
      ElMessage.error("添加失败，服务端返回错误");
    }
  })
  .catch(function (error){
    ElMessage.error("网络错误");
  });
  loading.close();
  add2SpecialDilogFlag.value = false;
}
/*-----------------------------------------------------------------*/
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
const uploadPermisson = ref<string>('')

const fileFiedOption = [
  {
    label: '关键字',
    field: 'keywords',
  },
  {
    label: '所属专题',
    field: 'ofkind',
  },
  {
    label: '主要责任者1',
    field: 'mainresponsible1',
  },
  {
    label: '主要责任者2',
    field: 'mainresponsible2'
  },
  {
    label: '次要责任者',
    field: 'secondresponsible'
  },
  {
    label: '出版者',
    field: 'publisher'
  },
  {
    label: '出版地',
    field: 'publishedplace'
  },
  {
    label: '出版时间',
    field: 'publishedtime'
  },
  {
    label: '权限',
    field: 'permission'
  },
  {
    label: '简介',
    field: 'resume'
  } 
];
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
      //isMul.value = false;
      dialogVisible2.value = false;
      //console.log(upFile)
      done()
    })
    .catch(() => {
      // catch error
    })
}
//文件类型选择
//const uploadFileType = ref('')
//const uploadFileSpec = ref('')
const options = [
  {
    value: '图书',
    label: '图书',
  },
  {
    value: '报纸',
    label: '报纸',
  },
  {
    value: '档案',
    label: '档案',
  },
  {
    value: '期刊',
    label: '期刊',
  },
  {
    value: '音频',
    label: '音频',
  },
  {
    value: '图片',
    label: '图片',
  },
  {
    value: '视频',
    label: '视频',
  },
]

const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const multipleSelection = ref<ServerEntity[]>([])
const toggleSelection = (rows?: ServerEntity[]) => {
  if (rows) {
    rows.forEach((row) => {
      // TODO: improvement typing when refactor table
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // ts-expect-error
      multipleTableRef.value!.toggleRowSelection(row, undefined)
    })
  } else {
    multipleTableRef.value!.clearSelection()
  }
}
const handleSelectionChange = (val: ServerEntity[]) => {
    multipleSelection.value = val
    //console.log(multipleSelection.value)
}

const tableData = ref<ServerEntity[]>([])

</script>

<template>
<div class="fileShow" style="width:95%; margin-left: 5%;">
    <el-row style="margin-top: 2%;">
        <el-col :span="13" style="margin-bottom:2%;">
            <el-input 
              v-model="findFileName"
              class="w-50 m-2"
              placeholder="输入文件名称"
              clearable 
            >
                <template #prefix>
                    <el-icon class="el-input__icon"><search /></el-icon>
                </template>
            </el-input>
        </el-col>
        <el-col :span="5">
            <el-button type="primary"  @click="findFileByName">查询</el-button>
        </el-col>
        <el-col :span="6">
          <span style="color:brown; font-size:0.9em;">管理口述史文件？</span>
          <el-switch
            v-model="isMul"
            inline-prompt
            active-text="是"
            inactive-text="否"
          />
        </el-col>
    </el-row>
    <div id="fileUp" style="margin-bottom: 2%;">
            <el-button v-if="!isMul" type="primary"  style="width:100px;" @click="clickUploadFile">
             文件上传
            </el-button  >

            <el-button v-if="isMul" type="primary"  style="width:100px;" @click="clickUploadFiles">
              口述史上传
            </el-button>
            <el-dialog
              v-model="dialogVisible2"
              width="900px"
              title="口述史资料创建"
              :before-close="handleClose"
              class="dialog"
              align-center
            >
              <el-form
                label-width="60px"
                :model="formLabelAlign"
                style="max-width: 98%;"
              >
                <el-form-item label="标题">
                  <el-input placeholder="输入标题" v-model="formLabelAlign.ofname" />
                </el-form-item>
                <el-form-item label="题记">
                  <el-input
                    v-model="formLabelAlign.notes"
                    :rows="2"
                    type="textarea"
                    placeholder="输入题记"
                  />
                </el-form-item>
                <el-form-item label="简介">
                  <el-input
                    v-model="formLabelAlign.resume"
                    :rows="4"
                    type="textarea"
                    placeholder="输入简介"
                  />
                </el-form-item>
                <el-form-item label="封面">
                  <el-upload
                    ref="uploadOriCover"
                    class="avatar-uploader"
                    action="fake"
                    :show-file-list="false"
                    :on-change="getOriCoverFile"
                    :limit="1"
                    :auto-upload="false"
                  >
                    <img v-if="formLabelAlign.picture_url" :src="formLabelAlign.picture_url" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                </el-upload>
                </el-form-item>
              </el-form>
              <div style="margin-left:1%; margin-bottom: 20px;"><span>文件列表</span></div>
              <div style="border-bottom: 1px solid; width:90%; margin-left:5%;"></div>
              <el-table :data="multiFileTable" style="width: 90%; margin-left:5%;" max-height="250">
                <el-table-column fixed property="desc.ofname" label="标题" width="120" :show-overflow-tooltip="true"/>
                <el-table-column prop="desc.keywords" label="关键词" width="120" :show-overflow-tooltip="true"/>
                <el-table-column prop="desc.mainresponsible1" label="主要责任者1" width="110" :show-overflow-tooltip="true"/>
                <el-table-column prop="desc.mainresponsible2" label="主要责任者2" width="110" :show-overflow-tooltip="true"/>
                <el-table-column prop="desc.secondresponsibleminrRes" label="次要责任者" width="100" :show-overflow-tooltip="true"/>
                <el-table-column prop="desc.publisher" label="出版者" width="120" :show-overflow-tooltip="true"/>
                <el-table-column prop="desc.publishedplace" label="出版地" width="80" :show-overflow-tooltip="true"/>
                <el-table-column prop="desc.publishedtime" label="出版时间" width="110" :show-overflow-tooltip="true"/>
                <el-table-column prop="desc.oftype" label="类型" width="80" :show-overflow-tooltip="true"/>
                <el-table-column prop="desc.permission" label="隐私" width="80" :show-overflow-tooltip="true"/>
                <el-table-column fixed="right" label="操作">
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
              width="900px"
              title="文件上传"
              :before-close="handleClose"
              class="dialog "
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
              accept=".pdf, .mp4, .png, .jpg, jpeg, bmp, .webm, .ogg, .mp3"
            >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
                拖入文件或者/<em>点击此处选择文件</em><br/>
                <span style="color:brown; font-size:0.7em;">支持PDF、图片(.png, .jpg, jpeg, bmp)、音频(mp3)、视频文件(.mp4, .webm, .ogg)</span>
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
                <el-col v-if="!isMul" :span="8">
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
    </div>
    <div class="line"></div>
    <el-table      
        ref="multipleTableRef"
        v-loading="findFileLoading"
        :data="tableData"
        style="width: 98%; border-bottom: 2px solid brown;"
        @selection-change="handleSelectionChange"
        height="480"
        :highlight-current-row="true"
        stripe
        empty-text="Null"
        v-if="!isMul"
    >
        <el-table-column type="selection" width="55" />
        <el-table-column fixed property="ofname" label="标题" width="120" :show-overflow-tooltip="true"/>
        <el-table-column property="keywords" label="关键词" width="124" :show-overflow-tooltip="true"/>
        <el-table-column property="mainresponsible1" label="主要责任者1" width="110" :show-overflow-tooltip="true"/>
        <el-table-column property="mainresponsible2" label="主要责任者2" width="110" :show-overflow-tooltip="true"/>
        <el-table-column property="secondresponsibleminrRes" label="次要责任者" width="100" :show-overflow-tooltip="true"/>
        <el-table-column property="publisher" label="出版者" width="120" :show-overflow-tooltip="true"/>
        <el-table-column property="publishedplace" label="出版地" width="80" :show-overflow-tooltip="true"/>
        <el-table-column property="publishedtime" label="出版时间" width="110" :show-overflow-tooltip="true"/>
        <el-table-column property="ofkind" label="专题" width="100" :show-overflow-tooltip="true"/>
        <el-table-column property="permission" label="权限" width="100" :show-overflow-tooltip="true"/>
        <el-table-column fixed="right" property="oftype" label="类型" :show-overflow-tooltip="true"/>
    </el-table>
    <div style="margin-top: 20px" v-if="!isMul" >
        <el-button type="primary"  @click="toggleSelection()">清除选择</el-button>
        <el-popconfirm title="确认删除?" @confirm="deleteFileToSev">
          <template #reference>
            <el-button type="primary"  style="margin-left: 2%;">删除所选</el-button>
          </template>
        </el-popconfirm>
        <el-button type="primary" style="margin-left: 2%;" @click="modefiyFileFlag = true">修改所选</el-button>
        <el-button type="primary" style="margin-left: 2%;" @click="cilckAdd2Special">添加到专题</el-button>
        <el-dialog align-center width="400px" v-model="add2SpecialDilogFlag" title="添加到专题">
          <el-row>
            <el-col :span="6" style="padding-top: 4px;">请选择专题：</el-col>
            <el-col :span="12">
              <el-select style="width: 250px;" v-model="specialSelectedValue" class="m-2" placeholder="请选择专题">
                <el-option
                  v-for="item in specialSelectorOption"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-col>
          </el-row> 
          <template #footer>
            <span class="dialog-footer">
              <el-button type="primary"  @click="add2SpecialCancel">取消</el-button>
              <el-button type="primary"  @click="add2SpecialOK" >
                    确认
              </el-button >
            </span>
          </template>
        </el-dialog>
        <el-dialog align-center width="700px" v-model="modefiyFileFlag" title="文件字段修改">
          <el-row style="margin-bottom: 2%;">
            <el-col :span="4"><span style="display:inline-block; margin-top: 3%; margin-left:25%;">文章名称:</span></el-col>
            <el-col :span="16">
              <el-select v-model="selectedFileId" class="m-2" placeholder="选择标题">
                  <el-option
                    v-for="item in multipleSelection"
                    :key="item"
                    :label="item.ofname"
                    :value="item"
                  />
              </el-select>
            </el-col>
          </el-row>
          <el-row style="margin-bottom: 2%;">
            <el-col :span="4"><span style="display:inline-block; margin-top:3%;">选择修改字段:</span></el-col>
            <el-col :span="8">
            <el-select @change="selectedFieldChange" v-model="selectedFileField" class="m-2" placeholder="选择字段">
                  <el-option
                    v-for="item in fileFiedOption"
                    :key="item.field"
                    :label="item.label"
                    :value="item.field"
                  />
            </el-select>
            </el-col>
            <el-col :span="4"><span style="display:inline-block; margin-top:3%; margin-left:25%;">选择新的值:</span></el-col>
            <el-col :span="8"><el-select :disabled="!iskindSelect" v-model="fileModefySelector" placeholder="请选择">
                <el-option
                    v-for="item in returnOption()"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
            </el-select></el-col>
          </el-row>
          <el-row>
            <el-col :span="4"><span style="display:inline-block; margin-top:5%; margin-left: 25%;">原来的值:</span></el-col>
            <el-col :span="8"><el-input disabled :placeholder="isSelectedFied ? selectedFileId[selectedFileField] : ''"></el-input></el-col>
            <el-col :span="4"><span style="display:inline-block; margin-top:5%; margin-left: 25%;">输入新的值:</span></el-col>
            <el-col :span="8"><el-input :disabled="!notkindSelect" v-model="modefileFileValue" placeholder="关键字请用空格隔开"></el-input></el-col>
          </el-row>
          <template #footer>
            <span class="dialog-footer">
              <el-button type="primary"  @click="modefiyFileFlag = false; isSelectedFied=false; selectedFileId = null; selectedFileField = ''; ">取消</el-button>
              <el-button type="primary"  @click="modifyFile" >
                    确认
              </el-button >
            </span>
          </template>
        </el-dialog>
    </div>

    <el-table v-loading="findOriLoading" v-if="isMul" :data="multiFile" :fit="false" style="width: 98%; height:550px;">
        <el-table-column fixed prop="ofname" label="标题" :show-overflow-tooltip="true" min-width="150" />
        <el-table-column prop="notes" label="题记"  :show-overflow-tooltip="true" width="200" />
        <el-table-column prop="resume" label="简介" :show-overflow-tooltip="true" width="200" />
        <el-table-column prop="updateTime" label="更新时间"  :show-overflow-tooltip="true" width="100" />
        <el-table-column prop="createTime" label="创建时间"  :show-overflow-tooltip="true" width="100" />
        <el-table-column fixed="right" label="文件操作" width="120">
          <template #default="scope" >
            <el-button link type="primary" size="small" @click="deleteMulFile(scope.$index)"
              >删除</el-button>
            <el-button link type="primary" size="small" @click="modefyMulFile(scope.$index)"
              >修改</el-button>
          </template>
        </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
label.xreq:before{
    content: '*';
    color: red;
}
#fileUp .dialog .el-col{
    margin-bottom: 10px;
    font-size: 0.8em;
}
#fileUp .dialog .el-col label{
    margin-top:5px;
    display: inline-block;
}
.line{
    height:2px;
    border-top:2px solid brown;
    width: 98%;
}

.el-table__header-wrapper{
  --el-table-border: 2px solid brown;
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