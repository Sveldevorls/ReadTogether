<script setup lang="ts">
import { onBeforeMount } from "vue";

import "./App.css";
import Footer from "./components/Footer.vue";
import Header from "./components/Header.vue";
import SingularToast from "./components/SingularToast.vue";
import { useUserStore } from "./util/userStore";

const userStore = useUserStore();

onBeforeMount(async () => {
  await userStore.verify();
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
