<script setup lang="ts">
import defaultCover from "@/assets/default_cover.svg";
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { BookDetailsResponse, SuccessResponse } from "@/util/responses";
import { URLS } from "@/util/urls";
import { isAxiosError } from "axios";
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const identifier = route.params.identifier as string;
const details = ref<BookDetailsResponse | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

onBeforeMount(async () => {
  const id = identifier.match(/^\d+/g);
  if (id == null) {
    errorStatus.value = "notfound";
    isLoading.value = false;
    return;
  }

  try {
    const { data: response } = await api.get<SuccessResponse<BookDetailsResponse>>(ENDPOINTS.BOOK_DETAILS(id[0]));
    details.value = response.data;
  } catch (e) {
    if (isAxiosError(e) && e.status == 404) {
      errorStatus.value = "notfound";
    } else {
      errorStatus.value = "error";
    }
  } finally {
    isLoading.value = false;
  }
});
</script>

<template>
  <section
    v-if="!isLoading"
    class="w-[min(100%,80em)] p-4"
  >
    <h1 v-if="errorStatus && errorStatus == 'notfound'">404 author not found</h1>
    <h1 v-else-if="errorStatus && errorStatus === 'error'">Unknown error</h1>
    <!-- Book details -->
    <div
      v-if="details != null"
      class="flex flex-wrap md:grid md:grid-cols-[250px_auto] gap-4"
    >
      <img
        :src="details.book.bookData.coverUrl ?? defaultCover"
        :alt="details.book.bookData.title"
        class="mx-auto w-[200px] h-auto"
      />
      <div class="flex flex-col grow gap-4">
        <div>
          <span class="text-4xl font-black">{{ details.book.bookData.title }}</span>
          <div class="mt-1">
            <RouterLink
              v-for="(author, index) in details.authors"
              :to="URLS.AUTHOR_PROFILE(author.id, author.slug)"
              class="text-2xl hover:underline"
            >
              {{ author.authorName }}{{ index == details.authors.length - 1 ? "" : "," }}
            </RouterLink>
          </div>
        </div>
        <p class="break-all">{{ details.book.bookData.bookDescription }}</p>
        <div>
          <span class="font-black text-lg">Genres</span>
          <ul class="flex gap-2">
            <li v-for="(genre) in details.genres" class="border-b">
              <RouterLink :to="URLS.GENRE_PAGE(genre.slug)">
                {{ genre.genreName }}
              </RouterLink>
            </li>
          </ul>
        </div>
        <div>
          <span class="font-black text-lg">More details</span>
          <dl class="grid grid-cols-[175px_1fr]">
            <dt v-if="details.book.bookData.isbn">ISBN</dt>
            <dd v-if="details.book.bookData.isbn">{{ details.book.bookData.isbn }}</dd>
            <dt v-if="details.book.bookData.publisherName">Publisher</dt>
            <dd v-if="details.book.bookData.publisherName">{{ details.book.bookData.publisherName }}</dd>
            <dt v-if="details.book.bookData.publishedDate">Publication date</dt>
            <dd v-if="details.book.bookData.publishedDate">{{ details.book.bookData.publishedDate }}</dd>
          </dl>
        </div>
      </div>
    </div>
  </section>
</template>
