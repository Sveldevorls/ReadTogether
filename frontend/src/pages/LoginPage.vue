<script setup lang="ts">
// Todo: Fix form validation flashing error on blur when previously invalid value becomes valid
// Todo: Fix error message display bug when after a 400 response manually populated errors disappear on blur at form level rather than field level
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { LoginPageFields } from "@/util/fields";
import type { ErrorResponse, LoginResponse, SuccessResponse, VerifyResponse } from "@/util/responses";
import { useSingularToast } from "@/util/useSingularToast";
import { useUserStore } from "@/util/userStore";
import { isAxiosError } from "axios";
import { InputText, Message } from "primevue";
import { useForm } from "vee-validate";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { object, string } from "yup";

const toast = useSingularToast();
const userStore = useUserStore();
const router = useRouter();
const schema = object({
  identifier: string().required("Username or email is required"),
  password: string().required("Password is required"),
});

const fieldIsFocused = ref<Record<LoginPageFields, boolean>>({
  identifier: false,
  password: false,
});

const fieldConfig = {
  validateOnBlur: true,
  validateOnModelUpdate: false,
};

const { defineField, handleSubmit, setFieldError, errors } = useForm({
  validationSchema: schema,
});

const [identifier, identifierProps] = defineField<string>("identifier", fieldConfig);
const [password, passwordProps] = defineField<string>("password", fieldConfig);

function handleFocus(fieldName: LoginPageFields): void {
  fieldIsFocused.value[fieldName] = true;
}

function handleBlur(fieldName: LoginPageFields): void {
  fieldIsFocused.value[fieldName] = false;
}

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
  <section class="flex flex-col gap-6 p-6 w-[min(100%,500px)] rounded-md bg-slate-100 @container">
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
        v-bind="identifierProps"
        @focus="handleFocus('identifier')"
        @blur="handleBlur('identifier')"
      />
      <Message
        severity="error"
        variant="simple"
        size="small"
        v-if="!fieldIsFocused.identifier"
      >
        {{ errors.identifier }}
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
</style>
