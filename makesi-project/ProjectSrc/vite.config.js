import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  base: "./",
  server: {
    /* 自动打开浏览器 */
    open: true,
    /* 设置为0.0.0.0则所有的地址均能访问 */
    host: "0.0.0.0",
    port: 8000,
    https: false,
    hotOnly: false,
    headers: {
      "Cache-Control": "no-cache, no-store, must-revalidate", // 禁用缓存
      "Pragma": "no-cache",
      "Expires": "0",
    },
    /* 使用代理 */
    proxy: {
      "/api": {
        target: "http://127.0.0.1:8088", //这里填入你要请求的接口的前缀
        //target: 'http://127.0.0.1:16000',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
      "/dwn": {
        target: "https://openaccess.thecvf.com",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/dwn/, ""),
      }
    },
  },
});
