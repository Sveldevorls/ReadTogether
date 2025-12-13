<script setup lang="ts">
// Todo: Backend verificatio and response / patch
// Todo: Only one active at a time?
import BioSetting from "@/components/settings/BioSetting.vue";
import DisplayNameSetting from "@/components/settings/DisplayNameSetting.vue";
import { Divider } from "primevue";
</script>

<template>
  <section class="w-[min(100%,60em)] p-4">
    <DisplayNameSetting />
    <Divider />
    <BioSetting />
  </section>
</template>
