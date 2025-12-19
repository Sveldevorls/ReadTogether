import type { ProfileUpdateFields } from "./fields";

export const ENDPOINTS = {
  REGISTER: "/api/register",
  LOGIN: "/api/login",
  LOGOUT: "/api/logout",
  VERIFY: "/api/verify",

  USER_PROFILE: (username: string) => `/api/users/${username}`,
  MY_PROFILE: "/api/me",
  MY_PROFILE_UPDATE: (field: ProfileUpdateFields) => `/api/me/${field}`,

  AUTHOR_SUBMISSIONS: "/api/submissions/authors",
  AUTHOR_SUBMISSION_PAGE: (id: string) => `/api/submissions/authors/${id}`,
  AUTHOR_SUBMISSION_APPROVE: (id: string) => `/api/submissions/authors/${id}/approve`,
  AUTHOR_SUBMISSION_REJECT: (id: string) => `/api/submissions/authors/${id}/reject`,

  GENRES: "/api/genres",
}