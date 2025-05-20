<script setup lang="ts">
import { ref, onMounted, onBeforeMount, computed, nextTick } from 'vue'
import { useRoute } from "vue-router"
import { Search } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import SearchKinds from "../components/SearchKinds.vue"
import ResultBookCard from "../components/SearchResoultBookCard.vue"
import ResultBookCube from "../components/SearchResoultBookCube.vue"
//import SecondSearch   from "../components/SecondSearchBlock.vue"
import { Article } from '../tool/classDef/classDef'
import { ElLoading, ElMessage } from 'element-plus'
import { kindsbackMap, kindsMap } from '../tool/func/func'
import axios from 'axios'
axios.defaults.baseURL = '/api'
/*--------------------------------------------------------------------*/
const curKindDefaulVal = ref('所有')
onBeforeMount( async () => {
    //console.log("beforeMount")
    //console.log(homeType)
    //console.log(homeValue == '')
    //console.log(homeAccording)
    //curKindDefaulVal.value = kindsMap[Number(homeType)]
    if(homeType.length == 1)
        curKindDefaulVal.value = kindsMap[Number(homeType)];
    await askForSearchBtn(String(homeValue), String(homeAccording), String(homeType))
    //renderChart();
})
/*--------------------------------------------------------------------*/
//绘制搜索结果图表
onMounted(async () => {
    //await askForSearchBtn(String(homeValue), String(homeAccording), String(homeType));
    renderChart();
})
function renderChart(){
    let resultChart = echarts.init(document.getElementById("resultChart"))
    resultChart.setOption({
        xAxis: {
            data: histogramX.value,
            name: "年份",
        },
        yAxis: {
            name: '数量',
        },
        grid: {
            top:  '30px',
            left: '20px',
            right: '50px',
            bottom: '10px',
            containLabel: true,
        },
        series: [
        {
            type: 'bar',
            data: histogramY.value,
            itemStyle:{
                color: '#016989',     
            },
        }
        ]
   })
}
/*-------------------------------------------------------*/
//页面状态值
//vue router传递从参数，从父组件获得信息
const route = useRoute()
//home页面输入的搜索值
let homeType  = route.query.type          //用户查询类型
let homeValue = route.query.value         //用户输入查询内容
let homeAccording = route.query.according //用户输入查询依据
//seach页面再次输入的搜索值
const input3 = ref('')                    //用户输入搜索内容
const select = ref('default')             //用户输入查询依据
const checkList = ref('')                 //用户类型选择种类数组
//后端返回数据反馈显示值
const searchResultTotal = ref(0)          //搜索结果数量
const pageSize3 = ref(20)                 //单页显示数量
const histogramY = ref(['0', '0', '0', '0', '0', '0'])                    //柱状图Y值
const histogramX = ref(['2000', '2001', '2002', '2003', '2004', '2005'])  //柱状图X值
const articleAbstracts = ref<Article[]>([])           //搜索结果文章摘要
const articleImg       = ref<Array<Array<any>>>([[]]) //搜索结果
/*发送搜索数据，期望收到检索结果文章摘要、文章数量
*       inputValue: string     用户输入值
*       inputAccording: string 用户搜索依据，依据题名、关键字、等等
*       inputTypes: string     用户期望得到的文章类型，报纸、图书等
*接口期望接收值
*       response.data.total                            搜索结果数量
*       response.dada.years: 数组对象？                 搜索结构按年份统计数组
*       response.data.article: 包含Ariticle类型的数组   搜索结果文章摘要（包含图片url链接）
*/
async function askForSearchBtn(otherInputValue:string, otherInputAccording:string, otherInputTypes:string){
    if(otherInputTypes == '')
        otherInputTypes = '8'
    await nextTick(async function (){
        const loadingDOMobj    = document.getElementById('loading') 
        const loading = ElLoading.service({
            target: loadingDOMobj
        })
        //console.log(loadingDOMobj)
        await axios(
        {
            method: 'get',
            url: '/home/search',
            params: {
                infos: otherInputValue,
                scope: otherInputAccording,
                types: otherInputTypes,   
            },
        })    
        .then(function (response){
            if(response.data.code == 200){
                //console.log(response.data)
                searchResultTotal.value = response.data.data.sum
                //console.log(searchResultTotal.value)
                histogramX.value        = response.data.data.ofyearList
                //console.log(histogramX.value)
                histogramY.value        = response.data.data.ofnumberList
                //console.log(histogramY.value)
                articleAbstracts.value  = response.data.data.all_maxList
                //console.log(articleAbstracts.value)
            }else{
                ElMessage.error('搜索失败，服务端返回:' + response.data.code)
            }
        })
        .catch(function (error){
            ElMessage.error('搜索失败, 网络代码:' + error.response.status);
        })
        loading.close()
        renderChart();
        abstractToImg();
    })
}
function enterSearch(){
    askForSearchBtn(String(homeValue), String(homeAccording), String(homeType))
}
//排序
async function sortByPubTime(){
    const loadingDOMobj = document.getElementById('loading'); 
    const loading       = ElLoading.service({
        target: loadingDOMobj
    });
    await articleAbstracts.value.sort((v1, v2) => {
        const yearV1 = Number(v1.publishedtime.substring(0, 4));
        const yearV2 = Number(v2.publishedtime.substring(0, 4));
        let mv1: number, mv2: number;
        let d1: number, d2: number;
        if(v1.publishedtime[6] == '-'){
            mv1 = Number(v1.publishedtime.substring(5, 6));
            d1 = Number(v1.publishedtime.substring(7, 9));
        }
        else{
            mv1 = Number(v1.publishedtime.substring(5, 7));
            d1 = Number(v1.publishedtime.substring(8, 10));
        }
        if(v2.publishedtime[6] == '-'){
            mv2 = Number(v2.publishedtime.substring(5, 6));
            d2 = Number(v2.publishedtime.substring(7, 9));
        }
        else{
            mv2 = Number(v2.publishedtime.substring(5, 7));
            d2 = Number(v2.publishedtime.substring(8, 10));
        }
        //console.log(yearV1)
        //console.log(mv1)
        //console.log(d1)
        if(yearV1 > yearV2)
            return -1;
        else if(yearV1 == yearV2){
            if(mv1 > mv2)
                return -1;
            else if(mv1 == mv2){
                if(d1 > d2)
                    return -1;
                else
                    return 1;
            }
            else
                return 1;
        }
        else
            return 1;
    });
    loading.close();
}
async function sortByUpTime(){
    const loadingDOMobj = document.getElementById('loading'); 
    const loading       = ElLoading.service({
        target: loadingDOMobj
    });
    await articleAbstracts.value.sort((v1, v2) => {
        const yearV1 = Number(v1.update_time.substring(0, 4));
        const yearV2 = Number(v2.update_time.substring(0, 4));
        let mv1: number, mv2: number;
        let d1: number, d2: number;
        //console.log(v1.update_time)
        if(v1.update_time[6] == '-'){
            mv1 = Number(v1.update_time.substring(5, 6));
            d1 = Number(v1.update_time.substring(7, 9));
        }
        else{
            mv1 = Number(v1.update_time.substring(5, 7));
            d1 = Number(v1.update_time.substring(8, 10));
        }
        if(v2.update_time[6] == '-'){
            mv2 = Number(v2.update_time.substring(5, 6));
            d2 = Number(v2.update_time.substring(7, 9));
        }
        else{
            mv2 = Number(v2.update_time.substring(5, 7));
            d2 = Number(v2.update_time.substring(8, 10));
        }
        //console.log(yearV1)
        //console.log(mv1)
        //console.log(d1)
        if(yearV1 > yearV2)
            return -1;
        else if(yearV1 == yearV2){
            if(mv1 > mv2)
                return -1;
            else if(mv1 == mv2){
                if(d1 > d2)
                    return -1;
                else
                    return 1;
            }
            else
                return 1;
        }
        else
            return 1;
    });
    loading.close();
}
async function sortByDownTimes(){
    const loadingDOMobj = document.getElementById('loading'); 
    const loading       = ElLoading.service({
        target: loadingDOMobj
    });
    loading.close();
}
//列表类型数据转换为二维宫格数据显示
function abstractToImg(){
    articleImg.value = [[]];
    let k = 0;
    let num = articleAbstracts.value.length / 5
    //console.log(articleAbstracts.value.length);
    for(let m = 0; m < num; m++){
        articleImg.value[m] = [];
    }
    for(let i = 0; i < num; i++){
        for(let j = 0; j < 5; j++){
            //console.log(articleAbstracts.value[k])
            if(articleAbstracts.value[k] == undefined)
                return;
            articleImg.value[i][j] = articleAbstracts.value[k];
            k++
        }
    }
}
function checkListChange(e){
    let types = ''
    let index = 0;
    for(; index < e.length; index++){
        types += kindsbackMap.get(e[index])
    }
    checkList.value = types
    //console.log(checkList.value)
}
/*-------------------------------------------------------*/
//列表展示和缩略图显示的两种CSS模式切换
const listShow1Flag = ref(true)
const listShow2Flag = ref(false)
const dymStyle = computed(() => ({
    listShow1: listShow1Flag.value && !listShow2Flag.value,
    listShow2: listShow2Flag.value && !listShow1Flag.value,
}))
const dymStyle2 = computed(() => ({
    cubeShow1: listShow2Flag.value && !listShow1Flag.value,
    cubeShow2: listShow1Flag.value && !listShow2Flag.value,
}))
function listShowClick(){
    if(listShow1Flag.value == false){
        listShow1Flag.value = !listShow1Flag.value;
        listShow2Flag.value = !listShow2Flag.value;
    }
}
function cubeShowClick(){
    abstractToImg()
    if(listShow2Flag.value == false){
        listShow1Flag.value = !listShow1Flag.value;
        listShow2Flag.value = !listShow2Flag.value;
    }
}
/*-------------------------------------------------------*/
//下拉框选择
const options = [
  {
    value: 'ofname',
    label: '题名',
  },
  {
    value: 'keywords',
    label: '关键词',
  },
  {
    value: 'mainresponsible1',
    label: '责任者',
  },
  {
    value: 'publisher',
    label: '出版者',
  },
  {
    value: 'publishedplace',
    label: '出版地',
  },
  {
    value: 'default',
    label: '全部',
  }
]
/*-------------------------------------------------------*/
//页面跳转
const   gotoPage    = ref(1)
const   currentPage = ref(1)
function gotoPageFuc(){
    currentPage.value = Number(gotoPage.value)
}
function gotoPagination(){
    gotoPage.value = currentPage.value
}
const small = ref(false)
const background = ref(false)
const disabled = ref(false)
/*-------------------------------------------------------*/
</script>

<template>
<div class="parent-back">
    <div class="common-layout child-back">
        <el-container>
        <el-header class="header" style="height: 150px;">
            <div style="margin:3%; width:76%;">
                <div class="mt-4" style="width:99.4%;">
                    <el-input
                      v-model="input3"
                      placeholder="请输入搜索内容"
                      class="input-with-select"
                      size="large"
                      @keyup.enter.native="enterSearch"
                    >
                      <template #prepend>
                        <el-select size="large" v-model="select" style="width: 115px">
                          <el-option  v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                        </el-select>
                      </template>
                      <template #append>
                        <el-button @click="askForSearchBtn(input3, select, checkList)" class="btn" :icon="Search" />
                      </template>
                    </el-input>
                </div>
                <div><SearchKinds :checkListDefaultValue="curKindDefaulVal" @change="checkListChange"/></div>
            </div>
        </el-header>
        <el-container>
            <el-aside class="aside" >
                <div id="loading" class="searchResult">
                    <div style="padding-top:3%;">
                        <el-icon style="margin-left:3%; color:brown;"><Grid /></el-icon><h style="color:brown; font-size: 1.2em;">搜索结果</h>
                        <div class="dividLineH" style="margin-left:3%; width: 93%; margin-bottom:10px;"></div>
                        <div id="resultChart" style="margin-left:2%; width:90%; height: 150px"></div>
                    </div>
                    <div style="width:100%; padding-left: 2%;">
                        <el-row class="selectShow" style="margin-bottom: 18px; background-color:rgb(245, 245, 245); width:94%; height: 35px;">
                            <el-col :span="4"><button  @click="listShowClick" :class="dymStyle">列表展示</button></el-col>
                            <el-col :span="4"><button  @click="cubeShowClick" :class="dymStyle2">缩略图展示</button></el-col>
                        </el-row>
                        <div v-if="listShow1Flag">
                            <div class="listResultShow">
                                <button @click="sortByPubTime">按照出版时间</button>
                                <div class="dividLine"></div>
                                <button @click="sortByUpTime">按照上传时间</button>
                                <div class="dividLine"></div>
                                <button @click="sortByDownTimes">按照下载次顺序</button>
                                <span style="display: inline-block; margin-left: 370px; font-size: 0.3em; color:brown">
                                    共找到{{searchResultTotal}}条搜索结果
                                </span>
                            </div>
                            <ResultBookCard v-for="item in articleAbstracts.slice((currentPage - 1) * pageSize3, currentPage * pageSize3 + 1)" :article="item" />
                        </div>
                        <div v-if="listShow2Flag">
                            <ResultBookCube v-for="array in articleImg.slice((currentPage - 1) * pageSize3 / 5, currentPage * (pageSize3 / 5))" :article="array"/>
                        </div>
                        <el-row class="pageJump">
                            <el-col style="padding-top: 5px; padding-left: 62px;" :span="5" :offset="2">
                                <span style="font-size: 0.8em; color:brown;">共{{searchResultTotal}}条结果</span>
                            </el-col>
                            <el-col :span="12">
                                <el-pagination
                                    v-model:currentPage="currentPage"
                                    v-model:page-size="pageSize3"
                                    :small="small"
                                    :disabled="disabled"
                                    :background="background"
                                    layout="prev, pager, next"
                                    :total=searchResultTotal
                                    @current-change="gotoPagination"
                                />
                            </el-col>
                            <el-col style="font-size: 0.8em; color:brown;" :span="3">
                                <span>前往</span><el-input style="width: 30px; height: 70%; margin: 5px;" @change="gotoPageFuc" v-model="gotoPage" placeholder="1"/>页
                            </el-col>
                        </el-row>
                        
                      <div style="height:30px; margin-bottom: 30px;"></div>
                    </div>
                </div>
            </el-aside>
        </el-container>
        </el-container>
    </div>
</div>
</template>

<style lang="scss" scoped>
.header{
    display: flex;
    justify-content: center;
}
.dividLineH{
    height: 2px;
    border-top: 2px solid brown;
}
.pageJump .el-input{
    --el-input-border-radius: 5px;
}
.dividLine{
    display: inline-block;
    height: 12px;
    width: 2px;
    border-right: 2px solid black;
    margin-left: 15px;
    margin-right: 15px;
}
.listResultShow{
    width: 100%;
}
.listResultShow button{
    border: 0;
    background-color:rgba(255, 255, 255, 0.988);
}

.listResultShow button:hover{
    color:brown;
}
.el-aside::-webkit-scrollbar{
    width: 0;
    height: 0;
}

.selectShow button{
    width: 100%;
    height: 100%;
    border-radius: 0;
    border: 0;
}
.aside{
    min-height: 600px;
    width: 100%;
}
.selectShow button:hover{
    background-color: rgb(173, 37, 37);
    color:rgb(246, 248, 248);
}
.listShow1{
    padding-left: 3%;
    padding-top: 1%;
    background-color: rgb(173, 37, 37);
    color:rgb(246, 248, 248);
}

.listShow2{
    padding-left: 3%;
    padding-top: 1%; 
}
.cubeShow1{
    padding-left: 3%;
    padding-top: 1%;
    background-color: rgb(173, 37, 37);
    color:rgb(246, 248, 248);
}
.cubeShow2{
    padding-left: 3%;
    padding-top:  1%; 
}
.input-with-select .el-input-group__prepend {
    background-color: var(--el-fill-color-blank);
}

.searchResult{
    margin-top: 10px;
    width: 70%;
    margin-left: 15%; 
    box-shadow: 2px 2px 2px 2px rgb(193, 192, 192); 
    background-color:rgba(255, 255, 255, 0.988);
    min-height: 550px;
}


.el-input{
    --el-input-border-radius: 0;
}

.select{
    border-radius: 0;
}

.el-input-group__append .btn.is-circle{
    border-radius: 0%;
    border: 1px solid rgba(66, 66, 66, 0.708);
}
  
.el-input-group__append .btn{
    background-color: rgba(152, 8, 8, 0.875);
    color: aliceblue;
    height: 100%;
    border-radius: 0%;
}
</style>