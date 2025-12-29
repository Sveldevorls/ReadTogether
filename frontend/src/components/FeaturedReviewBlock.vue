<script setup lang="ts">
import defaultAvatar from "@/assets/default_avatar.svg";
import defaultCover from "@/assets/default_cover.svg";
import type { FeaturedReviewResponse } from "@/util/responses";
import { URLS } from "@/util/urls";
import { Icon } from "@iconify/vue";
import { Rating } from "primevue";

const props = defineProps<{
  review: FeaturedReviewResponse;
}>();
</script>
<template>
  <div class="flex gap-4 p-2 flex-wrap">
    <RouterLink
      :to="URLS.BOOK_PAGE(props.review.book.id, props.review.book.slug)"
      class="h-fit mx-auto"
    >
      <img
        width="200px"
        class="mx-auto self-start"
        :src="props.review.book.coverUrl ?? defaultCover"
      />
    </RouterLink>
    <div class="flex flex-col grow w-100">
      <RouterLink
        :to="URLS.BOOK_PAGE(props.review.book.id, props.review.book.slug)"
        class="text-xl font-bold hover:underline w-fit"
      >
        {{ props.review.book.title }}
      </RouterLink>
      <RouterLink
        v-for="author in props.review.book.authors"
        :to="URLS.AUTHOR_PROFILE(author.id, author.slug)"
        class="hover:underline w-fit"
      >
        {{ author.authorName }}
      </RouterLink>

      <div class="flex gap-2 mt-4 mb-4">
        <RouterLink
          :to="URLS.USER_PROFILE(review.reviewer.username)"
          class="h-fit"
        >
          <img
            width="40px"
            height="40px"
            class="shrink-0 rounded-full self-center"
            :src="props.review.reviewer.avatarUrl ?? defaultAvatar"
          />
        </RouterLink>
        <div class="flex flex-col">
          <span>
            Review by
            <RouterLink
              :to="URLS.USER_PROFILE(review.reviewer.username)"
              class="w-fit hover:underline"
            >
              {{ props.review.reviewer.displayName ?? props.review.reviewer.username }}
            </RouterLink>
          </span>
          <Rating
            :stars="5"
            v-model="props.review.content.rating"
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
        </div>
      </div>
      <p class="h-fit mb-5">{{ props.review.content.comment }}</p>
    </div>
  </div>
</template>
