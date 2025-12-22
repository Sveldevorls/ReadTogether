<script setup lang="ts">
import defaultAvatar from "@/assets/default_avatar.svg";
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { AuthorProfileResponse, SuccessResponse } from "@/util/responses";
import { isAxiosError } from "axios";
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const identifier = route.params.identifier as string;
const profile = ref<AuthorProfileResponse | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

onBeforeMount(async () => {
  const id = identifier.match(/^\d+/g);
  if (id == null) {
    errorStatus.value = "notfound";
    isLoading.value = false;
    return;
  }

  try {
    const { data: response } = await api.get<SuccessResponse<AuthorProfileResponse>>(ENDPOINTS.AUTHOR_PROFILE(id[0]));
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
    <h1 v-if="errorStatus && errorStatus == 'notfound'">404 author not found</h1>
    <h1 v-else-if="errorStatus && errorStatus === 'error'">Unknown error</h1>
    <!-- Author profile -->
    <div
      v-if="profile != null"
      class="flex flex-wrap md:grid md:grid-cols-[1fr_3fr] gap-4"
    >
      <img
        :src="profile.author.authorData.authorImageUrl ?? defaultAvatar"
        :alt="profile.author.authorData.authorName"
        class="mx-auto h-50 w-50 object-cover"
      />
      <div class="flex flex-col grow">
        <span class="text-3xl font-black">{{ profile.author.authorData.authorName }}</span>
        <dl class="grid grid-cols-[1fr_3fr] w-50">
          <dt v-if="profile.author.authorData.dateOfBirth">Born</dt>
          <dd v-if="profile.author.authorData.dateOfBirth">{{ profile.author.authorData.dateOfBirth }}</dd>
          <dt v-if="profile.author.authorData.dateOfDeath">Died</dt>
          <dd v-if="profile.author.authorData.dateOfDeath">{{ profile.author.authorData.dateOfDeath }}</dd>
        </dl>
        <p>{{ profile.author.authorData.biography }}</p>
      </div>
    </div>
  </section>
</template>
