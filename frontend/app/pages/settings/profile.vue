<script setup lang="ts">
import type z from "zod";
import type { FormSubmitEvent } from "@nuxt/ui";
import type { User } from "~/types";
import { profileSchema } from "~/schemas";

useHead({
  title: "Profile",
});

const { user } = useAuth();

const toast = useToast();

const schema = profileSchema;

type Schema = z.output<typeof schema>;

const state = reactive<Partial<Schema>>({
  email: user.value?.email || "",
  fullName: user.value?.fullName || "",
  address: user.value?.address || "",
  city: user.value?.city || "",
  country: user.value?.country || "",
});

const loading = ref(false);

async function onSubmit(event: FormSubmitEvent<Schema>) {
  const hasChanges =
    event.data.email !== user.value?.email ||
    event.data.fullName !== user.value?.fullName ||
    event.data.address != user.value?.address ||
    event.data.city !== user.value?.city ||
    event.data.country !== user.value?.country;

  if (!hasChanges) {
    toast.add({
      title: "No changes to update",
      color: "info",
    });
    return;
  }

  loading.value = true;
  const { data, error } = await useAPI<User>("/api/users/me", {
    method: "PATCH",
    body: event.data,
  });

  if (error.value) {
    toast.add({
      title: "Error updating profile",
      color: "error",
    });
    loading.value = false;
    return;
  }

  if (data.value) {
    if (user.value) {
      Object.assign(user.value, data.value);
    }
    Object.assign(state, data.value);
  }

  toast.add({
    title: "Profile updated successfully",
    color: "success",
  });
  loading.value = false;
}
</script>

<template>
  <NuxtLayout name="settings">
    <UForm :schema="schema" :state="state" class="space-y-6" @submit="onSubmit">
      <div class="flex flex-col gap-8 *:w-full *:**:w-full max-w-xl">
        <FormsHeader>
          When you post a job announcement as a personal user, the information
          from this profile will be used to display your details to potential
          applicants.
        </FormsHeader>
        <UFormField label="Email" name="email">
          <UInput v-model="state.email" />
        </UFormField>
        <UFormField label="Full Name" name="fullName">
          <UInput v-model="state.fullName" />
        </UFormField>

        <FormsSeparator>
          This is your personal address information. It can be different from
          the address you use in your job announcements.
        </FormsSeparator>

        <UFormField label="Address" name="address">
          <UInput v-model="state.address" />
        </UFormField>
        <UFormField label="City" name="city">
          <UInput v-model="state.city" />
        </UFormField>
        <UFormField label="Country" name="country">
          <UInput v-model="state.country" />
        </UFormField>
        <UButton
          type="submit"
          class="px-8 max-h-fit self-end justify-center cursor-pointer"
          :disabled="loading"
        >
          Update
        </UButton>
      </div>
    </UForm>
  </NuxtLayout>
</template>
