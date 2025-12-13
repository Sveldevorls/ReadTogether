import { defineStore } from "pinia";
import { ref } from "vue";

import api from "./api";
import { ENDPOINTS } from "./endpoints";
import { roles } from "./enums";
import type { SuccessResponse, UserDataResponse, VerifyResponse } from "./responses";

export const useUserStore = defineStore("user", () => {
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
  };

  const clearUser = () => {
    username.value = null;
    displayName.value = null;
    avatarUrl.value = null;
    bio.value = null;
    createdAt.value = null;
    role.value = roles.guest;
  };

  let lastVerified: Date | null = null;
  const verify = async () => {
    // Refresh time: 30 minutes
    const now = new Date();
    if (!lastVerified || now.getTime() - lastVerified.getTime() > 1800000) {
      try {
        const { data: response } = await api.post<SuccessResponse<VerifyResponse>>(ENDPOINTS.VERIFY);
        setUser(response.data.user);
        lastVerified = new Date();
      } catch (error) {
        clearUser();
      }
    }
  };

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
  };

  return { username, displayName, avatarUrl, bio, createdAt, role, setUser, clearUser, verify };
});
