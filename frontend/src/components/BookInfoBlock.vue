<script setup lang="ts">
import defaultCover from "@/assets/default_cover.svg";
import type { BookSummary } from "@/util/responses";
import { URLS } from "@/util/urls";

const props = defineProps<{ book: BookSummary }>();
</script>
<template>
  <div class="grid grid-cols-[100px_1fr] gap-2 p-2">
    <RouterLink :to="URLS.BOOK_PAGE(book.id, book.slug)">
      <img
        :src="props.book.coverUrl ?? defaultCover"
      />
    </RouterLink>
    <div class="flex flex-col grow">
      <RouterLink
        :to="URLS.BOOK_PAGE(book.id, book.slug)"
        class="text-xl font-bold w-fit hover:underline"
      >
        {{ props.book.title }}
      </RouterLink>
      <RouterLink
        v-for="author in props.book.authors"
        :key="author.id"
        :to="URLS.AUTHOR_PROFILE(author.id, author.slug)"
        class="w-fit hover:underline"
      >
        {{ author.authorName }}
      </RouterLink>
    </div>
  </div>
</template>
