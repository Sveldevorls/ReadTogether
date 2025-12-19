// Error data
export type ErrorData<PageFields> = {
	field: PageFields | "general";
	message: string;
};

// Author core data
export type AuthorData = {
	authorName: string,
	dateOfBirth: string | null,
	dateOfDeath: string | null,
	authorImageUrl: string | null,
	biography: string | null,
}

// Basic genre data
export type Genre = {
	id: number,
	slug: string,
	genreName: string
}

// Barebones data to generate links to an author
export type AuthorLink = {
	id: number,
	slug: string,
	authorName: string
}