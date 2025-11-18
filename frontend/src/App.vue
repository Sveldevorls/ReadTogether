<script setup lang="ts">
import "./App.css";
import Header from "./Header.vue";
import Footer from "./Footer.vue";
</script>

<template>
  <Header />
  <main class="mt-4 grow">
    <RouterView></RouterView>
  </main>
  <Footer />
</template>

<style scoped></style>
