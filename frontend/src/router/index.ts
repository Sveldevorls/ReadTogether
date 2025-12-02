import { createRouter, createWebHistory } from 'vue-router'
import Index from '@/pages/IndexPage.vue'
import LoginPage from '@/pages/LoginPage.vue'
import RegisterPage from '@/pages/RegisterPage.vue'

const routes = [
  {path: "/", component: Index},
  {path: "/login", component: LoginPage},
  {path: "/register", component: RegisterPage},
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
})

export default router
