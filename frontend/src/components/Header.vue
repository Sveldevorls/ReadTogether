<script setup lang="ts">
// Todo: If switched to access-refresh token system: update logout function
import defaultCover from "@/assets/default_cover.svg";
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { BookSummary, SuccessResponse } from "@/util/responses";
import { URLS } from "@/util/urls";
import { useSingularToast } from "@/util/useSingularToast";
import { useUserStore } from "@/util/userStore";
import { Icon } from "@iconify/vue";
import { AutoComplete, Menu, Menubar } from "primevue";
import type { AutoCompleteCompleteEvent, AutoCompleteOptionSelectEvent } from "primevue";
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
        separator: true,
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

const searchInput = ref<string>("");
const bookSearchResult = ref<BookSummary[]>([]);
const showMobileSearch = ref(false);

async function searchBooks(e: AutoCompleteCompleteEvent) {
  const title = e.query;
  try {
    const { data: response } = await api.get<SuccessResponse<BookSummary[]>>(ENDPOINTS.BOOK_SEARCH_BY_TITLE(title));
    bookSearchResult.value = response.data;
  } catch (error) {}
}

function navigateToBook(e: AutoCompleteOptionSelectEvent) {
  router.push(URLS.BOOK_PAGE(e.value.id, e.value.slug));
  searchInput.value = "";
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
        end: '!h-12 flex items-center',
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
        <div class="flex items-center">
          <!-- Inline search for large screens -->
          <div class="hidden lg:flex items-center">
            <AutoComplete
              v-model="searchInput"
              :delay="500"
              :suggestions="bookSearchResult"
              placeholder="Search books"
              @complete="searchBooks"
              @option-select="navigateToBook"
              :pt="{ input: 'w-[300px]! h-[36px]!' }"
              :inputStyle="{ width: '300px', height: '36px' }"
              :panelStyle="{ width: '300px' }"
              class="shrink"
            >
              <template #option="result: { option: BookSummary }">
                <div class="flex w-full gap-3 items-center" :key="result.option.id">
                  <img
                    :src="result.option.coverUrl ?? defaultCover"
                    class="w-12 aspect-square object-cover"
                  />
                  <div class="flex flex-col truncate">
                    <span class="truncate font-bold">{{ result.option.title }}</span>
                    <div>
                      <span
                        v-for="author in result.option.authors"
                        :key="author.id"
                        class="truncate"
                        >{{ author.authorName }}</span
                      >
                    </div>
                  </div>
                </div>
              </template>
            </AutoComplete>
          </div>

          <!-- Search button for md and smaller (toggles a row beneath the menubar) -->
          <button
            @click="showMobileSearch = !showMobileSearch"
            class="flex items-center h-full px-4 lg:hidden"
            aria-label="Toggle search"
          >
            <Icon
              icon="material-symbols:search"
              width="24"
              height="24"
            />
          </button>
          <div class="hover:bg-slate-200">
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
              <button class="flex items-center h-12 px-4">
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
          </div>
        </div>
      </template>
    </Menubar>

    <!-- Mobile search row (appears under the menubar when toggled) -->
    <div
      v-if="showMobileSearch"
      class="bg-slate-50 border-t border-t-slate-200 lg:hidden"
    >
      <div class="max-w-[80em] mx-auto py-2 flex justify-center">
        <AutoComplete
          v-model="searchInput"
          :delay="500"
          :suggestions="bookSearchResult"
          placeholder="Search books"
          @complete="searchBooks"
          @option-select="navigateToBook"
          :inputStyle="{ width: '300px', height: '36px' }"
          :panelStyle="{ width: '300px' }"
        >
          <template #option="result: { option: BookSummary }">
            <div class="flex w-full gap-1 items-center" :key="result.option.id">
              <img
                :src="result.option.coverUrl ?? defaultCover"
                class="w-12 aspect-square object-cover"
              />
              <div class="flex flex-col truncate">
                <span class="truncate font-bold">{{ result.option.title }}</span>
                <div>
                  <span
                    v-for="author in result.option.authors"
                    class="truncate"
                    >{{ author.authorName }}</span
                  >
                </div>
              </div>
            </div>
          </template>
        </AutoComplete>
      </div>
    </div>
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
