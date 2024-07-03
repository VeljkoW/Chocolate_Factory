import { createRouter, createWebHashHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import FactoryView from '../views/FactoryView.vue';
import ChocolateView from '../views/ChocolateView.vue';
import AddChocolateView from '../views/AddChocolateView.vue';
import EditChocolateView from '../views/EditChocolateView.vue';
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';
import FactoryCreateView from '../views/FactoryCreateView.vue';
import EmployeeCreationView from '../views/EmployeeCreationView.vue';
import UsersView from '../views/UsersView.vue';

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
  },
  {
    path: "/factory/:id",
    name: "FactoryView",
    component: FactoryView,
  },
  {
    path: "/factorycreate",
    name: "FactoryCreateView",
    component: FactoryCreateView,
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
  },
  {
    path: "/createEmployee/:id",
    name: "EmployeeCreationView",
    component: EmployeeCreationView
  },
  {
    path: "/users",
    name: "UsersView",
    component: UsersView
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
