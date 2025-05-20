<script setup lang="ts">
import { useStore } from "vuex";
import { ref } from "vue";
const store = useStore();
//console.log(store.getters.getOri);
//console.log(store.getters.getOri.length);
//const oriHisUrl = store.getters.getOri.length == 0 ? [
//{title: '加载失败', url: 'none'},
//{title: '加载失败', url: 'none'},
//{title: '加载失败', url: 'none'},
//{title: '加载失败', url: 'none'}] : ref(store.getters.getOri);
const oriHisUrl = ref(store.getters.getOri);
</script>

<template>
  <el-carousel :interval="4000" type="card" height="250px">
    <el-carousel-item v-for="item in oriHisUrl" :key="item">
      <el-container
        style="margin-left: 5%; margin-top: 3%; width: 80%; height: 90%"
      >
        <el-aside width="200px">
          <el-image
            style="height: 98%"
            :src="'api//' + item.picture_url"
            fit="cover"
          />
        </el-aside>
        <el-container>
          <el-header>
            <span class="ofname">{{ item.ofname }}</span>
          </el-header>
          <el-main
            ><span style="font-size: large">{{ item.notes }}</span></el-main
          >
        </el-container>
      </el-container>
    </el-carousel-item>
  </el-carousel>
</template>

<style scoped>
.ofname {
  font-size: xx-large;
}
.ofname:hover {
  text-decoration: underline;
  color: rgb(210, 51, 30);
}

.el-carousel__item:nth-child(2n) {
  background-color: #c8c8c8;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #c8c8c8;
}
</style>
