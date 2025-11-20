import { createRouter, createWebHistory } from 'vue-router'
import Index from '@/Index.vue'
import LoginPage from '@/LoginPage.vue'

const routes = [
  {path: "/", component: Index},
  {path: "/login", component: LoginPage},
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
})

export default router
