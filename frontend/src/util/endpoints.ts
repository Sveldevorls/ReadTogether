import type { ProfileUpdateFields } from "./fields";

export const ENDPOINTS = {
  REGISTER: "/api/register",
  LOGIN: "/api/login",
  LOGOUT: "/api/logout",
  VERIFY: "/api/verify",

  USER_PROFILE: (username: string) => `/api/users/${username}`,
  MY_PROFILE: "/api/me",
  MY_PROFILE_UPDATE: (field: ProfileUpdateFields) => `/api/me/${field}`,

  AUTHOR_DATA: (id: string) => `/api/authors/${id}`,
  AUTHOR_PROFILE: (id: string) => `/api/authors/${id}/profile`,
  AUTHOR_SEARCH_BY_NAME: (name: string) => `/api/authors?name=${name}`,

  BOOK_DETAILS: (id: string) => `/api/books/${id}/details`,
  BOOK_REVIEWS: (id: string) => `/api/books/${id}/reviews`,
  BOOK_SEARCH_BY_TITLE: (title: string) => `/api/books?title=${title}`,

  AUTHOR_SUBMISSIONS_SUBMIT: "/api/submissions/authors",
  AUTHOR_SUBMISSIONS_LIST: (limit: number, page: number, status: string) => `/api/submissions/authors?limit=${limit}&page=${page}&status=${status}`,
  AUTHOR_SUBMISSION_PAGE: (id: string) => `/api/submissions/authors/${id}`,
  AUTHOR_SUBMISSION_APPROVE: (id: string) => `/api/submissions/authors/${id}/approve`,
  AUTHOR_SUBMISSION_REJECT: (id: string) => `/api/submissions/authors/${id}/reject`,

  BOOK_SUBMISSIONS: "/api/submissions/books",
  BOOK_SUBMISSION_PAGE: (id: string) => `/api/submissions/books/${id}`,
  BOOK_SUBMISSION_APPROVE: (id: string) => `/api/submissions/books/${id}/approve`,
  BOOK_SUBMISSION_REJECT: (id: string) => `/api/submissions/books/${id}/reject`,

  REVIEW_SET_FEATURED: (id: number) => `/api/reviews/${id}/featured`,

  GENRES: "/api/genres",

  HOMEPAGE: "/api/homepage",
};
