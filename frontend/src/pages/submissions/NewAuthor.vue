<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { AuthorDataFields } from "@/util/fields";
import type { ErrorResponse, NewAuthorSubmissionResponse, SuccessResponse } from "@/util/responses";
import { useSingularToast } from "@/util/useSingularToast";
import { isAxiosError } from "axios";
import { Button, DatePicker, Fieldset, InputText, Message, Textarea } from "primevue";
import { useForm } from "vee-validate";
import { useRouter } from "vue-router";
import { date, object, string } from "yup";

type NewAuthorFormSchema = {
  authorName: string;
  dateOfBirth: Date | null;
  dateOfDeath: Date | null;
  authorImageUrl: string | null;
  biography: string | null;
  submitterComment: string | null;
};

const toast = useSingularToast();
const router = useRouter();

const schema = object({
  authorName: string().required("Author name is required").max(255, "Author name must be at most 255 characters long"),
  dateOfBirth: date()
    .max(new Date(), "Date of birth cannot be in the future")
    .test("dob-before-dod", "Date of birth cannot be after date of death", function (value) {
      const { dateOfDeath } = this.parent;
      if (!value || !dateOfDeath) return true;
      return value <= dateOfDeath;
    }),
  dateOfDeath: date()
    .max(new Date(), "Date of death cannot be in the future")
    .test("dod-after-dob", "Date of death cannot be before date of birth", function (value) {
      const { dateOfBirth } = this.parent;
      if (!value || !dateOfBirth) return true;
      return value >= dateOfBirth;
    }),
  authorImageUrl: string().url("Link to the author's image must be a valid URL"),
  biography: string().max(500, "Author biography must be at most 500 characters long"),
  submitterComment: string().max(500, "Comments must be at most 500 characters long"),
});

const { errorBag, defineField, handleSubmit, setFieldError } = useForm<NewAuthorFormSchema>({
  validationSchema: schema,
});
const [authorName] = defineField("authorName");
const [dateOfBirth] = defineField("dateOfBirth");
const [dateOfDeath] = defineField("dateOfDeath");
const [authorImageUrl] = defineField("authorImageUrl");
const [biography] = defineField("biography");
const [submitterComment] = defineField("submitterComment");

function convertDateToString(date: Date | null): string | null {
  if (!date) return null;
  return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, "0")}-${date.getDate().toString().padStart(2, "0")}`;
}

const submit = handleSubmit(
  async (values) => {
    const payload = {
      authorData: {
        authorName: values.authorName,
        dateOfBirth: convertDateToString(values.dateOfBirth),
        dateOfDeath: convertDateToString(values.dateOfDeath),
        authorImageUrl: values.authorImageUrl,
        biography: values.biography,
      },
      submitterComment: values.submitterComment,
    };
    try {
      const { data: response } = await api.post<SuccessResponse<NewAuthorSubmissionResponse>>(
        ENDPOINTS.AUTHOR_SUBMISSIONS,
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
        const errorData: ErrorResponse<AuthorDataFields> = error.response?.data;
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
    <h1 class="text-2xl font-bold">Add a new author</h1>
    <Fieldset legend="Before you submit...">
      <p>
        Your submissions will be reviewed by moderators, but the author will not be searchable on the main site yet.<br />
        All submisison history are public, and will be kept no matter the final review status.
      </p>
    </Fieldset>
    <form class="flex flex-col items-start p-2 mt-6 max-w-[50em]">
      <div class="flex flex-col gap-1 w-full">
        <label for="authorName">
          Name
          <span class="text-xs text-red-500">Required</span>
        </label>
        <InputText
          id="authorName"
          v-model="authorName"
          :invalid="errorBag.authorName != undefined"
        />
        <Transition>
          <div v-if="errorBag.authorName?.length">
            <Message
              v-for="error in errorBag.authorName"
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
        <label for="dateOfBirth">
          Date of birth
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <DatePicker
          id="dateOfBirth"
          class="self-start"
          v-model="dateOfBirth"
          dateFormat="MM dd, yy"
          :maxDate="new Date()"
          :invalid="errorBag.dateOfBirth != undefined"
        />
        <Transition>
          <div v-if="errorBag.dateOfBirth != null">
            <Message
              v-for="error in errorBag.dateOfBirth"
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
        <label for="dateOfDeath">
          Date of death
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <DatePicker
          id="dateOfDeath"
          class="self-start"
          v-model="dateOfDeath"
          dateFormat="MM dd, yy"
          :maxDate="new Date()"
          :invalid="errorBag.dateOfDeath != undefined"
        />
        <Transition>
          <div v-if="errorBag.dateOfDeath != null">
            <Message
              v-for="error in errorBag.dateOfDeath"
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
        <label for="authorImageUrl">
          Author image link
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <InputText
          id="authorImageUrl"
          v-model="authorImageUrl"
          :invalid="errorBag.authorImageUrl != undefined"
        />
        <Transition>
          <div v-if="errorBag.authorImageUrl != null">
            <Message
              v-for="error in errorBag.authorImageUrl"
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
        <label for="biography">
          Author biography
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <Textarea
          id="biography"
          v-model="biography"
          :invalid="errorBag.biography != undefined"
          rows="5"
        />
        <Transition>
          <div v-if="errorBag.biography != undefined">
            <Message
              v-for="error in errorBag.biography"
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
        <label for="submitterComment">
          Additional comments
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <Textarea
          id="submitterComment"
          v-model="submitterComment"
          :invalid="errorBag.submitterComment != undefined"
          rows="5"
        />
        <Transition>
          <div v-if="errorBag.submitterComment != undefined">
            <Message
              v-for="error in errorBag.submitterComment"
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
