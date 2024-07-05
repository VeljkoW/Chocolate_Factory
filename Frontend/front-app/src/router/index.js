import { createRouter, createWebHashHistory } from 'vue-router';
import axios from 'axios';
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
import UserProfileView from '../views/UserProfileView.vue';
import EditUserProfileView from '../views/EditUserProfileView.vue';

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
    meta: { roles: ["Administrator"] },
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
  },
  {
    path: "/userprofile/:id",
    name: "UserProfileView",
    component: UserProfileView
  },
  {
    path: "/edituserprofile/:id",
    name: "EditUserProfileView",
    component: EditUserProfileView
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach(async (to, from, next) => {
  const token = localStorage.getItem('token');
  console.log(token);
  if (token) {
    try {
      const response = await axios.post("http://localhost:8080/WebShopAppREST/rest/user/jwt/decode", {
        token
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      console.log(response);

      if (response.status === 200) {
        let { id, username, role } = response.data;
        console.log(id, username, role);

        if (to.meta.roles) {
          if (to.meta.roles.includes(role)) {
            return next();
          } else {
            return next({ name: "home" });
          }
        } else {
          return next();
        }
      } else {
        localStorage.removeItem('token');
        return next({ name: 'home' });
      }
    } catch (error) {
      console.error('Failed to decode token:', error);
      localStorage.removeItem('token');
      return next({ name: 'home' });
    }
  } else {
    if (to.meta.roles) {
      return next({ name: "home" });
    } else {
      return next();
    }
  }

  next();
});

export default router