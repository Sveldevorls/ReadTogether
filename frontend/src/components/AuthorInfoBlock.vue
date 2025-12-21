<script setup lang="ts">
import type { AuthorData } from "@/util/types";
import defaultAvatar from "@/assets/default_avatar.svg";

const props = defineProps<{ id: number; authorData: AuthorData }>();

function getBirthYearDisplay(date: string | null): string {
  if (!date) return "";
  const dob = new Date(date);
  return `Born ${dob.getFullYear()}`;
}
</script>

<template>
  <div class="flex gap-2 h-[50px] w-[250px] shrink-0">
    <img
      :src="authorData.authorImageUrl ?? defaultAvatar"
      class="h-[50px] aspect-square object-cover"
    />
    <div class="flex flex-col w-full relative">
      <span class="font-bold truncate">{{ authorData.authorName }}</span>
      <span class="text-sm text-gray-500">{{ getBirthYearDisplay(authorData.dateOfBirth) }}</span>
      <span class="absolute right-0 bottom-0 text-xs text-gray-500">ID: {{ id }}</span>
    </div>
  </div>
</template>
