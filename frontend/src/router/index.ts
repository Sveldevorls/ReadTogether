import { createRouter, createWebHistory } from 'vue-router'
import IndexPage from '@/pages/IndexPage.vue'
import LoginPage from '@/pages/LoginPage.vue'
import RegisterPage from '@/pages/RegisterPage.vue'
import UserProfilePage from '@/pages/UserProfilePage.vue'
import SettingsPage from '@/pages/SettingsPage.vue'
import { roles } from '@/util/enums'
import { useUserStore } from '@/util/userStore'

declare module 'vue-router' {
  interface RouteMeta {
    requiresAuth: boolean
    minimalRole: roles
  }
}

const routes = [
  { path: "/", component: IndexPage },
  { path: "/login", component: LoginPage },
  { path: "/register", component: RegisterPage },
  { path: "/users/:username", component: UserProfilePage },
  { path: "/settings", component: SettingsPage, meta: { requiresAuth: true, minimalRole: roles.user } },
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
