<script setup lang="ts">
import { ref } from "vue";
const email = ref<string>("");
const password = ref<string>("");

function login() {
  // placeholder
  console.log("Logging in with", email.value, password.value);
}
</script>

<template>
  <section
    class="flex flex-col gap-6 p-6 w-[min(100%,500px)] rounded-md bg-slate-100 @container"
  >
    <h1 class="text-center font-bold text-2xl">Log in to ReadTogether</h1>
    <form
      id="login"
      class="flex flex-col gap-2 @md:grid @md:grid-cols-[80px_1fr] @md:items-center p-2 w-full"
      @submit.prevent="login"
    >
      <label for="email">Email</label>
      <input
        type="text"
        class="w-full h-10 px-2 rounded-sm border border-slate-300 bg-white hover:bg-gray-50 focus:bg-white"
        id="email"
        v-model="email"
      />
      <label for="password">Password</label>
      <input
        type="password"
        class="w-full h-10 px-2 rounded-sm border border-slate-300 bg-white hover:bg-gray-50 focus:bg-white"
        id="password"
        v-model="password"
      />
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
<style scoped></style>
