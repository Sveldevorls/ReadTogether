import Index from "@/pages/Index.vue";
import { roles } from "@/util/enums";
import { useUserStore } from "@/util/userStore";
import { createRouter, createWebHistory } from "vue-router";

import authRoutes from "./authRoutes";
import authorRoutes from "./authorRoutes";
import bookRoutes from "./bookRoutes";
import submissionsRoutes from "./submissionsRoutes";
import usersRoutes from "./usersRoutes";

declare module "vue-router" {
  interface RouteMeta {
    requiresAuth: boolean;
    minimalRole: roles;
    redirectTo?: string;
  }
}

const routes = [{ path: "/", component: Index }, ...usersRoutes, ...authRoutes, ...submissionsRoutes, ...authorRoutes, ...bookRoutes];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
});

router.beforeEach(async (to) => {
  if (to.meta.requiresAuth) {
    const userStore = useUserStore();
    await userStore.verify();
    if (userStore.role < to.meta.minimalRole) {
      return to.meta.redirectTo || "/";
    }
  }
});

export default router;
