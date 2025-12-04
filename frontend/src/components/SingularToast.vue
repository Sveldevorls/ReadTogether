<script setup lang="ts">
import { Toast } from "primevue";
</script>
<template>
  <Toast
    :pt="{
      transition: {
        enterFromClass: 'opacity-0 translate-y-full',
        enterActiveClass: 'transition-all duration-300 ease-out',
        leaveActiveClass: 'transition-all duration-200 ease-in',
        leaveToClass: 'opacity-0 translate-y-full',
      },
    }"
    position="bottom-center"
    group="message"
  />
</template>
