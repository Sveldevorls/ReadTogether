<script setup lang="ts">
import { useToast } from "primevue";
import { useForm } from "vee-validate";
import { Toast } from "primevue";
import * as yup from "yup";

const toast = useToast();

const schema = yup.object({
  email: yup
    .string()
    .email("Email must be valid")
    .required("Email is required"),
  username: yup
    .string()
    .min(3, "Username must be at least 3 characters long")
    .max(20, "Username must be at most 20 characters long")
    .matches(
      /^[a-zA-Z0-9_]+$/,
      "Username can only contain letters, numbers, and underscores"
    )
    .required("Username is required"),
  password: yup
    .string()
    .min(8, "Password must be at least 8 characters long")
    .required("Password is required"),
  passwordConfirm: yup
    .string()
    .oneOf([yup.ref("password")], "Passwords must match")
    .required("Please confirm your password"),
});

const { defineField, handleSubmit, errors } = useForm({
  validationSchema: schema,
});

const [email] = defineField<string>("email");
const [password] = defineField<string>("password");
const [username] = defineField<string>("username");
const [passwordConfirm] = defineField<string>("passwordConfirm");

const onSubmit = handleSubmit(
  (values) => {
    // placeholder
    console.log("Registering with", values);
  },
  () => {
    toast.add({
      severity: "error",
      summary: "Validation Error",
      detail: "Please fix the errors in the form before submitting.",
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
      <input
        :class="errors.email ? 'border-red-500' : 'border-slate-300'"
        type="text"
        class="w-full h-10 px-2 rounded-sm border bg-white hover:bg-gray-50 focus:bg-white"
        id="email"
        v-model="email"
      />
      <span class="text-sm text-red-500">{{ errors.email }}</span>
      <label for="username">Username</label>
      <input
        :class="errors.username ? 'border-red-500' : 'border-slate-300'"
        type="text"
        class="w-full h-10 px-2 rounded-sm border bg-white hover:bg-gray-50 focus:bg-white"
        id="username"
        v-model="username"
      />
      <span class="text-sm text-red-500">{{ errors.username }}</span>
      <label for="password">Password</label>
      <input
        :class="errors.password ? 'border-red-500' : 'border-slate-300'"
        type="password"
        class="w-full h-10 px-2 rounded-sm border bg-white hover:bg-gray-50 focus:bg-white"
        id="password"
        v-model="password"
      />
      <span class="text-sm text-red-500">{{ errors.password }}</span>
      <label for="passwordConfirm">Password confirmation</label>
      <input
        :class="errors.passwordConfirm ? 'border-red-500' : 'border-slate-300'"
        type="password"
        class="w-full h-10 px-2 rounded-sm border bg-white hover:bg-gray-50 focus:bg-white"
        id="passwordConfirm"
        v-model="passwordConfirm"
      />
      <span class="text-sm text-red-500">{{ errors.passwordConfirm }}</span>
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

span + label {
  @apply mt-4;
}

span {
  @apply mt-1;
}
</style>
