import { createRouter, createWebHistory } from 'vue-router'
import Index from '@/pages/Index.vue'
import Login from '@/pages/Login.vue'
import Register from '@/pages/Register.vue'
import UserProfile from '@/pages/UserProfile.vue'
import Settings from '@/pages/Settings.vue'
import { roles } from '@/util/enums'
import { useUserStore } from '@/util/userStore'

declare module 'vue-router' {
  interface RouteMeta {
    requiresAuth: boolean
    minimalRole: roles
  }
}

const routes = [
  { path: "/", component: Index },
  { path: "/login", component: Login },
  { path: "/register", component: Register },
  { path: "/users/:username", component: UserProfile },
  { path: "/settings", component: Settings, meta: { requiresAuth: true, minimalRole: roles.user } },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
})

router.beforeEach(async (to) => {
  const userStore = useUserStore();
  if ((to.meta.requiresAuth)) {
    await userStore.verify();
    if (userStore.role < to.meta.minimalRole) return "/";
  }
})

export default router
