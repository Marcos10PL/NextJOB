<script setup lang="ts">
import type { FormSubmitEvent } from "@nuxt/ui";
import z from "zod";

const loading = ref(false);
const success = ref(false);

const registerSchema = z
  .object({
    full_name: z
      .string()
      .min(2, "Full name must be at least 2 characters long"),
    email: z.email("Invalid email address"),
    password: z.string().min(8, "Password must be at least 8 characters long"),
    confirm_password: z
      .string()
      .min(8, "Confirm password must be at least 8 characters long"),
  })
  .refine(data => data.password === data.confirm_password, {
    message: "Passwords do not match",
    path: ["confirm_password"],
  });

const state = reactive({
  email: "",
  password: "",
  full_name: "",
  confirm_password: "",
});

const onSubmit = async (
  event: FormSubmitEvent<z.output<typeof registerSchema>>
) => {
  console.log(event.data);
};
</script>

<template>
  <UForm
    class="min-w-full flex flex-col gap-4 items-center justify-center rounded-lg"
    :schema="registerSchema"
    :state="state"
    @submit="onSubmit"
  >
    <div class="flex flex-col gap-y-4 w-full mt-4">
      <UFormField name="full_name" class="w-full">
        <UInput
          v-model="state.full_name"
          trailing-icon="i-lucide-user"
          placeholder="Full Name"
          class="w-full"
          size="xl"
          variant="subtle"
        />
      </UFormField>
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
      <UFormField name="confirm_password" class="w-full">
        <UInput
          v-model="state.confirm_password"
          trailing-icon="i-lucide-lock"
          placeholder="Confirm Password"
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
