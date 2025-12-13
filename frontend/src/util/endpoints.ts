import type { ProfileUpdateFields } from "./fields";

export const ENDPOINTS = {
  REGISTER: "/api/register",
  LOGIN: "/api/login",
  LOGOUT: "/api/logout",
  VERIFY: "/api/verify",
  USERS: "/api/users",
  MY_PROFILE: "/api/me",
  MY_PROFILE_UPDATE: (field: ProfileUpdateFields) => `/api/me/${field}`,
}