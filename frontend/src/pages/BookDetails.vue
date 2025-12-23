<script setup lang="ts">
import defaultCover from "@/assets/default_cover.svg";
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { BookDetailsResponse, SuccessResponse } from "@/util/responses";
import { URLS } from "@/util/urls";
import { Icon } from "@iconify/vue";
import { isAxiosError } from "axios";
import { Button, Divider, Rating } from "primevue";
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const identifier = route.params.identifier as string;
const details = ref<BookDetailsResponse | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

const rating = ref<number>(0);

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

function submitRating() {
  console.log(rating.value)
}
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
      class="flex flex-wrap md:grid md:grid-cols-[200px_auto] mx-10 gap-10"
    >
      <div class="sticky">
        <img
          :src="details.book.bookData.coverUrl ?? defaultCover"
          :alt="details.book.bookData.title"
          class="mx-auto w-[200px] h-auto"
        />
        <Rating
          :stars="5"
          v-model="rating"
        >
          <template #officon>
            <Icon
              icon="tdesign:star-filled"
              width="24"
              height="24"
              class="text-neutral-400"
            />
          </template>
          <template #onicon>
            <Icon
              icon="tdesign:star-filled"
              width="24"
              height="24"
              class="text-yellow-400"
            />
          </template>
        </Rating>
        <Button
        label="Submit"
        @click="submitRating"
        />
      </div>
      <div class="flex flex-col grow gap-4">
        <div>
          <h1 class="text-4xl font-black">{{ details.book.bookData.title }}</h1>
          <div class="mt-1">
            <RouterLink
              v-for="(author, index) in details.authors"
              :to="URLS.AUTHOR_PROFILE(author.id, author.slug)"
              class="text-2xl hover:underline"
            >
              <h2>{{ author.authorName }}{{ index == details.authors.length - 1 ? "" : "," }}</h2>
            </RouterLink>
          </div>
        </div>
        <div>
          <div>{{ details.ratings.average }}</div>
          <div>{{ details.ratings.count }} ratings</div>
        </div>
        <p>{{ details.book.bookData.bookDescription }}</p>
        <div>
          <h3 class="font-black text-lg">Genres</h3>
          <ul class="flex gap-2">
            <li
              v-for="genre in details.genres"
              class="border-b"
            >
              <RouterLink :to="URLS.GENRE_PAGE(genre.slug)">
                {{ genre.genreName }}
              </RouterLink>
            </li>
          </ul>
        </div>
        <div>
          <h3 class="font-black text-lg">More details</h3>
          <dl class="grid grid-cols-[175px_1fr]">
            <dt v-if="details.book.bookData.isbn">ISBN</dt>
            <dd v-if="details.book.bookData.isbn">{{ details.book.bookData.isbn }}</dd>
            <dt v-if="details.book.bookData.publisherName">Publisher</dt>
            <dd v-if="details.book.bookData.publisherName">{{ details.book.bookData.publisherName }}</dd>
            <dt v-if="details.book.bookData.publishedDate">Publication date</dt>
            <dd v-if="details.book.bookData.publishedDate">{{ details.book.bookData.publishedDate }}</dd>
          </dl>
        </div>
        <Divider />
        <h2>Community Ratings</h2>
        <div v-if="details.ratings.count == 0">No one has rated this book yet. Be the first to do it</div>
        <div v-else>123</div>
      </div>
    </div>
  </section>
</template>
