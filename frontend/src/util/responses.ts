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
}

// POST /api/login
export type LoginResponse = {
    token: string,
}

// POST /api/verify
export type VerifyResponse = {
    username: string,
    role: string,
}
