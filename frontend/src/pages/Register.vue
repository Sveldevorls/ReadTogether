<script setup lang="ts">
// Todo: Fix error message display bug when after a 400 response manually populated errors disappear on blur at form level rather than field level
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { RegisterPageFields } from "@/util/fields";
import type { ErrorResponse, RegisterResponse, SuccessResponse } from "@/util/responses";
import { useSingularToast } from "@/util/useSingularToast";
import { useUserStore } from "@/util/userStore";
import { isAxiosError } from "axios";
import { InputText, Message } from "primevue";
import { useForm } from "vee-validate";
import { useRouter } from "vue-router";
import { object, string, ref as yupRef } from "yup";

const toast = useSingularToast();
const userStore = useUserStore();
const router = useRouter();
const schema = object({
  email: string().email("Email format is not valid").required("Email is required"),
  username: string()
    .min(3, "Username must be at least 3 characters long")
    .max(20, "Username must be at most 20 characters long")
    .matches(/^[a-zA-Z0-9_]+$/, "Username can only contain letters, numbers, and underscores")
    .required("Username is required"),
  password: string()
    .min(8, "Password must be at least 8 characters long")
    .max(64, "Password must be at most 64 characters long")
    .required("Password is required"),
  passwordConfirm: string()
    .oneOf([yupRef("password")], "Passwords must match")
    .required("Please confirm your password"),
});

const { defineField, handleSubmit, setFieldError, errors } = useForm({
  validationSchema: schema,
});

const [email] = defineField<string>("email");
const [password] = defineField<string>("password");
const [username] = defineField<string>("username");
const [passwordConfirm] = defineField<string>("passwordConfirm");

const onSubmit = handleSubmit(
  async (values) => {
    try {
      const { data: registerResponse } = await api.post<SuccessResponse<RegisterResponse>>(ENDPOINTS.REGISTER, values);
      const token = registerResponse.data.token;
      localStorage.setItem("token", token);
      userStore.setUser(registerResponse.data.user);
      toast({
        severity: "success",
        summary: "Registration complete",
        group: "message",
        life: 3000,
      });
      router.push("/");
    } catch (error) {
      if (isAxiosError(error) && error.status === 400) {
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
  <section class="flex flex-col gap-6 p-6 w-[min(100%,500px)] h-fit rounded-md bg-slate-100">
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
      />
      <Transition>
        <div v-if="errors.username">
          <Message
            severity="error"
            variant="simple"
            size="small"
          >
            {{ errors.username }}
          </Message>
        </div>
      </Transition>
      <label for="email">Email</label>
      <InputText
        :invalid="!!errors.email"
        type="text"
        id="email"
        v-model="email"
      />
      <Transition>
        <div v-if="errors.email">
          <Message
            severity="error"
            variant="simple"
            size="small"
          >
            {{ errors.email }}
          </Message>
        </div>
      </Transition>
      <label for="password">Password</label>
      <InputText
        :invalid="!!errors.password"
        type="password"
        id="password"
        v-model="password"
      />
      <Transition>
        <div v-if="errors.password">
          <Message
            severity="error"
            variant="simple"
            size="small"
          >
            {{ errors.password }}
          </Message>
        </div>
      </Transition>
      <label for="passwordConfirm">Password confirmation</label>
      <InputText
        :invalid="!!errors.passwordConfirm"
        type="password"
        id="passwordConfirm"
        v-model="passwordConfirm"
      />
      <Transition>
        <div v-if="errors.passwordConfirm">
          <Message
            severity="error"
            variant="simple"
            size="small"
          >
            {{ errors.passwordConfirm }}
          </Message>
        </div>
      </Transition>
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

.v-enter-active,
.v-leave-active {
  transition: opacity 0.2s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>
