<script setup lang="ts">
import { useRoute } from 'vue-router';

const route = useRoute();
const username = route.params.username;
</script>

<template>
  User {{ username }}
</template>

<style scoped>

</style>