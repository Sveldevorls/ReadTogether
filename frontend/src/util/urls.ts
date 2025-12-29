export const URLS = {
    USER_PROFILE: (username: string) => `/users/${username}`,

    AUTHOR_PROFILE: (id: number, slug: string) => `/authors/${id}-${slug}`,

    BOOK_PAGE: (id: number, slug: string) => `/books/${id}-${slug}`,

    GENRE_PAGE: (slug: string) => `/genres/${slug}`
}