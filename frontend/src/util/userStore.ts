import { defineStore } from "pinia"
import { ref } from "vue"
import { roles } from "./enums";

export const useUserStore = defineStore('user', () => {
  const username = ref<string>("")
  const role = ref<roles>(roles.guest);
  const setUsername = (newUsername: string) => username.value = newUsername;
  const setRole = (newRole: roles) => role.value = newRole;
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
  return {username, role, setUsername, setRole, parseRole}
})