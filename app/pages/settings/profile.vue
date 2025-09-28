<script setup lang="ts">
import type z from "zod";
import { ProfileSchema } from "~/schemas";
import type { FormSubmitEvent } from "@nuxt/ui";

// TODO: Fetch profile data from API and populate the form

const toast = useToast();

const { data, error } = await useFetch("/api/profiles/me", {
  headers: useRequestHeaders(["cookie"]),
});

const state = reactive<Partial<z.output<typeof ProfileSchema>>>({
  email: undefined,
  full_name: undefined,
  phone: undefined,
  created_at: undefined,
  address: undefined,
  city: undefined,
  country: undefined,
});

async function onSubmit(
  event: FormSubmitEvent<z.output<typeof ProfileSchema>>
) {
  toast.add({
    title: "Success",
    description: "The form has been submitted.",
    color: "success",
  });
  console.log(event.data);
}
</script>

<template>
  <NuxtLayout name="settings">
    <UForm
      :schema="ProfileSchema"
      :state="state"
      class="space-y-4"
      @submit="onSubmit"
    >
      <div class="flex w-full gap-2 *:w-full *:**:w-full">
        <UFormField label="Email" name="email">
          <UInput v-model="state.email" />
        </UFormField>
        <UFormField label="Full Name" name="full_name">
          <UInput v-model="state.full_name" />
        </UFormField>
      </div>

      <UFormField label="Phone" name="phone">
        <UInput v-model="state.phone" />
      </UFormField>

      <UFormField label="Address" name="address">
        <UInput v-model="state.address" />
      </UFormField>

      <div class="flex w-full gap-2 *:w-full *:**:w-full">
        <UFormField label="City" name="city">
          <UInput v-model="state.city" />
        </UFormField>
        <UFormField label="Country" name="country">
          <UInput v-model="state.country" />
        </UFormField>
      </div>

      <UFormField label="Created At" name="created_at">
        <UInput v-model="state.created_at" disabled />
      </UFormField>

      <UButton type="submit"> Submit </UButton>
    </UForm>
  </NuxtLayout>
</template>
