<script lang="ts" setup>
import { Search } from "@element-plus/icons-vue";
</script>

<template>
  <el-button :icon="Search" circle />
</template>

<style scoped>
.el-button.is-circle {
  border-radius: 0%;
  border: 1px solid rgba(66, 66, 66, 0.708);
  height: 2.8em;
  width: 2.8em;
}

.el-button {
  background-color: rgba(152, 8, 8, 0.875);
  color: aliceblue;
}
</style>
