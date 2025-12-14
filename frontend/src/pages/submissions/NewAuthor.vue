<script setup lang="ts">
import { Button, DatePicker, InputText, Message, Textarea } from "primevue";
import { useForm } from "vee-validate";
import { date, object, string } from "yup";

type NewAuthorFormSchema = {
  authorName: string;
  dateOfBirth: Date | null;
  dateOfDeath: Date | null;
  authorImageUrl: string | null;
  biography: string | null;
  submitterComment: string | null;
};

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
  submitterComment: string().max(500, "Comment must be at most 500 characters long"),
});

const { errorBag, defineField, handleSubmit } = useForm<NewAuthorFormSchema>({ validationSchema: schema });
const [authorName] = defineField("authorName");
const [dateOfBirth] = defineField("dateOfBirth");
const [dateOfDeath] = defineField("dateOfDeath");
const [authorImageUrl] = defineField("authorImageUrl");
const [biography] = defineField("biography");
const [submitterComment] = defineField("submitterComment");

const submit = handleSubmit((values) => {
  function convertDateToUtc(date: Date): Date {
    return new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDay()));
  }

  if (values.dateOfBirth) {
    values.dateOfBirth = convertDateToUtc(values.dateOfBirth);
  }
  if (values.dateOfDeath) {
    values.dateOfDeath = convertDateToUtc(values.dateOfDeath);
  }

  console.log(values);
  console.log(JSON.stringify(values));
});
</script>

<template>
  <section class="w-[min(100%,80em)] px-4">
    <h1 class="text-center font-bold text-2xl">Submit new author</h1>
    <form class="flex flex-col items-start p-2 max-w-[50em]">
      <div class="flex flex-col md:grid md:grid-cols-[1fr_3fr] w-full">
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

      <div class="flex flex-col md:grid md:grid-cols-[1fr_3fr] w-full">
        <label for="dateOfBirth">
          Date of birth
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <DatePicker
          id="dateOfBirth"
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

      <div class="flex flex-col md:grid md:grid-cols-[1fr_3fr] w-full">
        <label for="dateOfDeath">
          Date of death
          <span class="text-xs text-gray-500">(Optional)</span>
        </label>
        <DatePicker
          id="dateOfDeath"
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

      <div class="flex flex-col md:grid md:grid-cols-[1fr_3fr] w-full">
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

      <div class="flex flex-col md:grid md:grid-cols-[1fr_3fr] w-full">
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

      <div class="flex flex-col md:grid md:grid-cols-[1fr_3fr] w-full">
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
        label="Submit"
        severity="info"
        @click="submit"
      />
    </form>
  </section>
</template>

<style scoped>
@reference "tailwindcss";

.v-enter-active,
.v-leave-active {
  transition: opacity 0.2s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>
