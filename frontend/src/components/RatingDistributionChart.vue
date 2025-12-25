<script setup lang="ts">
import type { RatingsSummary } from "@/util/responses";

const props = defineProps<{ ratings: RatingsSummary }>();
console.log(props.ratings);

function getBarWidth(votes: number): string {
  return `${(votes / props.ratings.count) * 100}%`;
}
</script>

<template>
  <h2>{{ ratings.average.toFixed(2) }}</h2>
  <div
    v-for="[k, v] in Object.entries(ratings.distributions).reverse()"
    class="grid grid-cols-[50px_3fr_1fr] gap-4 items-center"
  >
    <div class="contents">
      <span>{{ k }} stars</span>
      <div class="relative">
        <div class="bg-gray-300 w-full h-3 rounded-full"></div>
        <div
          class="bg-orange-300 h-3 rounded-full absolute top-0"
          :style="{ width: getBarWidth(v) }"
        ></div>
      </div>
      <span>{{ v }}</span>
    </div>
  </div>
</template>
