<script setup lang="ts">
import { useUserStore } from "@/util/userStore";
import { Button, Message, Textarea } from "primevue";
import { useField } from "vee-validate";
import { ref } from "vue";
import { string } from "yup";

const userStore = useUserStore();
const isEditing = ref<boolean>(false);
const { value, meta, errors } = useField<string | null>(
  "bio",
  string().max(500, "About me must be at most 500 characters long"),
  { initialValue: userStore.bio },
);
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
      @click="isEditing = true"
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
          @click="isEditing = false"
        />
        <Button
          label="Save"
          severity="info"
          class="py-1!"
          :disabled="!meta.dirty || !meta.valid || !value"
        />
        <Button
          label="Remove about me"
          variant="link"
          severity="danger"
          class="p-0! ml-auto mr-2 text-red-500! self-end"
        />
      </div>
    </div>
  </div>
</template>
