<script setup lang="ts">
import type z from "zod";
import {
  WorkModeEnum,
  type Company,
  type ContractType,
  type Industry,
  type JobAnnouncement,
  type PaginationResponse,
  type PaymentType,
  type WorkloadType,
  type WorkMode,
} from "~/types";
import type { FormSubmitEvent } from "@nuxt/ui";
import { postJobSchema } from "~/schemas";
import {
  contractTypeLabels,
  paymentTypeLabels,
  POST_JOB_DESC_MAX_SIZE,
  workloadTypeLabels,
  workModeLabels,
} from "~/constants";

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

const { data: contractTypes } = await useAPI<ContractType[]>(
  "/api/contract-types",
  {
    method: "GET",
    query: {
      size: 1000,
    },
  }
);

const { data: workloadTypes } = await useAPI<WorkloadType[]>(
  "/api/workload-types",
  {
    method: "GET",
    query: {
      size: 1000,
    },
  }
);

const { data: workModeTypes } = await useAPI<WorkMode[]>("/api/work-modes", {
  method: "GET",
  query: {
    size: 1000,
  },
});

const { data: paymentTypes } = await useAPI<PaymentType[]>(
  "/api/payment-types",
  {
    method: "GET",
    query: {
      size: 1000,
    },
  }
);

const company = computed(() => companyProfile.value?.[0] || null);

const isRemote = computed(() => {
  const remote = workModeTypes?.value?.find(
    w => w.name === WorkModeEnum.REMOTE
  );

  return state.workModeId === remote?.id;
});

const schema = postJobSchema;

type Schema = z.output<typeof schema>;

const state = reactive<Partial<Schema>>({
  asCompany: false,
  title: "",
  description: "",

  salaryMin: undefined,
  salaryMax: undefined,

  industryId: undefined,

  isCvRequired: true,

  contractTypeId: undefined,
  workModeId: undefined,
  paymentTypeId: undefined,
  workloadTypeId: undefined,

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
      state.industryId = company.value?.industryId || undefined;
    } else {
      state.address = user.value?.address || "";
      state.city = user.value?.city || "";
      state.country = user.value?.country || "";
      state.industryId = undefined;
    }
  }
);

watch(
  company,
  newVal => {
    state.asCompany = !!newVal;
  },
  { immediate: true }
);

watch(isRemote, remote => {
  if (remote) {
    state.address = undefined;
    state.city = undefined;
    state.country = undefined;
  }
});

const toast = useToast();
const loading = ref(false);

async function onSubmit(event: FormSubmitEvent<Schema>) {
  if (!isRemote.value) {
    if (!state.address || !state.city || !state.country) {
      toast.add({
        title:
          "Location is required for non-remote jobs. Please fill it out or mark the job as remote.",
        color: "info",
      });
      return;
    }
  }

  loading.value = true;

  const { error } = await useAPI<JobAnnouncement>(`/api/job-announcements`, {
    method: "POST",
    body: {
      ...event.data,
      companyId: event.data.asCompany ? company.value?.id : null,
      salaryMin: event.data.salaryMin ? event.data.salaryMin * 100 : undefined,
      salaryMax: event.data.salaryMax ? event.data.salaryMax * 100 : undefined,
    },
  });

  if (error.value) {
    toast.add({
      title: "Error creating job announcement",
      color: "error",
    });
    loading.value = false;
    return;
  }

  toast.add({
    title: "Job announcement created successfully",
    color: "success",
  });
  loading.value = false;

  // Reset form
  Object.assign(state, {
    asCompany: !!company.value,
    title: "",
    description: "",

    salaryMin: undefined,
    salaryMax: undefined,

    industryId: company.value?.industryId || undefined,

    isCvRequired: true,

    contractTypeId: undefined,
    workModeId: undefined,
    paymentTypeId: undefined,
    workloadTypeId: undefined,

    address: company.value?.address || user.value?.address || "",
    city: company.value?.city || user.value?.city || "",
    country: company.value?.country || user.value?.country || "",
  });
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

    <UForm :schema="schema" :state="state" class="space-y-6" @submit="onSubmit">
      <USwitch
        v-model="state.asCompany"
        :disabled="!company"
        :label="`Post as ${state.asCompany ? 'a company' : 'an individual'}`"
        class="mb-4"
        size="xl"
      />

      <p v-if="!company" class="mb-8 text-gray-400">
        If you want to post this job announcement as a company, please create a
        company profile first.
      </p>

      <USwitch
        v-model="state.isCvRequired"
        :label="`CV is ${state.isCvRequired ? 'required' : 'not required'}`"
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
        :max-size="POST_JOB_DESC_MAX_SIZE"
        :rows="5"
        label="Description"
        name="description"
        placeholder="Provide a detailed description of the job..."
        description="Describe the responsibilities, requirements, and benefits of the job."
        size="xl"
      />

      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <UFormField
          v-if="workloadTypes"
          size="xl"
          label="Workload Type"
          name="workloadTypeId"
        >
          <USelect
            v-model="state.workloadTypeId"
            class="w-full"
            :items="mapToOptions(workloadTypes, workloadTypeLabels)"
          />
        </UFormField>
        <UFormField
          v-if="contractTypes"
          size="xl"
          label="Contract Type"
          name="contractTypeId"
        >
          <USelect
            v-model="state.contractTypeId"
            class="w-full"
            :items="mapToOptions(contractTypes, contractTypeLabels)"
          />
        </UFormField>
        <UFormField
          v-if="workModeTypes"
          size="xl"
          label="Work Mode"
          name="workModeId"
        >
          <USelect
            v-model="state.workModeId"
            class="w-full"
            :items="mapToOptions(workModeTypes, workModeLabels)"
          />
        </UFormField>
        <UFormField
          v-if="paymentTypes"
          size="xl"
          label="Payment"
          name="paymentTypeId"
        >
          <USelect
            v-model="state.paymentTypeId"
            class="w-full"
            :items="mapToOptions(paymentTypes, paymentTypeLabels)"
          />
        </UFormField>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <FormsNumberField
          v-model="state.salaryMin"
          label="Minimum Salary"
          name="salaryMin"
          :step="100"
          :step-snapping="false"
          :is-currency="true"
          size="xl"
          placeholder="e.g., 300"
        />

        <FormsNumberField
          v-model="state.salaryMax"
          label="Maximum Salary"
          name="salaryMax"
          :step="100"
          :step-snapping="false"
          :is-currency="true"
          size="xl"
          placeholder="e.g., 500"
        />
      </div>

      <UFormField
        label="Industry"
        name="industryId"
        class="w-full"
        size="xl"
        :description="
          state.asCompany && state.industryId !== company?.industryId
            ? 'Your company industry is different. Make sure this is correct.'
            : ''
        "
      >
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
        {{ state.asCompany ? "company" : "personal" }} profile (if available).
        Feel free to modify it.
        <br />
        Location is required unless the job is remote.
      </FormsSeparator>

      <UFormField label="Address" name="address" size="xl">
        <UInput v-model="state.address" class="w-full" :disabled="isRemote" />
      </UFormField>
      <UFormField label="City" name="city" size="xl">
        <UInput v-model="state.city" class="w-full" :disabled="isRemote" />
      </UFormField>
      <UFormField label="Country" name="country" size="xl">
        <UInput v-model="state.country" class="w-full" :disabled="isRemote" />
      </UFormField>

      <UButton type="submit"> Create </UButton>
    </UForm>
  </div>
</template>
