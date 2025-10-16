<script setup lang="ts">
import type { FormSubmitEvent } from "@nuxt/ui";
import z from "zod";

const loading = ref(false);
const success = ref(false);

const loginSchema = z.object({
  email: z.email("Invalid email address"),
  password: z.string().min(8, "Password must be at least 8 characters long"),
});

const state = reactive({
  email: "",
  password: "",
});

const onSubmit = async (
  event: FormSubmitEvent<z.output<typeof loginSchema>>
) => {
  console.log(event.data);
};
</script>

<template>
  <UForm
    class="min-w-full flex flex-col gap-4 items-center justify-center rounded-lg"
    :schema="loginSchema"
    :state="state"
    @submit="onSubmit"
  >
    <div class="flex flex-col gap-y-4 w-full mt-4">
      <UFormField name="email" class="w-full">
        <UInput
          v-model="state.email"
          trailing-icon="i-lucide-at-sign"
          placeholder="Email"
          class="w-full"
          size="xl"
          variant="subtle"
        />
      </UFormField>
      <UFormField name="password" class="w-full">
        <UInput
          v-model="state.password"
          trailing-icon="i-lucide-lock"
          placeholder="Password"
          class="w-full"
          size="xl"
          variant="subtle"
          type="password"
        />
      </UFormField>
      <UButton
        class="cursor-pointer h-10 justify-center px-8"
        size="lg"
        type="submit"
        label="Submit"
        :disabled="loading || success"
      />
    </div>
  </UForm>
</template>
