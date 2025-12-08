<script setup lang="ts">
import { ref } from "vue";
import { InputText, Message } from "primevue";
import { useForm } from "vee-validate";
import { object, string, ref as yupRef } from "yup";
import api from "@/util/api";
import { useRouter } from "vue-router";
import { isAxiosError } from "axios";
import type { ErrorResponse, RegisterPageFields } from "@/util/types";
import { useSingularToast } from "@/util/useSingularToast";

const toast = useSingularToast();
const router = useRouter();
const schema = object({
  email: string()
    .email("Email format is not valid")
    .required("Email is required"),
  username: string()
    .min(3, "Username must be at least 3 characters long")
    .max(20, "Username must be at most 20 characters long")
    .matches(
      /^[a-zA-Z0-9_]+$/,
      "Username can only contain letters, numbers, and underscores"
    )
    .required("Username is required"),
  password: string()
    .min(8, "Password must be at least 8 characters long")
    .max(64, "Password must be at most 64 characters long")
    .required("Password is required"),
  passwordConfirm: string()
    .oneOf([yupRef("password")], "Passwords must match")
    .required("Please confirm your password"),
});

const fieldIsFocused = ref<Record<RegisterPageFields, boolean>>({
  email: false,
  password: false,
  username: false,
  passwordConfirm: false,
});

const fieldConfig = {
  validateOnBlur: true,
  validateOnModelUpdate: false,
};

const { defineField, handleSubmit, setFieldError, errors } = useForm({
  validationSchema: schema,
});

const [email, emailProps] = defineField<string>("email", fieldConfig);
const [password, passwordProps] = defineField<string>("password", fieldConfig);
const [username, usernameProps] = defineField<string>("username", fieldConfig);
const [passwordConfirm, passwordConfirmProps] = defineField<string>(
  "passwordConfirm",
  fieldConfig
);

function handleFocus(fieldName: RegisterPageFields): void {
  fieldIsFocused.value[fieldName] = true;
}

function handleBlur(fieldName: RegisterPageFields): void {
  fieldIsFocused.value[fieldName] = false;
}

const onSubmit = handleSubmit(
  async (values) => {
    try {
      await api.post("/api/register", values);
      toast({
        severity: "success",
        summary: "Registration complete",
        group: "message",
        life: 3000,
      });
      router.push("/");
    } catch (error) {
      if (isAxiosError(error)) {
        if (error.status === 400) {
          const errorData: ErrorResponse<RegisterPageFields> = error.response?.data;
          errorData.errors.forEach((error) => {
            setFieldError(error.field, error.message);
          });
          toast({
            severity: "error",
            summary: "Please fix the errors in the form before submitting.",
            group: "message",
            life: 3000,
          });
        } else {
          toast({
            severity: "error",
            summary: `Unexpected error: ${error.status} ${error.code}\n Please try again later.`,
            group: "message",
            life: 3000,
          });
        }
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
  }
);
</script>

<template>
  <section
    class="flex flex-col gap-6 p-6 w-[min(100%,500px)] rounded-md bg-slate-100"
  >
    <h1 class="text-center font-bold text-2xl">Register</h1>
    <form
      id="register"
      class="flex flex-col p-2 w-full"
      @submit="onSubmit"
    >
      <label for="username">Username</label>
      <InputText
        :invalid="!!errors.username"
        type="text"
        id="username"
        v-model="username"
        v-bind="usernameProps"
        @focus="handleFocus('username')"
        @blur="handleBlur('username')"
      />
      <Message
        severity="error"
        variant="simple"
        size="small"
        v-if="!fieldIsFocused.username"
      >
        {{ errors.username }}
      </Message>
      <label for="email">Email</label>
      <InputText
        :invalid="!!errors.email"
        type="text"
        id="email"
        v-model="email"
        v-bind="emailProps"
        @focus="handleFocus('email')"
        @blur="handleBlur('email')"
      />
      <Message
        severity="error"
        variant="simple"
        size="small"
        v-if="!fieldIsFocused.email"
      >
        {{ errors.email }}
      </Message>
      <label for="password">Password</label>
      <InputText
        :invalid="!!errors.password"
        type="password"
        id="password"
        v-model="password"
        v-bind="passwordProps"
        @focus="handleFocus('password')"
        @blur="handleBlur('password')"
      />
      <Message
        severity="error"
        variant="simple"
        size="small"
        v-if="!fieldIsFocused.password"
      >
        {{ errors.password }}
      </Message>
      <label for="passwordConfirm">Password confirmation</label>
      <InputText
        :invalid="!!errors.passwordConfirm"
        type="password"
        id="passwordConfirm"
        v-model="passwordConfirm"
        v-bind="passwordConfirmProps"
        @focus="handleFocus('passwordConfirm')"
        @blur="handleBlur('passwordConfirm')"
      />
      <Message
        severity="error"
        variant="simple"
        size="small"
        v-if="!fieldIsFocused.passwordConfirm"
      >
        {{ errors.passwordConfirm }}
      </Message>
    </form>
    <button
      form="register"
      class="py-2 bg-blue-200 hover:bg-blue-300 border border-slate-100 rounded-sm"
    >
      Register
    </button>
    <p class="text-center">
      Already have an account?
      <router-link
        to="/login"
        class="inline-block text-blue-600 hover:underline"
      >
        Log in here
      </router-link>
    </p>
  </section>
</template>

<style scoped>
@reference "tailwindcss";

label:not(:first-child) {
  @apply mt-4;
}
</style>
