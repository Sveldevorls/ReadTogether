<script setup lang="ts">
// Todo: fix error message display lag for authors and genres
import AuthorInfoBlock from "@/components/AuthorInfoBlock.vue";
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { BookSubmissionFields } from "@/util/fields";
import { useGenreStore } from "@/util/genreStore";
import type { AuthorResponse, ErrorResponse, NewBookSubmissionResponse, SuccessResponse } from "@/util/responses";
import type { Genre } from "@/util/types";
import { useSingularToast } from "@/util/useSingularToast";
import { formatDate } from "@/util/utils";
import { Icon } from "@iconify/vue";
import { isAxiosError } from "axios";
import {
  AutoComplete,
  Button,
  Card,
  Chip,
  DatePicker,
  Fieldset,
  InputText,
  Message,
  MultiSelect,
  Textarea,
} from "primevue";
import type { AutoCompleteCompleteEvent, AutoCompleteOptionSelectEvent, MultiSelectChangeEvent } from "primevue";
import { useForm } from "vee-validate";
import { computed, onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { array, date, number, object, string } from "yup";

type NewAuthorFormSchema = {
  title: string;
  authors: number[];
  isbn: string | null;
  bookDescription: string | null;
  publisherName: string | null;
  publishedDate: Date | null;
  coverUrl: string | null;
  genres: number[];
};

const toast = useSingularToast();
const router = useRouter();
const route = useRoute();
const genreStore = useGenreStore();

const defaultAuthorId = route.query.author as string;

const schema = object({
  title: string().required("Title is required").max(255, "Title must be at most 255 characters long"),
  authors: array()
    .of(number())
    .required("Author is required")
    .min(1, "At least one author is required")
    .test("unique", "All authors must be unique", (value) => (!value ? true : new Set(value).size == value.length)),
  isbn: string()
    .test("length", "ISBN must be 10 or 13 numbers long", (isbn) =>
      isbn ? isbn.length == 10 || isbn.length == 13 : true,
    )
    .matches(/\d*/, "ISBN must contain only numbers"),
  bookDescription: string(),
  publisherName: string().max(100, "Publisher name must be at most 100 chracters long"),
  publishedDate: date().max(new Date(), "Publication date cannot be in the future"),
  coverUrl: string()
    .url("Link to the cover must be a valid URL")
    .max(500, "Cover URL must be at most 500 characters long"),
  genres: array()
    .of(number())
    .test("unique", "All genres must be unique", (value) => (!value ? true : new Set(value).size == value.length)),
});

const initialValues = {
  authors: [],
  genres: [],
};

const { errorBag, defineField, handleSubmit, setFieldError } = useForm<NewAuthorFormSchema>({
  validationSchema: schema,
  initialValues: initialValues,
});

const [title] = defineField("title");
const [authors] = defineField("authors", { validateOnModelUpdate: true });
const [isbn] = defineField("isbn");
const [bookDescription] = defineField("bookDescription");
const [publisherName] = defineField("publisherName");
const [publishedDate] = defineField("publishedDate");
const [coverUrl] = defineField("coverUrl");
const [genres] = defineField("genres");

const submit = handleSubmit(
  async (values) => {
    const payload = {
      authors: values.authors,
      genres: values.genres,
      bookData: {
        title: values.title,
        isbn: values.isbn,
        bookDescription: values.bookDescription,
        publisherName: values.publisherName,
        publishedDate: formatDate(values.publishedDate),
        coverUrl: values.coverUrl,
      },
    };
    console.log(payload);
    try {
      const { data: response } = await api.post<SuccessResponse<NewBookSubmissionResponse>>(
        ENDPOINTS.BOOK_SUBMISSIONS,
        payload,
      );
      toast({
        severity: "success",
        summary: "Submission complete, your submission will now be reviewed by a moderator.",
        group: "message",
        life: 3000,
      });
      router.push(`/submissions/books/${response.data.id}`);
    } catch (error) {
      if (isAxiosError(error) && error.status === 400) {
        const errorData: ErrorResponse<BookSubmissionFields> = error.response?.data;
        errorData.errors.forEach((error) => {
          if (error.field != "general") {
            setFieldError(error.field, error.message);
          }
        });
        toast({
          severity: "error",
          summary: "Please fix the errors in the form before submitting.",
          group: "message",
          life: 3000,
        });
      } else if (isAxiosError(error)) {
        toast({
          severity: "error",
          summary: `Unexpected error: ${error.status} ${error.code}\n Please try again later.`,
          group: "message",
          life: 3000,
        });
      } else {
        toast({
          severity: "error",
          summary: "An unknown error had occurred. Please try again later.",
          group: "message",
          life: 3000,
        });
      }
    }
  },
  () => {
    toast({
      severity: "error",
      summary: "Please fix the errors in the form before submitting.",
      group: "message",
      life: 3000,
    });
  },
);

const authorSearchResult = ref<AuthorResponse[]>([]);

async function searchAuthors(e: AutoCompleteCompleteEvent) {
  const name = e.query;
  try {
    const { data: response } = await api.get<SuccessResponse<AuthorResponse[]>>(ENDPOINTS.AUTHOR_SEARCH_BY_NAME(name));
    authorSearchResult.value = response.data;
  } catch (error) {}
}

const selectedAuthors = ref<AuthorResponse[]>([]);
const selectedGenres = ref<Genre[]>([]);
const sortedAuthors = computed(() =>
  [...selectedAuthors.value].sort((a1, a2) => a1.authorData.authorName.localeCompare(a2.authorData.authorName)),
);
const sortedGenres = computed(() =>
  [...selectedGenres.value].sort((g1, g2) => g1.genreName.localeCompare(g2.genreName)),
);
const searchInput = ref<string>("");

function addAuthor(e: AutoCompleteOptionSelectEvent) {
  authors.value.push(e.value.id);
  selectedAuthors.value.push(e.value);

  // Clear input
  searchInput.value = "";
  // Clear previous search result
  authorSearchResult.value = [];
}

function addGenre(e: MultiSelectChangeEvent) {
  genres.value = selectedGenres.value.map((genre) => genre.id);
}

function removeAuthor(removedId: number) {
  authors.value.splice(
    authors.value.findIndex((id) => id == removedId),
    1,
  );
  selectedAuthors.value.splice(
    authors.value.findIndex((id) => id == removedId),
    1,
  );
}

function removeGenre(removedId: number) {
  genres.value.splice(
    authors.value.findIndex((id) => id == removedId),
    1,
  );
  selectedGenres.value.splice(
    authors.value.findIndex((id) => id == removedId),
    1,
  );
}

onMounted(async () => {
  if (defaultAuthorId == undefined) return;

  try {
    const { data: response } = await api.get<SuccessResponse<AuthorResponse>>(ENDPOINTS.AUTHOR_DATA(defaultAuthorId));
    authors.value.push(response.data.id);
    selectedAuthors.value.push(response.data);
  } catch (error) {}
});
</script>

<template>
  <section class="w-[min(100%,80em)] px-4">
    <h1 class="text-2xl font-bold">Add a new book</h1>
    <Fieldset legend="Before you submit...">
      <p>
        Your submissions will be reviewed by moderators, but the book will not be searchable on the main site yet.<br />
        All submisison history are public, and will be kept no matter the final review status.
      </p>
    </Fieldset>
    <form class="flex flex-col items-start p-2 mt-6 max-w-[50em]">
      <div class="flex flex-col gap-1 w-full">
        <label for="title">
          Title
          <span class="text-xs text-red-500">Required</span>
        </label>
        <InputText
          id="title"
          v-model="title"
          :invalid="errorBag.title != undefined"
        />
        <Transition>
          <div v-if="errorBag.title != null">
            <Message
              v-for="error in errorBag.title"
              severity="error"
              variant="simple"
              size="small"
            >
              {{ error }}
            </Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1 w-full">
        <label for="authors">
          Author(s)
          <span class="text-xs text-red-500">Required</span>
        </label>
        <AutoComplete
          v-model="searchInput"
          :delay="500"
          :suggestions="authorSearchResult"
          placeholder="Click to search for authors"
          @complete="searchAuthors"
          @option-select="addAuthor"
          :pt="{ input: 'w-[300px]!' }"
          class="mb-1"
        >
          <template #option="result: { option: AuthorResponse }">
            <AuthorInfoBlock
              :id="result.option.id"
              :authorData="result.option.authorData"
            />
          </template>
        </AutoComplete>
        <div
          v-if="authors.length === 0"
          class="text-gray-700 h-10"
        >
          No authors selected
        </div>
        <div
          v-else
          class="flex flex-wrap gap-2"
        >
          <Card
            v-for="author in sortedAuthors"
            class="relative bg-neutral-100!"
          >
            <template #content>
              <Icon
                icon="fa7-solid:xmark"
                width="20"
                height="20"
                class="absolute right-0 top-0 mt-2 mr-2 hover:cursor-pointer"
                @click="removeAuthor(author.id)"
              />
              <AuthorInfoBlock
                :id="author.id"
                :authorData="author.authorData"
              />
            </template>
          </Card>
        </div>
        <Transition>
          <div v-if="errorBag.authors != undefined">
            <Message
              v-for="error in errorBag.authors"
              :key="error"
              severity="error"
              variant="simple"
              size="small"
            >
              {{ error }}
            </Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1 w-full">
        <label for="isbn">
          ISBN
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <InputText
          id="isbn"
          v-model="isbn"
          :invalid="errorBag.isbn != undefined"
        />
        <Transition>
          <div v-if="errorBag.isbn != null">
            <Message
              v-for="error in errorBag.isbn"
              severity="error"
              variant="simple"
              size="small"
            >
              {{ error }}
            </Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1 w-full">
        <label for="bookDescription">
          Book description
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <Textarea
          id="bookDescription"
          v-model="bookDescription"
          :invalid="errorBag.bookDescription != undefined"
          rows="5"
        />
        <Transition>
          <div v-if="errorBag.bookDescription != null">
            <Message
              v-for="error in errorBag.bookDescription"
              severity="error"
              variant="simple"
              size="small"
            >
              {{ error }}
            </Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1 w-full">
        <label for="publisherName">
          Publisher name
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <InputText
          id="publisherName"
          v-model="publisherName"
          :invalid="errorBag.publisherName != undefined"
        />
        <Transition>
          <div v-if="errorBag.publisherName != null">
            <Message
              v-for="error in errorBag.publisherName"
              severity="error"
              variant="simple"
              size="small"
            >
              {{ error }}
            </Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1 w-full">
        <label for="publishedDate">
          Published date
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <DatePicker
          id="publishedDate"
          class="self-start"
          v-model="publishedDate"
          dateFormat="MM dd, yy"
          :maxDate="new Date()"
          :invalid="errorBag.publishedDate != undefined"
        />
        <Transition>
          <div v-if="errorBag.publishedDate != null">
            <Message
              v-for="error in errorBag.publishedDate"
              severity="error"
              variant="simple"
              size="small"
            >
              {{ error }}
            </Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1 w-full">
        <label for="coverUrl">
          Cover URL
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <InputText
          id="coverUrl"
          v-model="coverUrl"
          :invalid="errorBag.coverUrl != undefined"
        />
        <Transition>
          <div v-if="errorBag.coverUrl != undefined">
            <Message
              v-for="error in errorBag.coverUrl"
              severity="error"
              variant="simple"
              size="small"
            >
              {{ error }}
            </Message>
          </div>
        </Transition>
      </div>

      <div class="flex flex-col gap-1 w-full">
        <label for="genres">
          Genres
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <div class="mb-1">
          <MultiSelect
            v-model="selectedGenres"
            placeholder="Add genres"
            :options="genreStore.genres!"
            optionLabel="genreName"
            :maxSelectedLabels="0"
            :showToggleAll="false"
            filter
            @change="addGenre"
          >
            <template #value>
              <div
                v-if="!selectedGenres || selectedGenres.length == 0"
                class="flex items-center gap-2"
              >
                <Icon
                  icon="ic:outline-plus"
                  width="16"
                  height="16"
                />
                Add genres
              </div>
              <div v-else>{{ selectedGenres.length }} genre{{ selectedGenres.length > 1 ? "s" : "" }} selected</div>
            </template>
          </MultiSelect>
        </div>
        <div class="flex flex-wrap gap-2">
          <div
            v-if="!selectedGenres || selectedGenres.length == 0"
            class="text-gray-700 h-10"
          >
            No genres selected
          </div>
          <Chip
            v-else
            v-for="genre in sortedGenres"
            :key="genre.id"
            :label="genre.genreName"
            removable
            @remove="removeGenre(genre.id)"
            :pt="{ body: 'bg-neutral-100' }"
          />
        </div>
        <Transition>
          <div v-if="errorBag.genres != undefined">
            <Message
              v-for="error in errorBag.genres"
              severity="error"
              variant="simple"
              size="small"
            >
              {{ error }}
            </Message>
          </div>
        </Transition>
      </div>

      <Button
        class="mt-6"
        label="Submit"
        severity="info"
        @click="submit"
      />
    </form>
  </section>
</template>

<style scoped>
@reference "tailwindcss";

form > div + div {
  @apply mt-6;
}

.v-enter-active,
.v-leave-active {
  transition: opacity 0.2s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>
