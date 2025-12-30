<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import { parseTimestamp } from "@/util/parser";
import type {
  AuthorSubmissionSummary,
  BookSubmissionSummary,
  SubmissionListResponse,
  SuccessResponse,
} from "@/util/responses";
import { URLS } from "@/util/urls";
import { isAxiosError } from "axios";
import { Column, DataTable, type DataTableRowClickEvent, type PageState, Paginator } from "primevue";
import Tab from "primevue/tab";
import TabPanel from "primevue/tabpanel";
import Tabs from "primevue/tabs";
import { onBeforeMount, ref, watch } from "vue";
import { useRouter } from "vue-router";
import Button from "primevue/button";

const submissions = ref<SubmissionListResponse<BookSubmissionSummary> | null>(null);
const isLoading = ref<boolean>(true);
const errorStatus = ref<"error" | "notfound" | null>(null);

const limit = ref<number>(10);
const currentPage = ref<number>(0);
const status = ref<string>("pending");

const router = useRouter();

const options = ["pending", "approved", "rejected"];

async function fetchSubmissions() {
  try {
    const { data: response } = await api.get<SuccessResponse<SubmissionListResponse<BookSubmissionSummary>>>(
      ENDPOINTS.BOOK_SUBMISSIONS_LIST(limit.value, currentPage.value, status.value),
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

watch(status, async () => {
  currentPage.value = 0; // Reset to first page when status changes
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

function getSubmitterName(submission: BookSubmissionSummary): string {
  return submission.submitter.displayName ?? submission.submitter.username;
}

function handleRowClick(e: DataTableRowClickEvent) {
  router.push(URLS.BOOK_SUBMISSION_PAGE(e.data.id));
}
</script>

<template>
  <section
    v-if="!isLoading"
    class="w-[min(100%,80em)] p-4 whitespace-pre-wrap"
  >
    <div class="flex items-center justify-between px-4">
      <h1 class="text-2xl font-black">Book submissions</h1>
      <RouterLink
        :to="URLS.NEW_BOOK_SUBMISSION"
        class="self-end"
      >
        <Button label="Add a new book" />
      </RouterLink>
    </div>
    <h1 v-if="errorStatus && errorStatus == 'notfound'">404 not found</h1>
    <h1 v-else-if="errorStatus && errorStatus === 'error'">Unknown error</h1>

    <!-- Tabs for filtering by status -->
    <div
      v-if="submissions != null"
      class="flex flex-col gap-2"
    >
      <Tabs v-model:value="status">
        <TabList>
          <Tab
            v-for="option in options"
            :key="option"
            :value="option"
            >{{ option.charAt(0).toUpperCase() + option.slice(1) }}</Tab
          >
        </TabList>
        <TabPanel
          v-for="statusOption in options"
          :key="statusOption"
          :header="statusOption.charAt(0).toUpperCase() + statusOption.slice(1)"
          :value="statusOption"
        >
          <DataTable
            :value="submissions.submissions ?? []"
            :rows="limit"
            stripedRows
            @row-click="handleRowClick"
            :pt="{
              bodyRow: {
                class: ['hover:bg-slate-200! hover:cursor-pointer'],
              },
            }"
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
              field="book.title"
              header="Title"
            />
            <Column
              :field="getSubmitterName"
              header="Submitter"
            />
            <Column
              field="createdAt"
              header="Created"
            >
              <template #body="slotProps">
                {{ parseTimestamp(slotProps.data.createdAt) }}
              </template>
            </Column>
            <Column
              field="updatedAt"
              header="Last updated"
            >
              <template #body="slotProps">
                {{ parseTimestamp(slotProps.data.updatedAt) }}
              </template>
            </Column>
          </DataTable>
        </TabPanel>
      </Tabs>
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
