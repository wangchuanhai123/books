<script lang="ts" setup>
import { ref, onBeforeMount } from "vue";

const props = defineProps({
  checkListDefaultValue: {
    type: String,
    default: () => "所有",
  },
});
onBeforeMount(() => {
  checkList.value[0] = props.checkListDefaultValue;
});
const emit = defineEmits(["change"]);
const checkList = ref(["所有"]);
//使'所有'选项与其它选项互斥
function valueChange() {
  if (checkList.value.length == 0) {
    checkList.value[0] = "所有";
  } else if (checkList.value[0] == "所有") {
    checkList.value[0] = checkList.value.pop();
  } else {
    if (checkList.value[checkList.value.length - 1] == "所有") {
      checkList.value[0] = "所有";
      while (checkList.value.length > 1) {
        checkList.value.pop();
      }
    }
  }
  //console.log("checklist: " + checkList.value)
  emit("change", checkList.value);
}
</script>

<template>
  <el-checkbox-group class="check" @change="valueChange" v-model="checkList">
    <el-checkbox label="所有" />
    <el-checkbox label="图书" />
    <el-checkbox label="报纸" />
    <el-checkbox label="档案" />
    <el-checkbox label="期刊" />
    <el-checkbox label="音频" />
    <el-checkbox label="图片" />
    <el-checkbox label="视频" />
    <el-checkbox label="专题库" />
    <el-checkbox label="口述史" />
  </el-checkbox-group>
</template>

<style scoped>
.check .el-checkbox {
  color: rgba(237, 237, 237, 0.751);
}

.check {
  background-color: rgba(0, 0, 0, 0.64);
  padding-left: 2%;
  width: 97.4%;
}
</style>
