<script setup lang="ts">
import defaultCover from "@/assets/default_cover.svg";
import RatingDistributionChart from "@/components/RatingDistributionChart.vue";
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import { roles } from "@/util/enums";
import type { BookDetailsResponse, RatingsSummary, ReviewSubmissionResponse, SuccessResponse } from "@/util/responses";
import { URLS } from "@/util/urls";
import { useSingularToast } from "@/util/useSingularToast";
import { useUserStore } from "@/util/userStore";
import { Icon } from "@iconify/vue";
import { isAxiosError } from "axios";
import { Button, Dialog, Divider, Rating, Textarea } from "primevue";
import { onBeforeMount, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
const toast = useSingularToast();

const identifier = route.params.identifier as string;
const id = identifier.match(/^\d+/g)?.[0] ?? null;
const details = ref<BookDetailsResponse | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

onBeforeMount(async () => {
  if (id === null) {
    errorStatus.value = "notfound";
    isLoading.value = false;
    return;
  }

  try {
    const { data: response } = await api.get<SuccessResponse<BookDetailsResponse>>(ENDPOINTS.BOOK_DETAILS(id));
    details.value = response.data;
  } catch (e) {
    if (isAxiosError(e) && e.status === 404) {
      errorStatus.value = "notfound";
    } else {
      errorStatus.value = "error";
    }
  } finally {
    isLoading.value = false;
  }
});

const dialogVisible = ref<boolean>(false);
const rating = ref<number>(0);
const reviewComment = ref<string | null>(null);

function toggleDialog() {
  dialogVisible.value = !dialogVisible.value;
}

async function submitReview() {
  if (userStore.role === roles.guest) {
    router.push("/login");
    return;
  }

  if (!rating.value || !id) return;

  const payload = {
    rating: rating.value,
    comment: reviewComment.value,
  };
  try {
    const { data: response } = await api.post<SuccessResponse<ReviewSubmissionResponse>>(
      ENDPOINTS.BOOK_REVIEW(id),
      payload,
    );
    if (details.value) {
      details.value.ratings = response.data.ratings;
      details.value.userReview = response.data.userReview;
    }
    toast({
      severity: "success",
      summary: "Review complete",
      group: "message",
      life: 5000,
    });
  } catch (error) {
    toast({
      severity: "error",
      summary: "An unknown error happened",
      group: "message",
      life: 5000,
    });
  } finally {
    toggleDialog();
  }
}
</script>

<template>
  <section
    v-if="!isLoading"
    class="w-[min(100%,80em)] p-4"
  >
    <h1 v-if="errorStatus && errorStatus === 'notfound'">404 author not found</h1>
    <h1 v-else-if="errorStatus && errorStatus === 'error'">Unknown error</h1>
    <!-- Book details -->
    <div
      v-if="details != null"
      class="flex flex-wrap md:grid md:grid-cols-[200px_auto] mx-4 gap-10"
    >
      <div class="flex flex-col gap-4 mx-auto">
        <img
          :src="details.book.bookData.coverUrl ?? defaultCover"
          :alt="details.book.bookData.title"
          class="mx-auto w-[200px] h-auto"
        />
        <div class="flex flex-col items-center gap-2">
          <Rating
            :stars="5"
            v-model="rating"
            v-if="userStore.role != roles.guest"
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
          <RouterLink
            v-if="userStore.role === roles.guest"
            to="/login"
          >
            <Button
              label="Log in to rate this book"
              severity="secondary"
            />
          </RouterLink>
          <Button
            v-else
            label="Rate this book"
            @click="rating >= 1 && toggleDialog()"
          />
        </div>
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
        <h2>{{ details.userReview }}</h2>
        <div v-if="details.ratings.count == 0">No one has reviewed this book yet. Be the first to do it</div>
        <div v-else>
          <RatingDistributionChart :ratings="details.ratings" />
        </div>
      </div>
    </div>

    <Dialog
      modal
      position="center"
      header="Submit review"
      :draggable="false"
      v-model:visible="dialogVisible"
      class="w-[500px] mx-4"
    >
      <div class="flex flex-col gap-2">
        <div class="flex items-center gap-2">
          <Rating
            v-model="rating"
            readonly
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
          <span>{{ rating }} / 5</span>
        </div>
        <h2>Additional comments:</h2>
        <Textarea
          v-model="reviewComment"
          rows="5"
          focusOnShow="false"
        />
        <div class="flex gap-4 self-end mt-4">
          <Button
            label="Cancel"
            severity="secondary"
            class="py-1! shrink-0"
            @click="toggleDialog"
          />
          <Button
            label="Submit"
            severity="success"
            class="py-1! shrink-0"
            @click="submitReview"
          />
        </div>
      </div>
    </Dialog>
  </section>
</template>
