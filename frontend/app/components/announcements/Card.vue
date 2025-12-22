<script setup lang="ts">
import {
  contractTypeLabels,
  workloadTypeLabels,
  workModeLabels,
} from "~/constants";
import type { JobAnnouncementListItem, WorkModeEnum } from "~/types";

const { jobAnn } = defineProps<{
  jobAnn: JobAnnouncementListItem;
}>();

const bodyInfo = computed(() => [
  mapSalaryRange(jobAnn.salaryMin, jobAnn.salaryMax, jobAnn.paymentType),

  contractTypeLabels[jobAnn.contractType] as string,

  workloadTypeLabels[jobAnn.workloadType] as string,

  workModeLabels[jobAnn.workMode as WorkModeEnum],
]);
</script>

<template>
  <UCard
    variant="soft"
    class="border border-gray-300 dark:border-gray-700 shadow-md hover:border-gray-400 transition-colors hover:shadow-md cursor-pointer"
    :ui="{
      header: 'font-semibold text-lg flex flex-col max-w-full gap-2',
      footer:
        'text-sm text-gray-400 flex flex-wrap items-center justify-between gap-x-4 gap-y-2',
      body: '!py-2',
    }"
    @click="$router.push(`/announcements/${jobAnn.id}`)"
  >
    <template #header>
      <h2 class="line-clamp-2 max-w-fit">
        {{ jobAnn.title }}
      </h2>
      <div class="flex items-center flex-wrap gap-2">
        <AnnouncementsElementsIndustryBadge
          :industry-name="jobAnn.industryName"
        />
        <AnnouncementsElementsAuthorBadge
          :company-name="jobAnn.companyName"
          :author-name="jobAnn.authorName"
        />
      </div>
    </template>

    <AnnouncementsElementsTiles :tiles="bodyInfo" />

    <template #footer>
      <div v-if="jobAnn.city || jobAnn.country" class="flex items-center gap-1">
        <UIcon name="material-symbols:location-on-outline" class="shrink-0" />
        {{ jobAnn.city }}, {{ jobAnn.country }}
      </div>
      <div v-else class="flex items-center gap-1.5">
        <UIcon name="material-symbols:computer-outline" class="shrink-0" />
        <span> Remote work </span>
      </div>
      <div>
        Last updated:
        <span class="font-semibold">
          {{ new Date(jobAnn.updatedAt).toLocaleDateString() }}
        </span>
      </div>
    </template>
  </UCard>
</template>
