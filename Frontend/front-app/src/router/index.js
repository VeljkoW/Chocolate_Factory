import { createRouter, createWebHashHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import FactoryView from '../views/FactoryView.vue';

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/factory/:id",
    name: "FactoryView",
    component: FactoryView,
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
