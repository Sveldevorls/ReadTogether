<script setup lang="ts">
import BookInfoBlock from "@/components/BookInfoBlock.vue";
import FeaturedReviewBlock from "@/components/FeaturedReviewBlock.vue";
</script>

<template>
  <section class="w-[min(100%,80em)] px-4">
    <div class="px-2 py-4">
      <h1 class="text-4xl">Welcome to ReadTogether</h1>
      <p>
        Share your reading journey, discover your new favorite books, and
        explore what the world is reading.
      </p>
    </div>
    <div
      class="flex flex-col lg:grid lg:grid-cols-[2fr_1fr] lg:items-start gap-2"
    >
      <div>
        <h2 class="px-2 text-2xl">Featured reviews</h2>
        <ul>
          <li
            v-for="index in 5"
            :key="index"
          >
            <FeaturedReviewBlock />
          </li>
        </ul>
      </div>
      <div class="flex flex-wrap lg:flex-col lg:gap-4">
        <div class="flex flex-col grow basis-90">
          <h2 class="px-2 text-2xl">Popular this week</h2>
          <ul>
            <li
              v-for="index in 5"
              :key="index"
            >
              <BookInfoBlock />
            </li>
          </ul>
        </div>
        <div class="flex flex-col grow basis-90">
          <h2 class="px-2 text-2xl">Newly added</h2>
          <ul>
            <li
              v-for="index in 5"
              :key="index"
            >
              <BookInfoBlock />
            </li>
          </ul>
        </div>
      </div>
    </div>
  </section>
</template>
