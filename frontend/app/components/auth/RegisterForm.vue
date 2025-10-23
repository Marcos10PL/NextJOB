<script setup lang="ts">
import type { FormSubmitEvent } from "@nuxt/ui";
import z from "zod";
import type { FetchError } from "~/types";

const loading = ref(false);
const error = ref<number | null>(null);
const success = ref(false);

const toast = useToast();
const router = useRouter();

const registerSchema = z
  .object({
    fullName: z
      .string()
      .min(2, "Full name must be at least 2 characters long"),
    email: z.email("Invalid email address"),
    password: z.string().min(8, "Password must be at least 8 characters long"),
    confirmPassword: z
      .string()
      .min(8, "Confirm password must be at least 8 characters long"),
  })
  .refine(data => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });

const state = reactive({
  email: "",
  password: "",
  fullName: "",
  confirmPassword: "",
});

const onSubmit = async (
  event: FormSubmitEvent<z.output<typeof registerSchema>>
) => {
  try {
    loading.value = true;
    error.value = null;
    success.value = false;

    await $fetch("/api/auth/signup", {
      method: "POST",
      body: {
        ...event.data,
        confirmPassword: undefined,
      },
    });
    success.value = true;

    toast.add({ title: "Registration Successful. You can now log in." });
    router.push("/?tab=signin#control-active-item");
  } catch (err) {
    error.value = (err as FetchError).status || 500;
    // console.error(err);
  } finally {
    loading.value = false;
  }
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
      <UFormField name="fullName" class="w-full">
        <UInput
          v-model="state.fullName"
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
      <UFormField name="confirmPassword" class="w-full">
        <UInput
          v-model="state.confirmPassword"
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
    <div v-if="error" class="text-red-400 text-center">
      {{ mapError(error, { register: true }) }}
    </div>
  </UForm>
</template>
