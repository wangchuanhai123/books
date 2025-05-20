<script setup lang="ts">
import { PropType } from "vue"
const props = defineProps({
    ulItem: {
        type: Array as PropType<String[]>,
        default: () => ['NULL'],
    },
    kind:  {
        type: String,
        default: () => 'NULL',
    }
})
</script>

<template>
<div class="dividHorizontal"></div>
<el-row style="color:brown;">
    <el-col :span="2" :offset="2"><el-icon><CaretBottom /></el-icon></el-col>
    <el-col :span="3">{{kind}}</el-col>
</el-row>
<ul style="color:brown; font-size: 0.8em; margin: 0; margin-left: 25px;">
    <li v-for="item in ulItem">{{item}}</li>
</ul>
<div style="height: 30px;"></div>
</template>

<style scoped>
.dividHorizontal{
    height: 1px;
    border-top: 1px solid rgb(200, 200, 200);
    width: 100%;
    margin-top: 9px;
    margin-bottom: 15px;
}
</style>