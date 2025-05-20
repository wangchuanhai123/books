<script setup lang="ts">
import { ref, reactive } from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import axios from "axios";

axios.defaults.baseURL = "/api";
const props = defineProps({
  multiFile: {},
});
const dialogVisible = ref(false);
function deleteMulFile() {}
function modefyMulFile() {
  dialogVisible.value = true;
}
</script>
<template>
  <el-table :data="multiFile" style="width: 98%; height: 550px">
    <el-table-column fixed prop="date" label="Date" width="150" />
    <el-table-column prop="name" label="Name" width="120" />
    <el-table-column prop="state" label="State" width="120" />
    <el-table-column prop="city" label="City" width="120" />
    <el-table-column prop="address" label="Address" width="600" />
    <el-table-column prop="zip" label="Zip" width="120" />
    <el-table-column fixed="right" label="文件操作" width="120">
      <template #default>
        <el-button link type="primary" size="small" @click="deleteMulFile"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>
<style scoped></style>
