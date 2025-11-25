<script setup lang="ts">
import { ref } from "vue";
import { useToast, InputText, Message } from "primevue";
import { useForm } from "vee-validate";
import { Toast } from "primevue";
import { object, string, ref as yupRef } from "yup";
import api from "./api";

type fieldNames = "email" | "username" | "password" | "passwordConfirm";

const toast = useToast();
const schema = object({
  email: string().email("Email is not valid").required("Email is required"),
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
    .required("Password is required"),
  passwordConfirm: string()
    .oneOf([yupRef("password")], "Passwords must match")
    .required("Please confirm your password"),
});

const isFocusedStates = ref<Record<fieldNames, boolean>>({
  email: false,
  password: false,
  username: false,
  passwordConfirm: false,
});

const fieldConfig = {
  validateOnBlur: true,
  validateOnModelUpdate: false,
};

const { defineField, handleSubmit, errors } = useForm({
  validationSchema: schema,
});

const [email, emailProps] = defineField<string>("email", fieldConfig);
const [password, passwordProps] = defineField<string>("password", fieldConfig);
const [username, usernameProps] = defineField<string>("username", fieldConfig);
const [passwordConfirm, passwordConfirmProps] = defineField<string>(
  "passwordConfirm",
  fieldConfig
);

function handleFocus(fieldName: fieldNames): void {
  isFocusedStates.value[fieldName] = true;
}

function handleBlur(fieldName: fieldNames): void {
  isFocusedStates.value[fieldName] = false;
}

const onSubmit = handleSubmit(
  async (values) => {
    console.log(values);
    // placeholder
    console.log("Registering with", values);
    const result = await api.post("/api/register", values);
    console.log(result);
  },
  () => {
    toast.add({
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
        v-if="!isFocusedStates.email"
      >
        {{ errors.email }}
      </Message>
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
        v-if="!isFocusedStates.username"
      >
        {{ errors.username }}
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
        v-if="!isFocusedStates.password"
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
        v-if="!isFocusedStates.passwordConfirm"
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
    <Toast
      position="bottom-center"
      group="message"
    />
  </section>
</template>

<style scoped>
@reference "tailwindcss";

label:not(:first-child) {
  @apply mt-4;
}
</style>
