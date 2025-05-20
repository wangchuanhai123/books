<script setup lang="ts">
import { PropType } from "vue"
import { useRouter } from "vue-router";
import { ServerEntity } from '../tool/classDef/classDef'

const props = defineProps({
    article: {
        type: Array as PropType<ServerEntity[]>,
        default: () => [],
    },
})
const router = useRouter();
function gotoDetailPage(index){
    if(props.article[index].ofkind == '口述史资料' || props.article[index].ofkind == '特色专题研究')
        router.push({
            path: 'set',
            query: { notes: props.article[index].notes, create_time: props.article[index].create_time, update_time: props.article[index].update_time,
                     picture_url: props.article[index].picture_url, ofname: props.article[index].ofname,permission: props.article[index].permission,
                     resume: props.article[index].resume,oftype: props.article[index].oftype}
        });
    router.push({
        path: 'detail',
        query: { keyword: props.article[index].keywords, mainresponsible1: props.article[index].mainresponsible1,
                 mainresponsible2: props.article[index].mainresponsible2, secondresponsible: props.article[index].secondresponsible,
                 ofkind: props.article[index].ofkind, create_time: props.article[index].create_time, update_time: props.article[index].update_time,
                 picture_url: props.article[index].picture_url, file_url: props.article[index].file_url, ofname: props.article[index].ofname,
                 publisher: props.article[index].publisher, publishedplace: props.article[index].publishedplace, publishedtime: props.article[index].publishedtime,
                 oftype: props.article[index].oftype, permission: props.article[index].permission, resume: props.article[index].resume},
    });
}
</script>

<template>
    <el-row class="cube">
        <el-col :span="4" v-for="(item, index) in article" >
            <div style="height: 75%; width: 80; margin: 4%;">
                <el-image :src=" 'api\\' + item.picture_url" fit="fill" style="width:100%; height:100%;" />
            </div>
            <div class="ofname" style="margin-left: 2%;">
                <button style="border:0px; background-color:white;" @click="gotoDetailPage(index)" ><span>{{item.ofname}}</span></button>
            </div>
       </el-col>
    </el-row>
</template>

<style scoped>
.ofname{
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.cube{
    height: 150px;
    width: 94%;
    margin-bottom: 20px;
}
.cube .el-col{
    border: 1px solid black;
    margin-right: 23.5px;
}
</style>