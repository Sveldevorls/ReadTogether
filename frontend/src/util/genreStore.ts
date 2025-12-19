import { defineStore } from "pinia";
import { ref } from "vue";
import api from "./api";
import { ENDPOINTS } from "./endpoints";
import type { SuccessResponse } from "./responses";
import type { Genre } from "./types";

export const useGenreStore = defineStore("genres", () => {
	const genres = ref<Genre[] | null>(null);

	const fetchGenres = async () => {
		try {
			const { data: response } = await api.get<SuccessResponse<Genre[]>>(ENDPOINTS.GENRES);
			genres.value = response.data;
		} catch (error) {

		};
	}

	return { fetchGenres, genres };
});