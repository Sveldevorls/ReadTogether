<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import { useGenreStore } from "@/util/genreStore";
import type { ErrorResponse, SuccessResponse } from "@/util/responses";
import type { AuthorLink, Genre } from "@/util/types";
import { useSingularToast } from "@/util/useSingularToast";
import { Icon } from "@iconify/vue";
import { isAxiosError } from "axios";
import { Button, Chip, DatePicker, Fieldset, InputText, Message, Select, Textarea } from "primevue";
import { useForm } from "vee-validate";
import { ref } from "vue";
import { useRouter } from "vue-router";
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
const genreStore = useGenreStore();

const selectedGenres = ref<Genre | null>(null);
const selectedAuthors = ref<AuthorLink | null>(null);

const isSelectingGenres = ref<boolean>(false);

const schema = object({
  title: string().required("Title is required").max(255, "Title must be at most 255 characters long"),
  authors: array()
    .of(
      object({
        id: number().nonNullable().defined(),
        slug: string().nonNullable().defined(),
        authorName: string().nonNullable().defined(),
      }),
    )
    .min(1, "Author is required")
    .test(
      "unique",
      "All provided authors must be unique",
      (value) => value && new Set(value.map((author) => author.id)).size == value.length,
    ),
  isbn: string()
    .test("length", "Incorrect ISBN length, should be 10 or 13 numbers long", (isbn) =>
      isbn ? isbn.length == 10 || isbn.length == 13 : true,
    )
    .matches(/\d*/, "Only numbers are allowed"),
  bookDescription: string(),
  publisherName: string(),
  publishedDate: date().max(new Date(), "Publication cannot be in the future"),
  coverUrl: string().url("Link to the cover must be a valid URL"),
  genres: array()
    .of(
      object({
        id: number().nonNullable().defined(),
        slug: string().nonNullable().defined(),
        genreName: string().nonNullable().defined(),
      }),
    )
    .test(
      "unique",
      "All provided genres must be unique.",
      (value) => value && new Set(value.map((genre) => genre.id)).size == value.length,
    ),
});

const { errorBag, defineField, handleSubmit, setFieldError } = useForm<NewAuthorFormSchema>({
  validationSchema: schema,
});
const [title] = defineField("title");
const [authors] = defineField("authors");
const [isbn] = defineField("isbn");
const [bookDescription] = defineField("bookDescription");
const [publisherName] = defineField("publisherName");
const [publishedDate] = defineField("publishedDate");
const [coverUrl] = defineField("coverUrl");
const [genres] = defineField("genres");

function convertDateToString(date: Date | null): string | null {
  if (!date) return null;
  return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, "0")}-${date.getDate().toString().padStart(2, "0")}`;
}

function handleGenreSelect(e) {
  console.log(e);
}

const submit = handleSubmit(
  async (values) => {
    const payload = {
      authors: authors.value,
      genres: genres.value,
      bookData: {
        title: title.value,
        isbn: isbn.value,
        bookDescription: bookDescription.value,
        publisherName: publisherName.value,
        publishedDate: publishedDate.value,
        coverUrl: coverUrl.value,
      },
    };
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
      router.push(`/submissions/authors/${response.data.id}`);
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
        <div></div>
        <Transition>
          <div v-if="errorBag.authors?.length">
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
        <div class="text-sm">
          <Button
            v-if="!isSelectingGenres"
            label="Add genre"
            severity="secondary"
            rounded
            @click="isSelectingGenres = true"
          >
            <template #icon>
              <Icon
                icon="ic:outline-plus"
                width="16"
                height="16"
              />
            </template>
          </Button>
          <!-- todo: fix this -->
          <Select
            v-else
            :options="genreStore.genres!"
            optionLabel="genreName"
            @select="(e) => handleGenreSelect(e)"
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
