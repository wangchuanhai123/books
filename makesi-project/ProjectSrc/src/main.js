import { createApp } from "vue";
import { createPinia } from "pinia";
import App from './App.vue';
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import { store } from "./stores/usr";
import zhCn from "element-plus/dist/locale/zh-cn.mjs";
import vue3videoPlay from "vue3-video-play"; // 引入组件
import "vue3-video-play/dist/style.css"; // 引入css
import "./assets/CSS/back.css";
import router from "@/router";

const app = createApp(App);

app.use(vue3videoPlay);
app.use(createPinia());
app.use(router);
app.use(ElementPlus, {
  locale: zhCn,
});
app.use(store);
//app.use(pdf);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
app.mount("#app");
