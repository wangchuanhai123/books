import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import axios from "axios";

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/usr",
      name: "usr",
      component: () => import("../views/UserView.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../views/LoginView.vue"),
    },
    {
      path: "/regis",
      name: "regis",
      component: () => import("../views/RegisteredView.vue"),
    },
    {
      path: "/scv",
      component: () => import("../views/SearchContentView.vue"),
    },
    {
      path: "/pdf",
      component: () => import("../views/PDFView.vue"),
    },
    {
      path: "/video",
      component: () => import("../views/VideoPlayView.vue"),
    },
    {
      path: "/music",
      component: () => import("../views/MusicPlayView.vue"),
    },
    {
      path: "/detail",
      component: () => import("../views/DetailView.vue"),
    },
    {
      path: "/set",
      component: () => import("../views/SetDetailView.vue"),
    },
  ],
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (to.matched.some(record => record.meta.requiresAuth) && !token) {
    next({ name: 'login' });
  } else {
    next();
  }
});


export default router;
