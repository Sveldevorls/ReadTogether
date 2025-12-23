export const URLS = {
    AUTHOR_PROFILE: (id: number, slug: string) => `/authors/${id}-${slug}`,

    GENRE_PAGE: (slug: string) => `/genres/${slug}`
}