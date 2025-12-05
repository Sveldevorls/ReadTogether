<script setup lang="ts">
import { Skeleton } from "primevue";
</script>
<template>
  <div class="flex gap-2 p-2 flex-wrap">
    <Skeleton
      width="240px"
      height="auto"
      class="mx-auto aspect-2/3"
    ></Skeleton>
    <div class="flex flex-col grow w-100 gap-2">
      <Skeleton></Skeleton>
      <Skeleton></Skeleton>
      <div class="flex gap-2">
        <Skeleton
          width="40px"
          height="40px"
          class="shrink-0"
        ></Skeleton>
        <Skeleton></Skeleton>
      </div>
      <Skeleton></Skeleton>
      <Skeleton height="10lh"></Skeleton>
    </div>
  </div>
</template>
