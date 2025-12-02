<script setup lang="ts">
import "./App.css";
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
</script>

<template>
  <Header />
  <main class="flex justify-center mt-4 mb-auto mx-auto w-[min(100%,100em)]">
    <RouterView></RouterView>
  </main>
  <Footer />
</template>

<style scoped></style>
