<script lang="ts" setup>
import { ref } from "vue";

const value = ref("default");

const emit = defineEmits(["changes"]);
function optionsChange() {
  //console.log("value: " + value)
  emit("changes", value.value);
}
const options = [
  {
    value: "ofname",
    label: "题名",
  },
  {
    value: "keywords",
    label: "关键词",
  },
  {
    value: "mainresponsible1",
    label: "责任者",
  },
  {
    value: "publisher",
    label: "出版者",
  },
  {
    value: "publishedplace",
    label: "出版地",
  },
  {
    value: "default",
    label: "全部",
  },
];
</script>

<template>
  <el-select
    v-model="value"
    @change="optionsChange"
    class="select"
    placeholder="全部"
  >
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
</template>

<style>
.select .select-trigger .el-input {
  --el-input-border-radius: 0;
  height: 2.8em;
}
.select {
  width: 80%;
  margin-left: 20%;
}
.select .select-trigger .el-input .el-input__wrapper {
  padding-left: 20%;
}
</style>
