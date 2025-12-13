// Page specific fields, used in error response
// /register
export type RegisterPageFields = "username" | "email" | "password" | "passwordConfirm";

// /login
export type LoginPageFields = "identifier" | "password";

// /settings profile section
export type ProfileUpdateFields = "displayName" | "bio";