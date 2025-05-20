<script setup lang="ts">
import { RouterLink } from "vue-router"
import { ref } from "vue"
import { ElScrollbar } from 'element-plus'
import { useStore } from "vuex"
const store = useStore();
const imgArrayAddress = ref(store.getters.getAcademic); 
const length = imgArrayAddress.length
const itemWidth = 147
const itemMargin = 20
let curOffsetLeft = 0
let maxOffsetLeft = length * (itemWidth + itemMargin) - itemMargin * 5 - 10 - 6 * itemWidth;
const scrollCopnt = ref<InstanceType<typeof ElScrollbar>>()
function clickLeftArrow(){
    if(curOffsetLeft >= itemWidth){
        curOffsetLeft -= itemWidth
        scrollCopnt.value.setScrollLeft(curOffsetLeft);
    }   
}

function clickRightArrow(){
    if(curOffsetLeft <= maxOffsetLeft){
        curOffsetLeft += itemWidth
        scrollCopnt.value.setScrollLeft(curOffsetLeft)
    }   
}

</script>
<template>
    <el-row>
        <el-col :span="2">
            <ArrowLeft @click="clickLeftArrow" style="width: 1.5em; height: 1.5em; margin-left: 60%; margin-top: 95%"></ArrowLeft>
        </el-col>
        <el-col :span="20">
            <el-scrollbar ref="scrollCopnt">
                <div class="scrollbar-flex-content">
                <div v-for="item in imgArrayAddress" class="scrollbar-demo-item">
                    <div>
                        <div class="itemBack">
                            <el-image style="height:170px; width:135px;" :src="'api//' + item.picture_url" fit="cover" />
                        </div>
                        <div><span class="ofname">{{ item.ofname }}</span></div>
                    </div>
                </div>
                </div>
            </el-scrollbar>
        </el-col>
        <el-col :span="2">
            <ArrowRight @click="clickRightArrow" style="width: 1.5em; height: 1.5em; margin-left: 10%; margin-top: 95%"></ArrowRight>
        </el-col>
    </el-row>
</template>

<style scoped>
.ofname{
    font-size: larger;
}
.ofname:hover{
  text-decoration: underline;
  color: chocolate;
}
.el-row{
    height: 80%;
    margin-top: 1.5%;
}

.scrollbar-flex-content {
    display: flex;
}
.scrollbar-demo-item {
    flex-shrink: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 147px;
    height: 230px;
    margin-right: 20px;
    text-align: center;
    border-radius: 4px;
}

</style>
  