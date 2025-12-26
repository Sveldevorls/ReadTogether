<script setup lang="ts">
import type { RatingsSummary } from "@/util/responses";

const props = defineProps<{ ratings: RatingsSummary }>();
console.log(props.ratings);

function getBarWidth(votes: number): string {
  return `${(votes / props.ratings.count) * 100}%`;
}
</script>

<template>
  <div
    v-for="[k, v] in Object.entries(ratings.distributions).reverse()"
    class="grid grid-cols-[25px_3fr_1fr] gap-4 gap-y-8 items-center"
  >
    <div class="contents">
      <span>{{ k }}</span>
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
