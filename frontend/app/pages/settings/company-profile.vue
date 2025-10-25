<script setup lang="ts">
import z from "zod";
import type { FormSubmitEvent } from "@nuxt/ui";
import type { Industry, PaginationResponse } from "~/types";

useHead({
  title: "Company Profile",
});

const { user } = useAuth();

// const toast = useToast();

const { data: industries } = await useAPI<PaginationResponse<Industry>>(
  "/api/industries",
  {
    method: "GET",
    query: {
      size: 1000,
    },
  }
);

const DESC_MAX_SIZE = 500;

const CompanyProfileSchema = z.object({
  name: z
    .string()
    .min(2, "Name must be at least 2 characters long")
    .max(100, "Name must be at most 100 characters long"),
  description: z
    .string()
    .min(8, "Description must be at least 8 characters long")
    .max(
      DESC_MAX_SIZE,
      `Description must be at most ${DESC_MAX_SIZE} characters long`
    ),

  email: z.email("Invalid email address"),
  website: z.url("Invalid website URL").optional(),
  industryId: z.number("Industry is required").int("Industry is required"),
  address: z.string().optional(),
  city: z.string().optional(),
  country: z.string().optional(),
});

const state = reactive<Partial<z.output<typeof CompanyProfileSchema>>>({
  email: user.value?.email || "",
  name: "",
  description: "",
  website: "",
  industryId: undefined,
  address: "",
  city: "",
  country: "",
});

async function onSubmit(
  event: FormSubmitEvent<z.output<typeof CompanyProfileSchema>>
) {
  console.log(event.data);
  // if (error.value) {
  //   toast.add({
  //     title: "Error updating profile",
  //     color: "error",
  //   });
  //   return;
  // }

  // toast.add({
  //   title: "Profile updated successfully",
  //   color: "success",
  // });
}
</script>

<template>
  <NuxtLayout name="settings">
    <UForm
      :schema="CompanyProfileSchema"
      :state="state"
      class="space-y-6"
      @submit="onSubmit"
    >
      <div class="flex flex-col gap-8 max-w-xl *:w-full">
        <SettingsFormHeader>
          When you post a job announcement as a company, the information from
          this profile will be used to display company details to potential
          applicants.
        </SettingsFormHeader>
        <UFormField label="Name" name="name">
          <UInput
            v-model="state.name"
            class="w-full"
            placeholder="Name of your company..."
          />
        </UFormField>
        <UFormField
          label="Email"
          name="email"
          description="Company contact email, visible to job applicants. Can be different from your user email."
        >
          <UInput v-model="state.email" class="w-full" />
        </UFormField>

        <UFormField
          label="Description"
          name="description"
          :description="
            state.description
              ? `You have used ${state.description.length} out of ${DESC_MAX_SIZE} characters.`
              : `Provide a brief description of your company (max ${DESC_MAX_SIZE} characters).`
          "
        >
          <UTextarea
            v-model="state.description"
            :rows="4"
            class="w-full"
            placeholder="Your company description..."
            :maxlength="DESC_MAX_SIZE"
          />
        </UFormField>

        <UFormField label="Website" name="website">
          <UInput
            v-model="state.website"
            class="w-full"
            placeholder="Provide your company website URL"
          />
        </UFormField>

        <UFormField label="Industry" name="industryId" class="w-full">
          <USelect
            v-model="state.industryId"
            class="w-full"
            :items="
              industries?.content.map(industry => ({
                label: industry.name,
                value: industry.id,
              }))
            "
            placeholder="Select Industry"
          />
        </UFormField>

        <SettingsFormSeparator>
          This is your company's address information. It can be different from
          the address you use in your job announcements.
        </SettingsFormSeparator>

        <UFormField label="Address" name="address">
          <UInput v-model="state.address" class="w-full" />
        </UFormField>
        <UFormField label="City" name="city">
          <UInput v-model="state.city" class="w-full" />
        </UFormField>
        <UFormField label="Country" name="country">
          <UInput v-model="state.country" class="w-full" />
        </UFormField>
        <UButton
          type="submit"
          class="px-8 max-h-fit self-end justify-center cursor-pointer"
        >
          Submit
        </UButton>
      </div>
    </UForm>
  </NuxtLayout>
</template>
