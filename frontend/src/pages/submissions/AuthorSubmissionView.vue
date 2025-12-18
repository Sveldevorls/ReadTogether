<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import { parseDate, parseReviewStatus, parseTimestamp } from "@/util/parser";
import type { AuthorSubmisisonResponse, SuccessResponse } from "@/util/responses";
import { Icon } from "@iconify/vue";
import { isAxiosError } from "axios";
import { Divider } from "primevue";
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
    if (isAxiosError(error) && (error.status === 404 || error.status === 400)) {
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
    class="w-[min(100%,80em)] p-4 break-all"
  >
    <h1 v-if="errorStatus && errorStatus == 'notfound'">404 not found</h1>
    <h1 v-else-if="errorStatus && errorStatus === 'error'">Unknown error</h1>
    <!-- Author submission details -->
    <div
      v-if="submission != null"
      class="flex flex-col gap-2"
    >
      <RouterLink
        to="/submissions/authors"
        class="self-start flex hover:underline"
      >
        <Icon
          icon="material-symbols:arrow-left"
          width="24"
          height="24"
        />
        List of submissions
      </RouterLink>
      <h1 class="text-3xl">Submission #{{ submission.id }} by {{ submission.submitterUsername }}</h1>
      <h2 class="text-2xl mt-6">Summary</h2>
      <dl class="flex flex-col sm:grid sm:grid-cols-[200px_1fr] border border-gray-400">
        <dt>Submitted by</dt>
        <dd>
          <RouterLink
            :to="'/users/' + submission.submitterUsername"
            class="hover:underline"
            >{{ submission.submitterUsername }}</RouterLink
          >
        </dd>
        <dt>Submission time</dt>
        <dd>{{ parseTimestamp(submission.createdAt) }}</dd>
        <dt>Review status</dt>
        <dd
          class="font-black"
          :class="parseReviewStatus(submission.reviewStatus)?.style"
        >
          {{ parseReviewStatus(submission.reviewStatus)?.text }}
        </dd>
      </dl>

      <h2 class="text-2xl mt-6">Author details</h2>
      <dl class="flex flex-col sm:grid sm:grid-cols-[200px_1fr] border border-gray-400">
        <dt>Author name</dt>
        <dd>{{ submission.authorData.authorName }}</dd>
        <dt>Date of birth</dt>
        <dd>{{ submission.authorData.dateOfBirth && parseDate(submission.authorData.dateOfBirth) }}</dd>
        <dt>Date of death</dt>
        <dd>{{ submission.authorData.dateOfDeath && parseDate(submission.authorData.dateOfDeath) }}</dd>
        <dt>Author Image</dt>
        <dd>{{ submission.authorData.authorImageUrl }}</dd>
        <dt>Biography</dt>
        <dd>{{ submission.authorData.biography }}</dd>
      </dl>

      <h2 class="text-2xl mt-6">Additional information</h2>
      <dl class="flex flex-col sm:grid sm:grid-cols-[200px_1fr] border border-gray-400">
        <dt>Submitter comment</dt>
        <dd>{{ submission.submitterComment }}</dd>
      </dl>

      <h2 class="text-2xl mt-6">Review result</h2>
      <p v-if="submission.reviewStatus == 'pending'">This submission has not yeet been reviewed</p>
      <dl
        v-else
        class="flex flex-col sm:grid sm:grid-cols-[200px_1fr] border border-gray-400"
      >
        <dt>Reviewed at</dt>
        <dd>{{ submission.reviewedAt && parseTimestamp(submission.reviewedAt) }}</dd>
        <dt>Reviewed by</dt>
        <dd>{{ submission.reviewerUsername }}</dd>
        <dt>Review comments</dt>
        <dd>{{ submission.reviewerComment }}</dd>
      </dl>

      <div class="mt-8">
        <Divider />
        <h3 class="text-center">Last updated: {{ parseTimestamp(submission!.updatedAt) }}</h3>
      </div>
    </div>
  </section>
</template>

<style>
@reference "tailwindcss";

dt {
  @apply font-black p-2 max-sm:pb-0;

  &:not(:last-of-type) {
    @apply sm:border-b border-gray-400;
  }

  &:nth-of-type(even) {
    @apply bg-neutral-100;
  }
}

dd {
  @apply p-2;

  &:not(:last-of-type) {
    @apply border-b border-gray-400;
  }

  &:nth-last-of-type(even) {
    @apply bg-neutral-100;
  }
}
</style>
