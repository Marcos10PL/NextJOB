<script setup lang="ts">
import type {
  ContractType,
  Industry,
  JobAnnouncementFilter,
  JobAnnouncementFilterDebounced,
  PaginationResponse,
  PaymentType,
  WorkloadType,
  WorkMode,
} from "~/types";
import type { CheckboxGroupItem } from "@nuxt/ui";

const { header = true } = defineProps<{
  header?: boolean;
}>();

const { data: industries } = await useAPI<PaginationResponse<Industry>>(
  "/api/industries",
  {
    method: "GET",
    query: {
      size: 1000,
    },
    lazy: true,
  }
);

const { data: contractTypes } = await useAPI<ContractType[]>(
  "/api/contract-types"
);

const { data: workloadTypes } = await useAPI<WorkloadType[]>(
  "/api/workload-types"
);

const { data: workModeTypes } = await useAPI<WorkMode[]>("/api/work-modes", {});

const { data: paymentTypes } = await useAPI<PaymentType[]>(
  "/api/payment-types"
);

const emit = defineEmits(["reset"]);

const filters = inject("filters") as JobAnnouncementFilter;
const debouncedFilters = inject(
  "debouncedFilters"
) as JobAnnouncementFilterDebounced;

const addIndustryFilter = (industryId: number) => {
  if (!filters.industryIds) {
    filters.industryIds = [];
  }

  if (!filters.industryIds.includes(String(industryId))) {
    filters.industryIds.push(String(industryId));
  } else {
    filters.industryIds = filters.industryIds.filter(
      id => id !== String(industryId)
    );
  }
};

const contractTypesItems = computed<CheckboxGroupItem[]>(() => [
  ...(contractTypes?.value?.map(ct => ({
    label: contractTypeLabels[ct.name as keyof typeof contractTypeLabels],
    value: String(ct.id),
  })) || []),
]);

const workloadTypesItems = computed<CheckboxGroupItem[]>(() => [
  ...(workloadTypes?.value?.map(wt => ({
    label: workloadTypeLabels[wt.name as keyof typeof workloadTypeLabels],
    value: String(wt.id),
  })) || []),
]);

const paymentTypesItems = computed<CheckboxGroupItem[]>(() => [
  ...(paymentTypes?.value?.map(pt => ({
    label: paymentTypeLabels[pt.name as keyof typeof paymentTypeLabels],
    value: String(pt.id),
  })) || []),
]);

const workModeTypesItems = computed<CheckboxGroupItem[]>(() => [
  ...(workModeTypes?.value?.map(wm => ({
    label: workModeLabels[wm.name as keyof typeof workModeLabels],
    value: String(wm.id),
  })) || []),
]);

const cvRequiredItems = [
  { label: "All", value: undefined },
  { label: "CV required", value: true },
  { label: "CV not required", value: false },
];
</script>

<template>
  <UCard
    variant="soft"
    class="border border-gray-300 dark:border-gray-700 shadow-md"
    :ui="{
      header: 'flex justify-between items-center gap-2 ',
      body: 'flex flex-col gap-4 *:border-b *:border-gray-300 *:dark:border-gray-600 *:pb-4 *:last:border-0 *:last:pb-0',
    }"
  >
    <template v-if="header !== false" #header>
      <h2 class="font-semibold">Filters</h2>
      <UButton
        variant="link"
        class="underline cursor-pointer"
        @click="() => emit('reset')"
      >
        Reset
      </UButton>
    </template>

    <div class="flex flex-col gap-2">
      <UInput
        v-model="debouncedFilters.title"
        icon="i-lucide-search"
        class="w-full"
        size="md"
        variant="outline"
        placeholder="Search by title..."
      />
      <UInput
        v-model="debouncedFilters.country"
        icon="i-lucide-search"
        class="w-full"
        size="md"
        variant="outline"
        placeholder="Search by country..."
      />
      <UInput
        v-model="debouncedFilters.city"
        icon="i-lucide-search"
        class="w-full"
        size="md"
        variant="outline"
        placeholder="Search by city..."
      />
    </div>

    <div class="flex flex-wrap gap-2">
      <div
        v-for="industry in industries?.content"
        :key="industry.id"
        class="px-2 py-1 border border-gray-300 dark:border-gray-600 rounded-lg cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600 transition-colors"
        :class="{
          'bg-gray-200 dark:bg-gray-700 ': filters.industryIds?.includes(
            String(industry.id)
          ),
          ' ': !filters.industryIds?.includes(String(industry.id)),
        }"
        @click="addIndustryFilter(industry.id)"
      >
        {{ industry.name }}
      </div>
    </div>

    <URadioGroup v-model="filters.isCvRequired" :items="cvRequiredItems" />

    <div class="grid grid-cols-1 gap-2">
      <FormsNumberField
        v-model="debouncedFilters.salaryMin"
        name="salaryMin"
        :step="100"
        :step-snapping="false"
        :is-currency="true"
        placeholder="Salary min..."
      />

      <FormsNumberField
        v-model="debouncedFilters.salaryMax"
        name="salaryMax"
        :step="100"
        :step-snapping="false"
        :is-currency="true"
        placeholder="Salary max..."
      />

      <UButton
        variant="subtle"
        color="neutral"
        size="sm"
        class="justify-center"
        @click="
          (debouncedFilters.salaryMin = undefined),
            (debouncedFilters.salaryMax = undefined)
        "
      >
        Clear salary filter
      </UButton>
    </div>

    <UCheckboxGroup
      v-model="filters.paymentTypeIds"
      label="Payment Types"
      :items="paymentTypesItems"
    />

    <UCheckboxGroup
      v-model="filters.contractTypeIds"
      label="Contract Types"
      :items="contractTypesItems"
    />

    <UCheckboxGroup
      v-model="filters.workloadTypeIds"
      label="Workload Types"
      :items="workloadTypesItems"
    />

    <UCheckboxGroup
      v-model="filters.workModeIds"
      label="Work Modes"
      :items="workModeTypesItems"
    />
  </UCard>
</template>
