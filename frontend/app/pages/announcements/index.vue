<script setup lang="ts">
import {
  type PaginationResponse,
  type JobAnnouncementListItem,
  type JobAnnouncementFilter,
  type JobAnnouncementFilterDebounced,
  JobSort,
} from "~/types";

// refs
const route = useRoute();
const router = useRouter();

const page = ref(route.query.page ? Number(route.query.page) : 1);
const limit = 10;

const debouncedFilters = reactive<JobAnnouncementFilterDebounced>({
  title: (route.query.title as string) || undefined,
  city: (route.query.city as string) || undefined,
  country: (route.query.country as string) || undefined,
  salaryMin: route.query.salaryMin ? Number(route.query.salaryMin) : undefined,
  salaryMax: route.query.salaryMax ? Number(route.query.salaryMax) : undefined,
});

const debouncedFiltersTimeout = ref<JobAnnouncementFilterDebounced>({
  ...debouncedFilters,
});

const filters = reactive<JobAnnouncementFilter>({
  industryIds: route.query.industryIds
    ? String(route.query.industryIds).split(",")
    : [],
  contractTypeIds: route.query.contractTypeIds
    ? String(route.query.contractTypeIds).split(",")
    : [],
  workloadTypeIds: route.query.workloadTypeIds
    ? String(route.query.workloadTypeIds).split(",")
    : [],
  paymentTypeIds: route.query.paymentTypeIds
    ? String(route.query.paymentTypeIds).split(",")
    : [],
  workModeIds: route.query.workModeIds
    ? String(route.query.workModeIds).split(",")
    : [],
  isCvRequired:
    route.query.isCvRequired === "true"
      ? true
      : route.query.isCvRequired === "false"
      ? false
      : undefined,
});

const sortOptions: { label: string; value: JobSort }[] = [
  { label: "Newest", value: JobSort.CREATED_AT_DESC },
  { label: "Oldest", value: JobSort.CREATED_AT_ASC },
  { label: "Recently updated", value: JobSort.UPDATED_AT_DESC },
  { label: "Least recently updated", value: JobSort.UPDATED_AT_ASC },
] as const;

const sort = ref<JobSort>(
  (route.query.sort as JobSort) || JobSort.CREATED_AT_DESC
);

const showFiltersModal = ref(false);

// query params watcher
watch(
  [filters, debouncedFilters, sort, page],
  () => {
    const query: Record<string, string | string[] | undefined> = {};

    if (filters.industryIds?.length)
      query.industryIds = filters.industryIds.map(String);
    if (filters.contractTypeIds?.length)
      query.contractTypeIds = filters.contractTypeIds.map(String);
    if (filters.workloadTypeIds?.length)
      query.workloadTypeIds = filters.workloadTypeIds.map(String);
    if (filters.paymentTypeIds?.length)
      query.paymentTypeIds = filters.paymentTypeIds.map(String);
    if (filters.workModeIds?.length)
      query.workModeIds = filters.workModeIds.map(String);

    if (filters.isCvRequired !== undefined)
      query.isCvRequired = String(filters.isCvRequired);

    if (debouncedFilters.title) query.title = debouncedFilters.title;
    if (debouncedFilters.city) query.city = debouncedFilters.city;
    if (debouncedFilters.country) query.country = debouncedFilters.country;
    if (debouncedFilters.salaryMin !== undefined)
      query.salaryMin = String(debouncedFilters.salaryMin);
    if (debouncedFilters.salaryMax !== undefined)
      query.salaryMax = String(debouncedFilters.salaryMax);

    query.sort = sort.value;
    query.page = String(page.value);

    router.replace({ query });
  },
  { deep: true }
);

// API call
const { data, pending } = await useAPI<
  PaginationResponse<JobAnnouncementListItem>
>("/api/job-announcements", {
  query: computed(() => ({
    page: page.value - 1,
    size: limit,
    ...filters,
    ...debouncedFiltersTimeout.value,
    sort: sort.value,
  })),
});

// uptate filters methods
const reset = () => {
  (Object.keys(filters) as (keyof JobAnnouncementFilter)[]).forEach(key => {
    filters[key] = undefined;
  });
  (
    Object.keys(debouncedFilters) as (keyof JobAnnouncementFilterDebounced)[]
  ).forEach(key => {
    debouncedFilters[key] = undefined;
  });
};

// watchers
let timeout: ReturnType<typeof setTimeout>;

watch(
  () => debouncedFilters,
  () => {
    clearTimeout(timeout);
    timeout = setTimeout(() => {
      debouncedFiltersTimeout.value = { ...debouncedFilters };
      page.value = 1;
    }, 500);
  },
  { deep: true }
);

watch(
  () => filters,
  () => {
    page.value = 1;
  },
  { deep: true }
);

// provides
provide("filters", filters);
provide("debouncedFilters", debouncedFilters);
</script>

<template>
  <div>
    <div class="grid grid-cols-1 lg:grid-cols-[30%_70%] gap-4">
      <AnnouncementsFilters
        class="hidden lg:block"
        :filters="filters"
        :debounced-filters="debouncedFilters"
        @reset="reset"
      />

      <div class="flex flex-col gap-4">
        <AnnouncementsModalFilters
          v-model:open="showFiltersModal"
          class="lg:hidden"
          :filters="filters"
          :debounced-filters="debouncedFilters"
          :total="data?.page.totalElements || 0"
          @reset="reset"
        />
        <div
          class="flex flex-col-reverse md:flex-row md:items-center justify-between gap-4"
        >
          <h2 class="font-semibold pl-0.5">
            Jobs - {{ data?.page.totalElements || 0 }} offerts found
          </h2>
          <USelect
            v-model="sort"
            :items="sortOptions"
            label="Sort by"
            hide-details
            dense
            class="flex-1 md:max-w-48"
          />
        </div>

        <template v-if="pending">
          <SkeletonsCard v-for="n in limit" :key="n" />
        </template>

        <template v-else-if="data && data.content.length === 0">
          <div class="text-gray-500 dark:text-gray-400">
            No job announcements found.
          </div>
        </template>

        <template v-else>
          <AnnouncementsCard
            v-for="jobAnn in data?.content"
            :key="jobAnn.id"
            :job-ann="jobAnn"
          />
        </template>
      </div>
    </div>
    <div class="w-full flex justify-center my-4">
      <UPagination
        v-model:page="page"
        :total="data?.page.totalElements"
        :items-per-page="limit"
      />
    </div>
  </div>
</template>
