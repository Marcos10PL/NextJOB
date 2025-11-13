<script setup lang="ts">
import z from "zod";
import type { FormSubmitEvent } from "@nuxt/ui";
import type { Company, Industry, PaginationResponse } from "~/types";
import deepEqual from "fast-deep-equal";

useHead({
  title: "Company Profile",
});

const { user } = useAuth();

const toast = useToast();

const { data: industries, status: industriesStatus } = await useAPI<
  PaginationResponse<Industry>
>("/api/industries", {
  method: "GET",
  query: {
    size: 1000,
  },
});

const { data: companyProfile, status: companyProfileStatus } = await useAPI<
  Company[]
>("/api/companies", {
  method: "GET",
  query: {
    ownerId: user.value?.id,
  },
});

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
  website: z.url("Invalid website URL").or(z.literal("")),
  industryId: z.number("Industry is required").int("Industry is required"),
  
  address: z.string().optional(),
  city: z.string().optional(),
  country: z.string().optional(),
});

const company = computed(() => companyProfile.value?.[0] || null);
const isCompanyExists = ref(!!company.value);

const state = reactive<Partial<z.output<typeof CompanyProfileSchema>>>({
  email: company.value?.email || user.value?.email || "",
  name: company.value?.name || "",
  description: company.value?.description || "",
  website: company.value?.website || "",
  industryId: company.value?.industryId || undefined,
  address: company.value?.address || "",
  city: company.value?.city || "",
  country: company.value?.country || "",
});

watch(
  company,
  newVal => {
    isCompanyExists.value = !!newVal;
    Object.assign(state, newVal);
  },
  { immediate: true }
);

const loading = ref(false);

async function onSubmit(
  event: FormSubmitEvent<z.output<typeof CompanyProfileSchema>>
) {
  const hasChanges = !deepEqual(event.data, company.value);

  if (!hasChanges) {
    toast.add({
      title: "No changes to update",
      color: "info",
    });
    return;
  }

  if (!isCompanyExists.value) {
    loading.value = true;
    const { data, error } = await useAPI<Company>("/api/companies", {
      method: "POST",
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
      Object.assign(state, data.value);
      companyProfile.value = [data.value];
      isCompanyExists.value = true;
    }
  } else {
    loading.value = true;
    const { data, error } = await useAPI<Company>(`/api/companies/me`, {
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
      Object.assign(state, data.value);
      companyProfile.value = [data.value];
    }
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
    <UForm
      :schema="CompanyProfileSchema"
      :state="state"
      class="space-y-6"
      @submit="onSubmit"
    >
      <div class="flex flex-col gap-8 max-w-xl *:w-full">
        <FormsHeader>
          When you post a job announcement as a company, the information from
          this profile will be used to display company details to potential
          applicants.
        </FormsHeader>
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

        <FormsTextarea
          v-model="state.description"
          :max-size="DESC_MAX_SIZE"
          :rows="5"
          label="Description"
          name="description"
          placeholder="Your company description..."
          description="Provide a brief description of your company."
        />

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

        <FormsSeparator>
          This is your company's address information. It can be different from
          the address you use in your job announcements.
        </FormsSeparator>

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
          :disabled="
            industriesStatus === 'pending' ||
            companyProfileStatus === 'pending' ||
            loading
          "
        >
          {{ isCompanyExists ? "Update Profile" : "Create Profile" }}
        </UButton>
      </div>
    </UForm>
  </NuxtLayout>
</template>
