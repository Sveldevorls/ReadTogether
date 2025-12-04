<script setup lang="ts">
import { ref } from "vue";
import { Menubar } from "primevue";
import type { MenuItem } from "primevue/menuitem";

const navbarItems = ref<MenuItem[]>([
  {
    label: "Home",
    route: "/",
  },
  {
    label: "My books",
    route: "/mybooks",
  },
  {
    label: "Browse",
  },
  {
    label: "Community",
  },
]);
</script>

<template>
  <header class="sticky top-0 bg-slate-50 shadow">
    <Menubar
      :model="navbarItems"
      :dt="{
        root: {
          background: 'none',
          transitionDuration: '0s',
        },
      }"
      :pt="{
        root: 'h-12 !max-w-[80em] mx-auto !border-0',
        rootList: '!p-0 !gap-0 !rounded-none',
        button: '!h-12 !w-12 !rounded-none !px-4 hover:!bg-slate-200',
        item: 'bg-slate-50',
        itemContent: '!rounded-none hover:!bg-slate-200',
        end: '!h-12 hover:bg-slate-200',
      }"
    >
      <template #item="{ item }">
        <router-link
          v-if="item.route"
          :to="item.route"
          class="flex items-center h-12 px-4 py-2"
        >
          <span>{{ item.label }}</span>
        </router-link>
        <a
          v-else
          class="flex items-center h-12 px-4 py-2"
        >
          <span>{{ item.label }}</span>
        </a>
      </template>
      <template #end>
        <router-link
          to="/login"
          class="flex items-center h-full px-4"
        >
          <span>Log in</span>
        </router-link>
      </template>
    </Menubar>
  </header>
</template>

<style scoped>
@reference "tailwindcss";

:deep(.p-menubar-mobile-active > .p-menubar-root-list) {
  @apply shadow;
}

:deep(.p-menubar-mobile) {
  @apply !p-0;
}
</style>
