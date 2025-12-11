<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { SuccessResponse, UserProfileResponse } from "@/util/responses";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const username = route.params.username;
const fetchedProfile = ref<UserProfileResponse | null>(null);
const isLoading = ref<boolean>(true);
const isError = ref<boolean>(false);

onMounted(async () => {
  try {
    const { data: response } = await api.get<SuccessResponse<UserProfileResponse>>(ENDPOINTS.USERS + "/" + username);
    fetchedProfile.value = response.data;
  } catch (error) {
    isError.value = true;
  } finally {
    isLoading.value = false;
  }
});
</script>

<template>
  <h1 v-if="isLoading">Loading...</h1>
  <div v-else>
    <h1 v-if="isError">404 NOT FOUND</h1>
    <p v-else>{{ fetchedProfile }}</p>
  </div>
</template>

<style scoped></style>
