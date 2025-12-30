export const URLS = {
    USER_PROFILE: (username: string) => `/users/${username}`,

    AUTHOR_PROFILE: (id: number, slug: string) => `/authors/${id}-${slug}`,

    BOOK_PAGE: (id: number, slug: string) => `/books/${id}-${slug}`,

    GENRE_PAGE: (slug: string) => `/genres/${slug}`,

    AUTHOR_SUBMISSION_PAGE: (id: number) => `/submissions/authors/${id}`,

    BOOK_SUBMISSION_PAGE: (id: number) => `/submissions/books/${id}`,

    NEW_AUTHOR_SUBMISSION: "/submissions/authors/new",
    NEW_BOOK_SUBMISSION: "/submissions/books/new"
}