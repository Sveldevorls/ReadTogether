<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { AuthorSubmisisonResponse, SuccessResponse } from "@/util/responses";
import { isAxiosError } from "axios";
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const id = route.params.id as string;
const submission = ref<AuthorSubmisisonResponse | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

onBeforeMount(async () => {
  try {
    const { data: response } = await api.get<SuccessResponse<AuthorSubmisisonResponse>>(
      ENDPOINTS.AUTHOR_SUBMISSION_PAGE(id),
    );
    submission.value = response.data;
  } catch (error) {
    if (isAxiosError(error) && error.status == 404) {
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
    <h1 v-if="errorStatus && errorStatus == 'notfound'">404 not found</h1>
    <h1 v-else-if="errorStatus && errorStatus === 'error'">Unknown error</h1>
    <!-- Author submission details -->
    {{ submission }}
  </section>
</template>
