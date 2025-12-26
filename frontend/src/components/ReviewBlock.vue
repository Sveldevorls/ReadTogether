<script setup lang="ts">
import defautlAvatar from "@/assets/default_avatar.svg";
import type { ReviewResponse } from "@/util/responses";
import { URLS } from "@/util/urls";
import { Icon } from "@iconify/vue";
import { Avatar, Rating } from "primevue";
import { RouterLink } from "vue-router";

const props = defineProps<{ review: ReviewResponse }>();
</script>

<template>
  <div class="flex flex-col md:grid md:grid-cols-[100px_3fr]">
    <div class="flex md:flex-col gap-1">
      <RouterLink
        :to="URLS.USER_PROFILE(review.reviewer.username)"
        class="w-fit h-fit block"
      >
        <Avatar
          :image="review.reviewer.avatarUrl ?? defautlAvatar"
          size="xlarge"
          shape="circle"
        />
      </RouterLink>
      <RouterLink
        :to="URLS.USER_PROFILE(review.reviewer.username)"
        class="hover:underline"
      >
        <span>{{ review.reviewer.displayName ?? review.reviewer.username }}</span>
      </RouterLink>
    </div>
    <div>
      <Rating
        :stars="5"
        v-model="review.content.rating"
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
</template>
