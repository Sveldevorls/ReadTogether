<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { ProfileUpdateResponse, SuccessResponse } from "@/util/responses";
import { useSingularToast } from "@/util/useSingularToast";
import { useUserStore } from "@/util/userStore";
import { Button, InputText, Message, useConfirm } from "primevue";
import { useField } from "vee-validate";
import { ref } from "vue";
import { string } from "yup";

const toast = useSingularToast();
const confirm = useConfirm();
const userStore = useUserStore();
const isEditing = ref<boolean>(false);
const { errors, meta, resetField, value } = useField<string | null>(
  "displayName",
  string()
    .max(30, "Display name must be at most 30 characters long")
    .matches(/^[a-zA-Z0-9_ ]*$/, "Display name can only contain letters, numbers, spaces, and underscores"),
  { initialValue: userStore.displayName },
);

function openEditor() {
  isEditing.value = true;
}

function closeEditor() {
  isEditing.value = false;
  resetField({
    value: value.value,
  });
}

async function submitDisplayName() {
  try {
    const { data: response } = await api.patch<SuccessResponse<ProfileUpdateResponse<"displayName">>>(
      ENDPOINTS.MY_PROFILE_UPDATE("displayName"),
      { displayName: value.value },
    );
    userStore.displayName = response.data.displayName;
    toast({
      severity: "success",
      summary: "Update complete",
      group: "message",
      life: 3000,
    });
  } catch (error) {
    toast({
      severity: "error",
      summary: "An unknown error had occurred. Please try again later.",
      group: "message",
      life: 3000,
    });
  }

  closeEditor();
}

function removeDisplayName() {
  confirm.require({
    message: "You are about to clear your display name. Are you sure you want to proceed?",
    header: "Confirmation",
    rejectProps: {
      label: "Cancel",
      severity: "secondary",
      outlined: true,
    },
    acceptProps: {
      label: "Remove",
      severity: "danger",
    },
    accept: () => {
      value.value = null;
      submitDisplayName();
    },
    reject: () => {
      closeEditor();
    },
  });
}
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
      @click="openEditor"
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
          @click="closeEditor"
        />
        <Button
          label="Save"
          severity="info"
          class="py-1! shrink-0"
          :disabled="!meta.dirty || !meta.valid || !value"
          @click="submitDisplayName"
        />
        <Button
          label="Remove display name"
          variant="link"
          severity="danger"
          class="p-0! ml-auto mr-2 text-red-500! self-end"
          @click="removeDisplayName"
        />
      </div>
    </div>
  </div>
</template>
