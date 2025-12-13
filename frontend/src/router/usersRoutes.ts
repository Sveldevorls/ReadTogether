import Settings from "@/pages/Settings.vue";
import UserProfile from "@/pages/UserProfile.vue";
import { roles } from "@/util/enums";

const usersRoutes = [
  {
    path: "/users/:username",
    component: UserProfile,
  },
  {
    path: "/settings",
    component: Settings,
    meta: { requiresAuth: true, minimalRole: roles.user },
  },
];

export default usersRoutes;
