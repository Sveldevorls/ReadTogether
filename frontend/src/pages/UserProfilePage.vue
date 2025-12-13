<script setup lang="ts">
import defaultAvatar from "@/assets/default_avatar.svg";
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { SuccessResponse, UserDataResponse } from "@/util/responses";
import { isAxiosError } from "axios";
import { Divider } from "primevue";
import { computed, onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const username = route.params.username as string;
const profile = ref<UserDataResponse | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

const roleDisplay = computed(() => {
  if (!profile.value) return null;
  const roleMap = {
    ROLE_ADMIN: "Admin",
    ROLE_MODERATOR: "Moderator",
    ROLE_USER: "",
  };
  return roleMap[profile.value?.userRole];
});

onMounted(async () => {
  try {
    const { data: response } = await api.get<SuccessResponse<UserDataResponse>>(ENDPOINTS.USER_PROFILE(username));
    profile.value = response.data;
  } catch (e) {
    if (isAxiosError(e) && e.status == 404) {
      errorStatus.value = "notfound";
    } else {
      errorStatus.value = "error";
    }
  } finally {
    isLoading.value = false;
  }
});
</script>

<template>
  <section
    v-if="!isLoading"
    class="w-[min(100%,80em)] p-4"
  >
    <h1 v-if="errorStatus && errorStatus == 'notfound'">404 user not found</h1>
    <h1 v-else-if="errorStatus && errorStatus === 'error'">Unknown error</h1>
    <!-- User profile -->
    <div
      v-if="profile != null"
      class="flex flex-wrap gap-4"
    >
      <img
        :src="profile.avatarUrl ?? defaultAvatar"
        :alt="profile.username"
        height="200"
        width="200"
        class="rounded-full mx-auto"
      />
      <div class="flex flex-col grow">
        <!-- Admin/Moderator role -->
        <span
          v-if="roleDisplay"
          class="text-red-600"
        >
          {{ roleDisplay }}
        </span>
        <!-- Display name if display name exists, else username -->
        <span class="text-3xl font-black">{{ profile.displayName ?? profile.username }}</span>
        <!-- Show username if display name exists -->
        <span v-if="profile.displayName != null">{{ profile.username }}</span>
        <Divider v-if="profile.bio != null" />
        <p v-if="profile.bio != null">{{ profile.bio }}</p>
      </div>
    </div>
  </section>
</template>

<style scoped></style>
