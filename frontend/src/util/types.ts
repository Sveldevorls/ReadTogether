type ErrorResponse<T> = {
    statusCose: number,
    message: string,
    errors: ErrorData<T>[],
};

type ErrorData<T> = {
    field: T | "general",
    message: string,
};

type LoginPageFields = "username" | "email" | "password" | "passwordConfirm";

export type { ErrorResponse, ErrorData, LoginPageFields };