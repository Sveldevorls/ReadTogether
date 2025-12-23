import type { ProfileUpdateFields } from "./fields";
import type { AuthorData, AuthorSummary, BookData, ErrorData, GenreSummary } from "./types";

// Generic response
// Error response
export type ErrorResponse<PageFields> = {
  statusCode: number;
  message: string;
  errors: ErrorData<PageFields>[];
};

// Success response
export type SuccessResponse<ResponseType> = {
  statusCode: number;
  message: string;
  data: ResponseType;
};

// Endpoint specific response
// POST /api/register
export type RegisterResponse = {
  token: string;
  user: UserDataResponse;
};

// POST /api/login
export type LoginResponse = {
  token: string;
  user: UserDataResponse;
};

// POST /api/verify
export type VerifyResponse = {
  user: UserDataResponse;
};

// GET /api/users/{username}
// Todo: Expand to UserProfileResponse once author and data are added
export type UserDataResponse = {
  username: string;
  displayName: string | null;
  avatarUrl: string | null;
  bio: string | null;
  createdAt: string;
  userRole: "ROLE_ADMIN" | "ROLE_MODERATOR" | "ROLE_USER";
};

// PATCH /api/me/{field}
export type ProfileUpdateResponse<fieldName extends ProfileUpdateFields> = Record<fieldName, string>;

// POST /api/submissions/authors
export type NewAuthorSubmissionResponse = {
  id: number;
};

// POST /api/submissions/authors
export type NewBookSubmissionResponse = {
  id: number;
};

// GET /api/submissions/authors/{id}
export type AuthorSubmisisonResponse = {
  // Meta
  id: number;
  createdAt: string;
  updatedAt: string;

  // Submission data
  previousSubmissionId: number | null;
  authorId: number;
  submitterUsername: string;
  submitterComment: string | null;
  reviewerUsername: string | null;
  reviewerComment: string | null;
  reviewedAt: string | null;
  reviewStatus: "approved" | "pending" | "rejected";

  // Author data
  authorData: AuthorData;
};

// GET /api/submissions/books/{id}
export type BookSubmisisonResponse = {
  // Meta
  id: number;
  createdAt: string;
  updatedAt: string;

  // Submission data
  previousSubmissionId: number | null;
  bookId: number;
  submitterUsername: string;
  submitterComment: string | null;
  reviewerUsername: string | null;
  reviewerComment: string | null;
  reviewedAt: string | null;
  reviewStatus: "approved" | "pending" | "rejected";

  authors: { id: number; slug: string; authorName: string }[];
  genres: { id: number; slug: string; genreName: string }[];
  bookData: BookData;
};

// GET /api/authors/{slug}
export type AuthorProfileResponse = {
  author: AuthorResponse;
};

export type AuthorResponse = {
  // Meta
  id: number;
  slug: string;
  createdAt: string;
  updatedAt: string;
  reviewStatus: "approved" | "pending" | "rejected";

  // Author data
  authorData: AuthorData;
};

// GET /api/authors/{slug}
export type BookDetailsResponse = {
  book: BookResponse;
  authors: AuthorSummary[];
  genres: GenreSummary[];
  ratings: BookRatingSummary
};

export type BookResponse = {
  // Meta
  id: number;
  slug: string;
  createdAt: string;
  updatedAt: string;
  reviewStatus: "approved" | "pending" | "rejected";

  // Book data
  bookData: BookData;
};

export type BookRatingSummary = {
  average: number,
  count: number,
  distributions: Record<1 | 2 | 3 | 4 | 5, number>;
}
