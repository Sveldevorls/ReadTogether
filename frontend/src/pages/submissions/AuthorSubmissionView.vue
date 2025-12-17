<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import { parseDate, parseTimestamp } from "@/util/parser";
import type { AuthorSubmisisonResponse, SuccessResponse } from "@/util/responses";
import { isAxiosError } from "axios";
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const id = route.params.id as string;
const submission = ref<AuthorSubmisisonResponse | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

function parseReviewStatus(status: "approved" | "pending" | "rejected") {
  if (!submission.value) return null;

  switch (submission.value.reviewStatus) {
    case "approved":
      return { style: "text-green-600", text: "Approved" };
    case "pending":
      return { style: "text-amber-400", text: "Pending" };
    case "rejected":
      return { style: "text-red-600", text: "Rejected" };
  }
}

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
    class="w-[min(100%,80em)] p-4"
  >
    <h1 v-if="errorStatus && errorStatus == 'notfound'">404 not found</h1>
    <h1 v-else-if="errorStatus && errorStatus === 'error'">Unknown error</h1>
    <!-- Author submission details -->
    <div v-if="submission != null">
      <RouterLink to="/submissions/authors">Back to submissions list</RouterLink>
      <h1 class="text-3xl">Submission #{{ submission.id }} by {{ submission.submitterUsername }}</h1>
      <h2 class="text-2xl">Summary</h2>
      <div class="grid grid-cols-[200px_1fr]">
        <span>Submitted by</span>
        <RouterLink :to="'/users/' + submission.submitterUsername">{{ submission.submitterUsername }}</RouterLink>

        <span>Submission time</span>
        <span>{{ parseTimestamp(submission.createdAt) }}</span>

        <span>Review status</span>
        <span
          class="font-black"
          :class="parseReviewStatus(submission.reviewStatus)?.style"
        >
          {{ parseReviewStatus(submission.reviewStatus)?.text }}
        </span>
      </div>

      <h2 class="text-2xl">Author details</h2>
      <div class="grid grid-cols-[200px_1fr]">
        <span>Author name</span>
        <span>{{ submission.authorData.authorName }}</span>

        <span>Date of birth</span>
        <span>{{ submission.authorData.dateOfBirth && parseDate(submission.authorData.dateOfBirth) }}</span>

        <span>Date of death</span>
        <span>{{ submission.authorData.dateOfDeath && parseDate(submission.authorData.dateOfDeath) }}</span>

        <span>Author Image</span>
        <span>{{ submission.authorData.authorImageUrl }}</span>

        <span>Biography</span>
        <span>{{ submission.authorData.biography }}</span>
      </div>

      <h2 class="text-2xl">Additional information</h2>
      <div class="grid grid-cols-[200px_1fr]">
        <span>Submitter comment</span>
        <p>{{ submission.submitterComment }}</p>
      </div>

      <h2 class="text-2xl">Review result</h2>
      <p v-if="submission.reviewStatus == 'pending'">This submission is still yet to be reviewed</p>
      <div
        v-else
        class="grid grid-cols-[200px_1fr]"
      >
        <span>Reviewed at</span>
        <span>{{ submission.reviewedAt && parseTimestamp(submission.reviewedAt) }}</span>

        <span>Reviewed by</span>
        <span>{{ submission.reviewerUsername }}</span>

        <span>Review comments</span>
        <span>{{ submission.reviewerComment }}</span>
      </div>

      <h3>Last updated: {{ parseTimestamp(submission!.updatedAt) }}</h3>
    </div>
  </section>
</template>
