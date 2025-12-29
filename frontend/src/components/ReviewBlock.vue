<script setup lang="ts">
import defautlAvatar from "@/assets/default_avatar.svg";
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import { roles } from "@/util/enums";
import { parseDate } from "@/util/parser";
import type { ReviewResponse } from "@/util/responses";
import { URLS } from "@/util/urls";
import { useUserStore } from "@/util/userStore";
import { Icon } from "@iconify/vue";
import { Avatar, Button, Menu, Rating } from "primevue";
import type { MenuItem } from "primevue/menuitem";
import { onBeforeMount, onMounted, ref } from "vue";
import { RouterLink } from "vue-router";

const props = defineProps<{ review: ReviewResponse }>();

const userStore = useUserStore();

const menuItems = ref<MenuItem[]>([]);

const menuOpen = ref();

function getMenuItems() {
  if (userStore.role >= roles.moderator) {
    menuItems.value.push({
      label: "Set as featured",
      command: () => setAsFeatured(),
    });
  }
  if (userStore.role >= roles.moderator || userStore.username == props.review.reviewer.username) {
    menuItems.value.push(
      {
        label: "Edit",
        command: () => editReview(),
      },
      {
        separator: true,
      },
      {
        label: "Delete",
        command: () => deleteReview(),
      },
    );
  }
}


async function setAsFeatured() {
  try {
   api.post(ENDPOINTS.REVIEW_SET_FEATURED(props.review.content.id)); 
  } catch (error) {
    
  }
}

function removeFromFeatured() {
}

function editReview() {}

function deleteReview() {}

function toggle(event: MouseEvent) {
  menuOpen.value.toggle(event);
}

onMounted(() => {
  getMenuItems();
});
</script>

<template>
  <div class="flex flex-col md:grid md:grid-cols-[130px_3fr]">
    <div class="flex md:flex-col gap-1">
      <RouterLink
        :to="URLS.USER_PROFILE(review.reviewer.username)"
        class="w-fit h-fit block"
      >
        <Avatar
          :image="review.reviewer.avatarUrl ?? defautlAvatar"
          size="xlarge"
          shape="circle"
        />
      </RouterLink>
      <RouterLink
        :to="URLS.USER_PROFILE(review.reviewer.username)"
        class="hover:underline"
      >
        <span>{{ review.reviewer.displayName ?? review.reviewer.username }}</span>
      </RouterLink>
    </div>
    <div class="flex flex-col gap-2 justify-between items-start">
      <div class="flex items-center w-full">
        <Rating
          :stars="5"
          v-model="review.content.rating"
          readonly
        >
          <template #officon>
            <Icon
              icon="tdesign:star-filled"
              width="24"
              height="24"
              class="text-neutral-400"
            />
          </template>
          <template #onicon>
            <Icon
              icon="tdesign:star-filled"
              width="24"
              height="24"
              class="text-yellow-400"
            />
          </template>
        </Rating>
        <span class="ml-auto">{{ parseDate(review.content.createdAt) }}</span>
      </div>
      <p class="h-fit mb-5">{{ review.content.comment }}</p>
      <!-- <span v-if="review.content.likeCount">
        {{ review.content.likeCount }} like{{ review.content.likeCount > 1 ? "s" : "" }}
      </span>
      <Button label="Like" /> -->
      <Button
        severity="secondary"
        type="button"
        @click="toggle"
        class="p-1! bg-transparent! border-none!"
      >
        <Icon
          icon="material-symbols:more-horiz"
          width="24"
          height="24"
        />
      </Button>
      <Menu
        ref="menuOpen"
        :model="menuItems"
        :popup="true"
      ></Menu>
    </div>
  </div>
</template>
