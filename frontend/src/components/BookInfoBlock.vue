<script setup lang="ts">
import { Skeleton } from "primevue";
</script>
<template>
  <div class="flex gap-2 p-2">
    <Skeleton
      width="120px"
      height="auto"
      class="aspect-2/3"
    ></Skeleton>
    <div class="flex flex-col grow gap-2">
      <Skeleton></Skeleton>
      <Skeleton></Skeleton>
    </div>
  </div>
</template>
