<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { AuthorSubmissionSummary, SubmissionListResponse, SuccessResponse } from "@/util/responses";
import { isAxiosError } from "axios";
import { Column, DataTable, type PageState, Paginator } from "primevue";
import { onBeforeMount, ref } from "vue";

const submissions = ref<SubmissionListResponse<AuthorSubmissionSummary> | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

const limit = ref<number>(10);
const currentPage = ref<number>(0);
const status = ref<string>("approved");

async function fetchSubmissions() {
  try {
    const { data: response } = await api.get<SuccessResponse<SubmissionListResponse<AuthorSubmissionSummary>>>(
      ENDPOINTS.AUTHOR_SUBMISSIONS_LIST(limit.value, currentPage.value, status.value),
    );
    console.log("Author submissions response:", response.data);
    submissions.value = response.data;
  } catch (error) {
    if (isAxiosError(error)) {
      if (error.response?.status == 404) {
        errorStatus.value = "notfound";
      } else {
        errorStatus.value = "error";
      }
    } else {
      errorStatus.value = "error";
    }
  } finally {
    isLoading.value = false;
  }
}

onBeforeMount(async () => {
  await fetchSubmissions();
});

function handlePageChange(e: PageState) {
  currentPage.value = e.page;
  fetchSubmissions();
}

function handleRowChange(e: number) {
  limit.value = e;
  currentPage.value = 0; // Reset to first page
  fetchSubmissions();
}

function getSubmitterName(submission: AuthorSubmissionSummary): string {
  return submission.submitter.displayName ?? submission.submitter.username;
}
</script>

<template>
  <section
    v-if="!isLoading"
    class="w-[min(100%,80em)] p-4 whitespace-pre-wrap"
  >
    <h1 v-if="errorStatus && errorStatus == 'notfound'">404 not found</h1>
    <h1 v-else-if="errorStatus && errorStatus === 'error'">Unknown error</h1>

    <!-- Header row for columns -->
    <div
      v-if="submissions != null"
      class="flex flex-col gap-2"
    >
      <DataTable
        :value="submissions.submissions ?? []"
        :rows="limit"
        stripedRows
      >
        <template #empty>
          <div class="p-4 text-gray-600">No submissions match this criteria.</div>
        </template>

        <Column
          field="id"
          header="Submission ID"
        />
        <Column
          field="type"
          header="Type"
        />
        <Column
          field="author.authorName"
          header="Author"
        />
        <Column
          :field="getSubmitterName"
          header="Submitter"
        />
        <Column
          field="createdAt"
          header="Created At"
        />
        <Column
          field="updatedAt"
          header="Updated At"
        />
      </DataTable>
      <Paginator
        :rows="limit"
        :totalRecords="submissions.totalCount"
        :rowsPerPageOptions="[10, 20]"
        @page="handlePageChange"
        @update:rows="handleRowChange"
      ></Paginator>
    </div>
  </section>
</template>
