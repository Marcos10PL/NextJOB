<script setup lang="ts">
import {
  contractTypeLabels,
  workloadTypeLabels,
  workModeLabels,
} from "~/constants";
import type { JobAnnouncementDetailsResponse } from "~/types";

const route = useRoute();

const { data } = await useAPI<JobAnnouncementDetailsResponse>(
  `/api/job-announcements/${route.params.id}`
);

const { isJobSeekerExists } = useJobSeeker();
const { checked, fetchApplications, applications } = useApplications();

const toast = useToast();

const isOwner = computed(() => {
  const { user } = useAuth();

  if (!data.value || !user.value) {
    return false;
  }

  return String(data.value.author?.id) === String(user.value.id);
});

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

  if (company?.address && company?.city && company?.country) {
    return `${company.address}, ${company.city}, ${company.country}`;
  } else {
    if (author?.address && author?.city && author?.country) {
      return `${author.address}, ${author.city}, ${author.country}`;
    }
  }

  return null;
});

const authorEmail = computed(() => {
  if (!data.value) {
    return null;
  }

  return data.value.company?.email || data.value.author?.email || null;
});

const websiteUrl = computed(() => {
  if (!data.value) {
    return null;
  }

  return data.value.company?.website || null;
});

const alreadyApplied = computed(() => {
  if (!data.value) {
    return false;
  }

  return applications.value.some(
    app => app.jobAnnouncementId === data.value!.id
  );
});

const loading = ref(false);

const apply = async () => {
  if (!data.value) {
    return;
  }

  if (isOwner.value) {
    toast.add({
      title: "You cannot apply to your own announcement!",
      color: "warning",
    });
    return;
  }

  console.log(isJobSeekerExists.value);

  if (!isJobSeekerExists.value) {
    toast.add({
      title:
        "Please create a job seeker profile before applying. You can do this in the settings section.",
      color: "info",
    });
    return;
  }

  try {
    loading.value = true;
    const { error } = await useAPI(
      `/api/applications/job-announcements/${data.value.id}`,
      {
        method: "POST",
      }
    );

    if (error.value) {
      throw error.value;
    }

    checked.value = false;
    await fetchApplications();

    toast.add({
      title: "Application submitted successfully!",
      color: "success",
    });
  } catch (err) {
    console.log(err);
    toast.add({
      title: "Something went wrong while submitting the application.",
      color: "error",
    });
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div v-if="!data" class="flex flex-col gap-4">
    <BackButton label="Back to announcements" />
    <p>Announcement not found.</p>
  </div>

  <div v-else class="flex flex-col gap-4">
    <div
      class="flex flex-col sm:flex-row sm:items-center justify-between gap-4"
    >
      <BackButton
        label="Back to announcements"
        class="min-w-full sm:min-w-fit"
      />
      <UButton
        class="cursor-pointer justify-center"
        :disabled="alreadyApplied"
        :loading="loading"
        @click="apply"
      >
        {{ alreadyApplied ? "Already applied!" : "Apply Now" }}
      </UButton>
    </div>
    <div
      class="flex flex-col gap-4 py-3 border-l-2 px-4 dark:bg-gray-800/50 bg-gray-200/50 border-gray-300 dark:border-gray-600 rounded-e-md"
    >
      <div class="flex items-center flex-wrap-reverse justify-between gap-4">
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
      <AnnouncementsElementsTile
        icon="material-symbols:mail-outline-rounded"
        :data="authorEmail"
      />
      <AnnouncementsElementsTile
        icon="material-symbols:location-on-outline"
        :data="authorAddress"
      />
      <AnnouncementsElementsTile
        icon="material-symbols:public-outline"
        :data="websiteUrl"
      >
        <a
          v-if="websiteUrl"
          :href="websiteUrl"
          target="_blank"
          rel="noopener noreferrer"
          class="underline hover:text-primary transition-colors"
        >
          Website
        </a>
      </AnnouncementsElementsTile>
    </div>

    <div
      class="flex flex-col gap-4 border-l-2 px-4 border-black dark:border-white/60 rounded-e-md py-3"
    >
      <h1 class="text-3xl font-bold">{{ data?.title }}</h1>

      <p class="whitespace-pre-line">{{ data?.description }}</p>
    </div>
  </div>
</template>
