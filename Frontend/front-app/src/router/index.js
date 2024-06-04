import { createRouter, createWebHashHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import FactoryView from '../views/FactoryView.vue';
import ChocolateView from '../views/ChocolateView.vue';
import AddChocolateView from '../views/AddChocolateView.vue';
import EditChocolateView from '../views/EditChocolateView.vue';

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
  },
  {
    path: "/chocolate/:id",
    name: "ChocolateView",
    component: ChocolateView,
  },
  {
    path: "/addChocolate/:id",
    name: "AddChocolateView",
    component: AddChocolateView,
  },
  {
    path: "/editChocolate/:id",
    name: "EditChocolateView",
    component: EditChocolateView,
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
