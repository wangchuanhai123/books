<template>
  <el-input
    @change="inputComplete"
    v-model="inputSearch"
    placeholder="请输入搜索内容"
  />
</template>

<script lang="ts" setup>
import { ref } from "vue";

const emit = defineEmits(["change"]);
const inputSearch = ref("");

function inputComplete() {
  //console.log("inputSearch: " + inputSearch)
  emit("change", inputSearch.value);
}
</script>

<style scoped>
.el-input {
  --el-input-border-radius: 0;
  height: 2.8em;
}
</style>
