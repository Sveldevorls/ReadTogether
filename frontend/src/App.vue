<script setup lang="ts">
import "./App.css";
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
import SingularToast from "./components/SingularToast.vue";
import { onBeforeMount } from "vue";
import api from "./util/api";
import { useUserStore } from "./util/userStore";
import { ENDPOINTS } from "./util/endpoints";
import type { SuccessResponse, VerifyResponse } from "./util/responses";

const userStore = useUserStore();

onBeforeMount(async () => {
  try {
    const { data: response } = await api.post<SuccessResponse<VerifyResponse>>(ENDPOINTS.VERIFY);
    userStore.setUsername(response.data.username);
    userStore.setRole(userStore.parseRole(response.data.role));
  } catch (error) {
    if (localStorage.getItem("token") != null) {
      localStorage.removeItem("token");
    }
  }
});
</script>

<template>
  <Header />
  <main class="flex justify-center mt-4 mb-auto mx-auto w-[min(100%,80em)]">
    <RouterView></RouterView>
  </main>
  <Footer />
  <div id="overlay">
    <SingularToast />
  </div>
</template>

<style scoped></style>
