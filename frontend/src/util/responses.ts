// Generic response data types
// Error response
export type ErrorResponse<PageFields> = {
    statusCode: number,
    message: string,
    errors: ErrorData<PageFields>[],
};

export type ErrorData<PageFields> = {
    field: PageFields | "general",
    message: string,
};

// Success response
export type SuccessResponse<ResponseType> = {
    statusCode: number,
    message: string,
    data: ResponseType,
};



// Endpoint specific response
// POST /api/register
export type RegisterResponse = {
    token: string,
    user: UserDataResponse,
}

// POST /api/login
export type LoginResponse = {
    token: string,
    user: UserDataResponse,
}

// POST /api/verify
export type VerifyResponse = {
    user: UserDataResponse,
}

// GET /api/users/{username}
// Todo: Expand to UserProfileResponse once author and data are added

export type UserDataResponse = {
    username: string,
    displayName: string | null,
    avatarUrl: string | null,
    bio: string | null,
    createdAt: string,
    userRole: "ROLE_ADMIN" | "ROLE_MODERATOR" | "ROLE_USER",
}
