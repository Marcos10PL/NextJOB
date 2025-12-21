<script setup lang="ts">
import type { JobAnnouncementDetailsResponse } from "~/types";

const route = useRoute();

const { data } = await useAPI<JobAnnouncementDetailsResponse>(
  `/api/job-announcements/${route.params.id}`
);

const annAddress = computed(() => {
  if (!data.value) {
    return null;
  }

  const announcement = data.value;

  if (!announcement.address && !announcement.city && !announcement.country) {
    return null;
  }

  return `${announcement.address}, ${announcement.city}, ${announcement.country}`;
});

const tiles = computed(() => {
  let arr: string[] = [];

  if (!data.value) {
    return arr;
  }

  if (annAddress.value) {
    arr.push(annAddress.value);
  }

  arr = [
    ...arr,

    mapSalaryRange(
      data.value.salaryMin,
      data.value.salaryMax,
      data.value.paymentTypeName
    ),
    contractTypeLabels[data.value.contractTypeName] as string,

    workloadTypeLabels[data.value.workloadTypeName] as string,

    workModeLabels[data.value.workModeName as keyof typeof workModeLabels],

    data.value.isCvRequired ? "CV Required" : "CV Not Required",
  ];

  return arr;
});

const authorAddress = computed(() => {
  if (!data.value) {
    return null;
  }

  const author = data.value.author;
  const company = data.value.company;

  if (author?.address && author?.city && author?.country) {
    return `${author.address}, ${author.city}, ${author.country}`;
  } else if (company?.address && company?.city && company?.country) {
    return `${company.address}, ${company.city}, ${company.country}`;
  }

  return null;
});
</script>

<template>
  <div v-if="!data">
    <BackButton label="Back to announcements" />
    <p>Announcement not found.</p>
  </div>

  <div v-else class="flex flex-col gap-4">
    <BackButton label="Back to announcements" />
    <div
      class="flex flex-col gap-4 py-3 border-l-2 px-4 dark:bg-gray-800/50 bg-gray-200/50 border-gray-300 dark:border-gray-600 rounded-e-md"
    >
      <div class="flex items-center justify-between gap-4">
        <AnnouncementsElementsIndustryBadge
          :industry-name="data.industryName"
          size="xl"
        />
        <p>
          Last updated:
          <span class="font-semibold">
            {{ new Date(data.updatedAt).toLocaleDateString() }}
          </span>
        </p>
      </div>

      <AnnouncementsElementsTiles :tiles="tiles" size="md" />
    </div>

    <div
      class="flex flex-wrap items-center gap-4 border-l-2 px-4 dark:bg-blue-950/30 bg-blue-200/30 border-blue-300 dark:border-blue-800/60 rounded-e-md py-3"
    >
      <AnnouncementsElementsAuthorBadge
        :company-name="data.company?.name || null"
        :author-name="data.author?.fullName || null"
        size="xl"
      />
      <div
        class="flex items-center gap-1 py-1 px-2 rounded-md border border-gray-300 dark:border-gray-600"
      >
        <UIcon name="material-symbols:mail-outline-rounded" />
        {{ data.author?.email }}
      </div>
      <div
        v-if="authorAddress"
        class="flex items-center gap-1 bg-gray-100 dark:bg-gray-800 py-1 px-2 rounded-md border border-gray-300 dark:border-gray-600"
      >
        <UIcon name="material-symbols:location-on-outline" class="shrink-0" />
        {{ authorAddress }}
      </div>
    </div>

    <div
      class="flex flex-col gap-4 border-l-2 px-4 border-black dark:border-white/60 rounded-e-md py-3"
    >
      <h1 class="text-3xl font-bold">{{ data?.title }}</h1>

      <p class="whitespace-pre-line">{{ data?.description }}</p>
    </div>
  </div>
</template>
