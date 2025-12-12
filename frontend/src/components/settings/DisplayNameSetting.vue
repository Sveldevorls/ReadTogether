<script setup lang="ts">
import { useUserStore } from "@/util/userStore";
import { Button, InputText, Message } from "primevue";
import { useField } from "vee-validate";
import { ref } from "vue";
import { string } from "yup";

const userStore = useUserStore();
const isEditing = ref<boolean>(false);
const { value, meta, errors } = useField<string | null>(
  "displayName",
  string()
    .max(30, "Display name must be at most 30 characters long")
    .matches(/^[a-zA-Z0-9_ ]*$/, "Display name can only contain letters, numbers, spaces, and underscores"),
  { initialValue: userStore.displayName },
);
</script>

<template>
  <div class="flex flex-col gap-2 sm:items-center sm:grid sm:grid-cols-[1fr_3fr] relative">
    <span
      class="py-1"
      :class="{ 'self-start': isEditing }"
    >
      Display name
    </span>
    <span
      v-if="!isEditing"
      class="py-1"
      :class="{ 'text-gray-500': userStore.displayName == null }"
    >
      {{ userStore.displayName ?? "Your display name" }}
    </span>
    <Button
      v-if="!isEditing"
      label="Edit"
      severity="secondary"
      class="py-1! absolute! right-0"
      @click="isEditing = true"
    />
    <div
      v-if="isEditing"
      class="flex flex-col gap-2"
    >
      <InputText
        :invalid="errors.length > 0"
        v-model="value"
        placeholder="Your display name"
        id="displayName"
        class="w-[15em]"
      />
      <Message
        v-for="error in errors"
        severity="error"
        variant="simple"
        size="small"
      >
        {{ error }}
      </Message>
      <div class="flex gap-2">
        <Button
          label="Cancel"
          severity="secondary"
          class="py-1! shrink-0"
          @click="isEditing = false"
        />
        <Button
          label="Save"
          severity="info"
          class="py-1! shrink-0"
          :disabled="!meta.dirty || !meta.valid || !value"
        />
        <Button
          label="Remove display name"
          variant="link"
          severity="danger"
          class="p-0! ml-auto mr-2 text-red-500! self-end"
        />
      </div>
    </div>
  </div>
</template>
