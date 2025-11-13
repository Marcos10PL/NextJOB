<script setup lang="ts">
import z from "zod";
import type { Company, Industry, PaginationResponse } from "~/types";
import type { FormSubmitEvent } from "@nuxt/ui";

useHead({
  title: "Post a Job",
});

const { user } = useAuth();

const { data: companyProfile } = await useAPI<Company[]>("/api/companies", {
  method: "GET",
  query: {
    ownerId: user.value?.id,
  },
});

const { data: industries } = await useAPI<PaginationResponse<Industry>>(
  "/api/industries",
  {
    method: "GET",
    query: {
      size: 1000,
    },
  }
);

const company = computed(() => companyProfile.value?.[0] || null);

const DESC_MAX_SIZE = 500;

const postJobSchema = z
  .object({
    asCompany: z.boolean(),
    title: z.string().min(5, "Title must be at least 5 characters long"),
    description: z
      .string()
      .min(8, "Description must be at least 8 characters long")
      .max(
        DESC_MAX_SIZE,
        `Description must be at most ${DESC_MAX_SIZE} characters long`
      ),

    salary_min: z
      .number()
      .min(0, "Minimum salary cannot be negative")
      .optional(),
    salary_max: z
      .number()
      .min(0, "Maximum salary cannot be negative")
      .optional(),

    industryId: z.number("Industry is required").int("Industry is required"),

    is_cv_required: z.boolean(),

    address: z.string().optional(),
    city: z.string().optional(),
    country: z.string().optional(),
  })
  .refine(
    data =>
      data.salary_min === undefined ||
      data.salary_max === undefined ||
      data.salary_min <= data.salary_max,
    {
      message: "Minimum salary cannot be greater than maximum salary",
      path: ["salary_max"],
    }
  );

const state = reactive<Partial<z.output<typeof postJobSchema>>>({
  asCompany: !!company.value,
  title: "",
  description: "",

  salary_min: undefined,
  salary_max: undefined,

  industryId: undefined,

  is_cv_required: true,

  address: company.value?.address || user.value?.address || "",
  city: company.value?.city || user.value?.city || "",
  country: company.value?.country || user.value?.country || "",
});

watch(
  () => state.asCompany,
  asCompany => {
    if (asCompany) {
      state.address = company.value?.address || user.value?.address || "";
      state.city = company.value?.city || user.value?.city || "";
      state.country = company.value?.country || user.value?.country || "";
    } else {
      state.address = user.value?.address || "";
      state.city = user.value?.city || "";
      state.country = user.value?.country || "";
    }
  }
);

async function onSubmit(
  event: FormSubmitEvent<z.output<typeof postJobSchema>>
) {
  console.log("Form submitted:", event.data);
}
</script>

<template>
  <div class="max-w-4xl">
    <header class="mb-8">
      <h1 class="text-2xl font-semibold">Create a job announcement</h1>

      <p class="my-2 dark:text-gray-400 text-gray-600">
        Fill out the form below to create a new job announcement. Make sure to
        provide accurate and detailed information to attract the best
        candidates.
      </p>
    </header>

    <UForm
      :schema="postJobSchema"
      :state="state"
      class="space-y-6"
      @submit="onSubmit"
    >
      <USwitch
        v-model="state.asCompany"
        :label="`Post as ${state.asCompany ? 'a company' : 'an individual'}`"
        class="mb-8"
        size="xl"
      />

      <USwitch
        v-model="state.is_cv_required"
        :label="`CV is ${state.is_cv_required ? 'required' : 'not required'}`"
        class="mb-8"
        size="xl"
      />

      <UFormField label="Title" name="title" size="xl">
        <UInput
          v-model="state.title"
          class="w-full"
          placeholder="Title of the job announcement..."
        />
      </UFormField>

      <FormsTextarea
        v-model="state.description"
        :max-size="DESC_MAX_SIZE"
        :rows="5"
        label="Description"
        name="description"
        placeholder="Provide a detailed description of the job..."
        description="Describe the responsibilities, requirements, and benefits of the job."
        size="xl"
      />

      <!-- TO DO: workload, contract_type, ... -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <UFormField size="xl">
          <USelect class="w-full" />
        </UFormField>
        <UFormField size="xl">
          <USelect class="w-full" />
        </UFormField>
        <UFormField size="xl">
          <USelect class="w-full" />
        </UFormField>
        <UFormField size="xl">
          <USelect class="w-full" />
        </UFormField>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <FormsNumberField
          v-model="state.salary_min"
          label="Minimum Salary"
          name="salary_min"
          :step="5"
          :step-snapping="false"
          :is-currency="true"
          size="xl"
          placeholder="e.g., 300"
        />

        <FormsNumberField
          v-model="state.salary_max"
          label="Maximum Salary"
          name="salary_max"
          :step="5"
          :step-snapping="false"
          :is-currency="true"
          size="xl"
          placeholder="e.g., 500"
        />
      </div>

      <UFormField label="Industry" name="industryId" class="w-full" size="xl">
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

      <FormsSeparator class="!text-base">
        The location has been loaded from your
        {{ state.asCompany ? "company" : "personal" }} profile. You can edit it,
        or leave it blank if the job is remote.
      </FormsSeparator>

      <UFormField label="Address" name="address" size="xl">
        <UInput v-model="state.address" class="w-full" />
      </UFormField>
      <UFormField label="City" name="city" size="xl">
        <UInput v-model="state.city" class="w-full" />
      </UFormField>
      <UFormField label="Country" name="country" size="xl">
        <UInput v-model="state.country" class="w-full" />
      </UFormField>

      <UButton type="submit"> Create </UButton>
    </UForm>
  </div>
</template>
