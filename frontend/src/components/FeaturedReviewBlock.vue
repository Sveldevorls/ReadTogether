<script setup lang="ts">
import defaultAvatar from "@/assets/default_avatar.svg";
import defaultCover from "@/assets/default_cover.svg";
import type { FeaturedReviewResponse } from "@/util/responses";
import { Icon } from "@iconify/vue";
import { Rating } from "primevue";

const props = defineProps<{
  review: FeaturedReviewResponse;
}>();
</script>
<template>
  <div class="flex gap-4 p-2 flex-wrap">
    <img
      width="200px"
      class="mx-auto self-start"
      :src="props.review.book.coverUrl ?? defaultCover"
    />
    <div class="flex flex-col grow w-100">
      <span class="text-xl font-bold">{{ props.review.book.title }}</span>
      <span v-for="author in props.review.book.authors">
        {{ author.authorName }}
      </span>
      <div class="flex gap-2 mt-4 mb-4">
        <img
          width="40px"
          height="40px"
          class="shrink-0 rounded-full self-center"
          :src="props.review.reviewer.avatarUrl ?? defaultAvatar"
        />
        <div class="flex flex-col">
          <span>Review by {{ props.review.reviewer.displayName ?? props.review.reviewer.username }}</span>
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
