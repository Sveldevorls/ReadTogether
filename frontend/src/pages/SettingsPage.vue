<script setup lang="ts">
// Todo: Form verification?
// Todo: Bind input to v-model
// Todo: Backend verificatio and response / patch
import { useUserStore } from "@/util/userStore";
import { Button, Divider, InputText, Textarea } from "primevue";
import { ref } from "vue";

const userStore = useUserStore();

const isEditingDisplayName = ref<boolean>(false);
const isEditingBio = ref<boolean>(false);
</script>

<template>
  <section class="w-[min(100%,60em)] p-4">
    <div class="flex flex-col gap-2 md:grid md:grid-cols-[1fr_3fr] relative">
      <span class="py-1">Display name</span>
      <span
        v-if="!isEditingDisplayName"
        class="py-1"
        :class="{ 'text-gray-500': userStore.displayName == null }"
      >
        {{ userStore.displayName ?? "Your display name" }}
      </span>
      <Button
        v-if="!isEditingDisplayName"
        severity="secondary"
        class="py-1! absolute! right-0"
        @click="isEditingDisplayName = true"
      >
        Edit
      </Button>
      <div
        v-if="isEditingDisplayName"
        class="flex flex-col gap-2"
      >
        <InputText
          type="text"
          id="displayName"
          class="w-[15em]"
        />
        <div class="flex gap-2">
          <Button
            severity="info"
            class="py-1!"
          >
            Save
          </Button>
          <Button
            severity="secondary"
            class="py-1!"
            @click="isEditingDisplayName = false"
          >
            Cancel
          </Button>
        </div>
      </div>
    </div>
    <Divider />
    <div class="flex flex-col gap-2 md:grid md:grid-cols-[1fr_3fr] relative">
      <span class="py-1">About me</span>
      <span
        v-if="!isEditingBio"
        class="py-1"
        :class="{ 'text-gray-500': userStore.bio == null }"
      >
        {{ userStore.bio ?? "Your about me" }}
      </span>
      <Button
        v-if="!isEditingBio"
        severity="secondary"
        class="py-1! absolute! right-0"
        @click="isEditingBio = true"
      >
        Edit
      </Button>
      <div
        v-if="isEditingBio"
        class="flex flex-col gap-2"
      >
        <Textarea
          autoResize
          type="text"
          id="displayName"
          rows="5"
        />
        <div class="flex gap-2">
          <Button
            severity="info"
            class="py-1!"
          >
            Save
          </Button>
          <Button
            severity="secondary"
            class="py-1!"
            @click="isEditingBio = false"
          >
            Cancel
          </Button>
        </div>
      </div>
    </div>
  </section>
</template>
