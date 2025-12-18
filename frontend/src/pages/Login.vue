<script setup lang="ts">
// Todo: Fix error message display bug when after a 400 response manually populated errors disappear on blur at form level rather than field level
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { LoginPageFields } from "@/util/fields";
import type { ErrorResponse, LoginResponse, SuccessResponse } from "@/util/responses";
import { useSingularToast } from "@/util/useSingularToast";
import { useUserStore } from "@/util/userStore";
import { isAxiosError } from "axios";
import { InputText, Message } from "primevue";
import { useForm } from "vee-validate";
import { useRouter } from "vue-router";
import { object, string } from "yup";

const toast = useSingularToast();
const userStore = useUserStore();
const router = useRouter();
const schema = object({
  identifier: string().required("Username or email is required"),
  password: string().required("Password is required"),
});

const { defineField, handleSubmit, setFieldError, errors } = useForm({
  validationSchema: schema,
});

const [identifier] = defineField<string>("identifier");
const [password] = defineField<string>("password");

const onSubmit = handleSubmit(
  async (values) => {
    try {
      const { data: loginResponse } = await api.post<SuccessResponse<LoginResponse>>(ENDPOINTS.LOGIN, values);
      const token = loginResponse.data.token;
      localStorage.setItem("token", token);
      userStore.setUser(loginResponse.data.user);
      toast({
        severity: "success",
        summary: "Login complete",
        group: "message",
        life: 3000,
      });
      router.push("/");
    } catch (error) {
      if (isAxiosError(error) && error.status === 400) {
        const errorData: ErrorResponse<LoginPageFields> = error.response?.data;
        errorData.errors.forEach((error) => {
          setFieldError("identifier", error.message);
          setFieldError("password", error.message);
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
        console.log(error);
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
    <h1 class="text-center font-bold text-2xl">Log in to ReadTogether</h1>
    <form
      id="login"
      class="flex flex-col p-2 w-full"
      @submit="onSubmit"
    >
      <label for="identifier">Username or email</label>
      <InputText
        :invalid="!!errors.identifier"
        type="text"
        id="identifier"
        v-model="identifier"
      />
      <Transition>
        <div v-if="errors.identifier">
          <Message
            severity="error"
            variant="simple"
            size="small"
          >
            {{ errors.identifier }}
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
    </form>
    <button
      form="login"
      class="py-2 bg-blue-200 hover:bg-blue-300 border border-slate-100 rounded-sm"
    >
      Log in
    </button>
    <p class="text-center">
      Don't have an account yet?
      <router-link
        to="/register"
        class="inline-block text-blue-600 hover:underline"
      >
        Register here
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
