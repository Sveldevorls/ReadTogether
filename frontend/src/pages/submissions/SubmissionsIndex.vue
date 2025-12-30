<script setup lang="ts">
import { Button } from "primevue";
import { useRouter } from "vue-router";

const router = useRouter();
</script>

<template>
  <section class="w-[min(100%,80em)] p-4">
    <div class="flex flex-col gap-4">
      <div>
        <h1 class="text-3xl font-black">Submissions</h1>
        <h2 class="text-lg">Select the page you'd like to visit</h2>
      </div>
      <div class="flex gap-6 justify-start">
        <RouterLink
          to="/submissions/authors/"
          class="flex-1 max-w-xs"
        >
          <div
            class="bg-slate-200 hover:bg-slate-300 p-6 rounded-lg transition-colors cursor-pointer h-full flex items-center justify-center"
          >
            <h2 class="text-xl font-semibold">Author submissions</h2>
          </div>
        </RouterLink>
        <RouterLink
          to="/submissions/books/"
          class="flex-1 max-w-xs"
        >
          <div
            class="bg-slate-200 hover:bg-slate-300 p-6 rounded-lg transition-colors cursor-pointer h-full flex items-center justify-center"
          >
            <h2 class="text-xl font-semibold">Book submissions</h2>
          </div>
        </RouterLink>
      </div>
    </div>
  </section>
</template>
