<script setup lang="ts">
// Todo: Fix flashing screen on load
// Todo: Maximal lodaing time limit?
import { ConfirmDialog } from "primevue";
import { onBeforeMount, ref } from "vue";

import "./App.css";
import Footer from "./components/Footer.vue";
import Header from "./components/Header.vue";
import SingularToast from "./components/SingularToast.vue";
import { useGenreStore } from "./util/genreStore";
import { useUserStore } from "./util/userStore";

const userStore = useUserStore();
const genreStore = useGenreStore();
const isVerifying = ref<boolean>(true);

onBeforeMount(async () => {
  try {
    await userStore.verify();
    await genreStore.fetchGenres();
  } catch (error) {
    console.log(error);
  } finally {
    isVerifying.value = false;
  }
});
</script>

<template>
  <div v-if="!isVerifying">
    <Header />
    <main class="flex justify-center mt-4 mb-auto mx-auto w-[min(100%,80em)] min-h-screen">
      <RouterView></RouterView>
    </main>
    <Footer />
    <div id="overlay">
      <SingularToast />
      <ConfirmDialog />
    </div>
  </div>
</template>

<style scoped></style>
