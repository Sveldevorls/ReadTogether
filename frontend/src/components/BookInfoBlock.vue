<script setup lang="ts">
import defaultCover from "@/assets/default_cover.svg";
import type { BookSummary } from "@/util/responses";

const props = defineProps<{ book: BookSummary }>();
</script>
<template>
  <div class="flex gap-2 p-2">
    <img
      width="100px"
      :src="props.book.coverUrl ?? defaultCover"
      class="self-start"
    />
    <div class="flex flex-col grow">
      <span class="text-xl font-bold">{{ props.book.title }}</span>
      <span
        v-for="author in props.book.authors"
        :key="author.authorName"
      >
        {{ author.authorName }}
      </span>
    </div>
  </div>
</template>
