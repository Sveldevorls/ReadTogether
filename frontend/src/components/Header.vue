<script setup lang="ts">
// Todo: If switched to access-refresh token system: update logout function
import { useSingularToast } from "@/util/useSingularToast";
import { useUserStore } from "@/util/userStore";
import { Menu, Menubar } from "primevue";
import type { MenuItem } from "primevue/menuitem";
import { computed, ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const toast = useSingularToast();
const userStore = useUserStore();
const menu = ref();
const menuItems = computed(() => [
  {
    label: `Hello, ${userStore.displayName ?? userStore.username}`,
    items: [
      {
        label: "Profile",
        route: "/me",
      },
      {
        label: "Settings",
        route: "/settings",
      },
      {
        label: "Log out",
        route: "#",
        class: "text-red-400",
        command: logout,
      },
    ],
  },
]);
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

const toggle = (event: MouseEvent) => {
  menu.value.toggle(event);
};

function logout() {
  localStorage.removeItem("token");
  userStore.clearUser();
  router.push("/");
  toast({
    severity: "success",
    summary: "Successfully logged out",
    group: "message",
    life: 3000,
  });
}
</script>

<template>
  <header class="sticky top-0 bg-slate-50 shadow z-1">
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
          v-if="userStore.role == 0"
          to="/login"
          class="flex items-center h-full px-4"
        >
          <span>Log in</span>
        </router-link>
        <div
          v-else
          @click="toggle"
          class="flex items-center h-full"
        >
          <button class="flex items-center h-full px-4">
            {{ userStore.username }}
          </button>
          <Menu
            ref="menu"
            :model="menuItems"
            :popup="true"
          >
            <template #item="{ item }">
              <router-link
                v-if="item.route"
                :to="item.route"
                class="flex items-center px-4 py-2"
              >
                <span :class="item.class">{{ item.label }}</span>
              </router-link>
            </template>
          </Menu>
        </div>
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
