<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import { roles } from "@/util/enums";
import { parseDate, parseReviewStatus, parseTimestamp } from "@/util/parser";
import type { BookSubmisisonResponse, SuccessResponse } from "@/util/responses";
import { useSingularToast } from "@/util/useSingularToast";
import { useUserStore } from "@/util/userStore";
import { Icon } from "@iconify/vue";
import { isAxiosError } from "axios";
import { Button, Dialog, Divider, Textarea } from "primevue";
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const userStore = useUserStore();
const toast = useSingularToast();

const id = route.params.id as string;
const submission = ref<BookSubmisisonResponse | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

const currentAction = ref<"approve" | "reject" | null>(null);
const dialogVisible = ref<boolean>(false);
const dialogHeader = ref<string>("");
const reviewerComment = ref<string | null>(null);
const dialogMainButtonProps = ref<{ label: string; severity: string } | null>(null);

function showDialog(type: "approve" | "reject") {
  switch (type) {
    case "approve":
      currentAction.value = "approve";
      dialogHeader.value = "Approve this submission";
      dialogVisible.value = true;
      dialogMainButtonProps.value = { label: "Approve", severity: "success" };
      break;
    case "reject":
      currentAction.value = "reject";
      dialogHeader.value = "Reject this submission";
      dialogVisible.value = true;
      dialogMainButtonProps.value = { label: "Reject", severity: "danger" };
      break;
  }
}

function closeDialog() {
  reviewerComment.value = null;
  currentAction.value = null;
  dialogVisible.value = false;
}

async function judgeSubmission(decision: "approve" | "reject") {
/*   const destination =
    decision == "approve" ? ENDPOINTS.AUTHOR_SUBMISSION_APPROVE(id) : ENDPOINTS.AUTHOR_SUBMISSION_REJECT(id);
  const payload = { reviewerComment: reviewerComment.value };
  try {
    const { data: response } = await api.post<SuccessResponse<AuthorSubmisisonResponse>>(destination, payload);
    submission.value = response.data;
    toast({
      severity: "success",
      summary: "Review complete",
      group: "message",
      life: 5000,
    });
  } catch (error) {
    toast({
      severity: "error",
      summary: "An unknown error had occurred. Please try again later or refresh the page.",
      group: "message",
      life: 5000,
    });
  } finally {
    closeDialog();
  } */
}

onBeforeMount(async () => {
  try {
    const { data: response } = await api.get<SuccessResponse<BookSubmisisonResponse>>(
      ENDPOINTS.BOOK_SUBMISSION_PAGE(id),
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
    <!-- Book submission details -->
    <div
      v-if="submission != null"
      class="flex flex-col gap-2"
    >
      <RouterLink
        to="/submissions/books"
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
        <dt>Submitted at</dt>
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
        <dt>Reviewed by</dt>
        <dd>{{ submission.reviewerUsername }}</dd>
        <dt>Reviewed at</dt>
        <dd>{{ submission.reviewedAt && parseTimestamp(submission.reviewedAt) }}</dd>
        <dt>Review comments</dt>
        <dd>{{ submission.reviewerComment }}</dd>
      </dl>

      <div
        v-if="userStore.role >= roles.moderator && submission.reviewStatus == 'pending'"
        class="flex mx-auto mt-8 gap-4"
      >
        <Button
          label="Approve"
          severity="success"
          @click="showDialog('approve')"
          class="w-[100px]"
        />
        <Button
          label="Reject"
          severity="danger"
          @click="showDialog('reject')"
          class="w-[100px]"
        />
      </div>

      <div class="mt-8">
        <Divider />
        <h3 class="text-center">Last updated: {{ parseTimestamp(submission!.updatedAt) }}</h3>
      </div>
    </div>

    <Dialog
      modal
      position="center"
      :draggable="false"
      v-model:visible="dialogVisible"
      :header="dialogHeader"
      @hide="closeDialog"
      class="w-[500px] mx-4"
    >
      <div class="flex flex-col gap-2">
        <h2>Additional comments:</h2>
        <Textarea
          v-model="reviewerComment"
          rows="5"
        />
        <div class="flex gap-4 self-end mt-4">
          <Button
            label="Cancel"
            severity="secondary"
            class="py-1! shrink-0"
            @click="closeDialog"
          />
          <Button
            :label="dialogMainButtonProps?.label"
            :severity="dialogMainButtonProps?.severity"
            class="py-1! shrink-0"
            @click="currentAction && judgeSubmission(currentAction)"
          />
        </div>
      </div>
    </Dialog>
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
