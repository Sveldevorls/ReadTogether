type ErrorResponse<T> = {
    statusCose: number,
    message: string,
    errors: ErrorData<T>[],
};

type ErrorData<T> = {
    field: T | "general",
    message: string,
};

type RegisterPageFields = "username" | "email" | "password" | "passwordConfirm";
type LoginPageFields = "identifier" | "password";

export type { ErrorResponse, ErrorData, RegisterPageFields, LoginPageFields };