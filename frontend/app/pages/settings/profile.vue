<script setup lang="ts">
import z from "zod";
import type { FormSubmitEvent } from "@nuxt/ui";

useHead({
  title: "Profile",
});

// const toast = useToast();

const ProfileSchema = z.object({
  email: z.email("Invalid email address"),
  full_name: z.string().min(2, "Full name must be at least 2 characters long"),

  address: z.string().optional(),
  city: z.string().optional(),
  country: z.string().optional(),
});

const state = reactive<Partial<z.output<typeof ProfileSchema>>>({
  email: "",
  full_name: "",
  address: undefined,
  city: undefined,
  country: undefined,
});

async function onSubmit(
  event: FormSubmitEvent<z.output<typeof ProfileSchema>>
) {
  console.log(event.data);
}
</script>

<template>
  <NuxtLayout name="settings">
    <UForm
      :schema="ProfileSchema"
      :state="state"
      class="space-y-6"
      @submit="onSubmit"
    >
      <div
        class="grid grid-cols-1 md:grid-cols-2 gap-8 *:w-full *:**:w-full max-w-xl"
      >
        <UFormField label="Email" name="email">
          <UInput v-model="state.email" />
        </UFormField>
        <UFormField label="Full Name" name="full_name">
          <UInput v-model="state.full_name" />
        </UFormField>

        <UFormField label="Address" name="address">
          <UInput v-model="state.address" />
        </UFormField>
        <UFormField label="City" name="city">
          <UInput v-model="state.city" />
        </UFormField>
        <UFormField label="Country" name="country">
          <UInput v-model="state.country" />
        </UFormField>
        <UButton type="submit" class="px-8 max-h-fit self-end justify-center">
          Submit
        </UButton>
      </div>
    </UForm>
  </NuxtLayout>
</template>
