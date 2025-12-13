<script setup lang="ts">
import api from "@/util/api";
import { ENDPOINTS } from "@/util/endpoints";
import type { ProfileUpdateResponse, SuccessResponse } from "@/util/responses";
import { useSingularToast } from "@/util/useSingularToast";
import { useUserStore } from "@/util/userStore";
import { Button, Message, Textarea, useConfirm } from "primevue";
import { useField } from "vee-validate";
import { ref } from "vue";
import { string } from "yup";

const toast = useSingularToast();
const confirm = useConfirm();
const userStore = useUserStore();
const isEditing = ref<boolean>(false);
const { errors, meta, resetField, value } = useField<string | null>(
  "bio",
  string().max(500, "About me must be at most 500 characters long"),
  { initialValue: userStore.bio },
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

async function submitBio() {
  try {
    const { data: response } = await api.patch<SuccessResponse<ProfileUpdateResponse<"bio">>>(
      ENDPOINTS.MY_PROFILE_UPDATE("bio"),
      { bio: value.value },
    );
    userStore.bio = response.data.bio;
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

function removeBio() {
  confirm.require({
    message: "You are about to clear your about me. Are you sure you want to proceed?",
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
      submitBio();
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
      About me
    </span>
    <span
      v-if="!isEditing"
      class="py-1"
      :class="{ 'text-gray-500': userStore.bio == null }"
    >
      {{ userStore.bio ?? "Your about me" }}
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
      <Textarea
        :invalid="errors.length > 0"
        v-model="value"
        autoResize
        type="text"
        id="displayName"
        rows="5"
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
          class="py-1!"
          @click="closeEditor"
        />
        <Button
          label="Save"
          severity="info"
          class="py-1!"
          :disabled="!meta.dirty || !meta.valid || !value"
          @click="submitBio"
        />
        <Button
          label="Remove about me"
          variant="link"
          severity="danger"
          class="p-0! ml-auto mr-2 text-red-500! self-end"
          @click="removeBio"
        />
      </div>
    </div>
  </div>
</template>
