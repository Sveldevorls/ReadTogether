<script setup lang="ts">
import BookInfoBlock from "@/components/BookInfoBlock.vue";
import FeaturedReviewBlock from "@/components/FeaturedReviewBlock.vue";
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { HomepageResponse, SuccessResponse } from "@/util/responses";
import { isAxiosError } from "axios";
import { onBeforeMount, ref } from "vue";
import { Divider } from "primevue";

const data = ref<HomepageResponse | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

onBeforeMount(async () => {
  try {
    const { data: response } = await api.get<SuccessResponse<HomepageResponse>>(ENDPOINTS.HOMEPAGE);
    data.value = response.data;
  } catch (e) {
    if (isAxiosError(e) && e.status == 404) {
      errorStatus.value = "notfound";
    } else {
      errorStatus.value = "error";
    }
  } finally {
    isLoading.value = false;
    console.log(data.value);
  }
});
</script>

<template>
  <section class="w-[min(100%,80em)] px-4">
    <div class="px-2 py-4">
      <h1 class="text-4xl">Welcome to ReadTogether</h1>
      <p>Share your reading journey, discover your new favorite books, and explore what the world is reading.</p>
    </div>
    <div class="flex flex-col lg:grid lg:grid-cols-[2fr_1fr] lg:items-start gap-10">
      <div>
        <h2 class="px-2 text-2xl">Featured reviews</h2>
        <ul class="flex flex-col">
          <li
            v-for="(review, index) in data?.featuredReviews"
            :key="review.book.id"
          >
            <FeaturedReviewBlock :review="review" />
            <Divider v-if="index != data?.featuredReviews.length! - 1"/>
          </li>
        </ul>
      </div>
      <div class="flex flex-wrap lg:flex-col lg:gap-15">
        <div class="flex flex-col grow basis-90">
          <h2 class="px-2 mb-2 text-2xl">Popular this week</h2>
          <ul class="flex flex-col">
            <li
              v-for="(book, index) in data?.weeklyPopularBooks"
              :key="book.id"
            >
              <BookInfoBlock :book="book" />
              <Divider v-if="index != data?.weeklyPopularBooks.length! - 1"/>
            </li>
          </ul>
        </div>
        <div class="flex flex-col grow basis-90">
          <h2 class="px-2 mb-2 text-2xl">Newly added</h2>
          <ul class="flex flex-col">
            <li
              v-for="(book, index) in data?.latestBooks"
              :key="book.id"
            >
              <BookInfoBlock :book="book" />
              <Divider v-if="index != data?.latestBooks.length! - 1"/>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </section>
</template>
