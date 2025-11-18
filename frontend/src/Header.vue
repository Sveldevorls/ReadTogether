<script setup lang="ts">
import { ref } from "vue";
import { Menubar } from "primevue";
import type { MenuItem } from "primevue/menuitem";
import { useRouter } from "vue-router";

const router = useRouter();
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
  <header class="sticky top-0 bg-amber-50 shadow">
    <Menubar :model="navbarItems">
      <template #item="{ item }">
        <router-link
          v-if="item.route"
          :to="item.route"
          class="block p-2 hover:bg-gray-500"
        >
          <span>{{ item.label }}</span>
        </router-link>
        <a
          v-else
          class="block p-2 hover:bg-gray-500"
        >
          <span>{{ item.label }}</span>
        </a>
      </template>
      <template #end>
        <router-link to="/login">
          <a class="block p-2 hover:bg-gray-500">
            <span>Log in</span>
          </a>
        </router-link>
      </template>
    </Menubar>
  </header>
</template>
