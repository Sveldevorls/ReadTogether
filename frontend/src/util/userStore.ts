import { defineStore } from "pinia"
import { ref } from "vue"
import { roles } from "./enums";
import type { UserDataResponse } from "./responses";

export const useUserStore = defineStore('user', () => {
  const username = ref<string | null>(null);
  const displayName = ref<string | null>(null);
  const avatarUrl = ref<string | null>(null);
  const bio = ref<string | null>(null);
  const createdAt = ref<string | null>(null);
  const role = ref<roles>(roles.guest);

  const setUser = (user: UserDataResponse) => {
    username.value = user.username;
    displayName.value = user.displayName;
    avatarUrl.value = user.avatarUrl;
    bio.value = user.bio;
    createdAt.value = user.createdAt;
    role.value = parseRole(user.userRole);
  }

  const clearUser = () => {
    username.value = null;
    displayName.value = null;
    avatarUrl.value = null;
    bio.value = null;
    createdAt.value = null;
    role.value = roles.guest;
  }

  const parseRole = (role: string): roles => {
    switch (role) {
      case "ROLE_ADMIN":
        return roles.admin;
      case "ROLE_MODERATOR":
        return roles.moderator;
      case "ROLE_USER":
        return roles.user;
      default:
        return roles.guest;
    }
  }
  return { username, displayName, avatarUrl, bio, createdAt, role, setUser, clearUser }
})