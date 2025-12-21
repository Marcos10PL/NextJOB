<script setup lang="ts">
const { toggle } = useAuthModal();
</script>

<template>
  <div class="flex flex-col gap-6">
    <UPageCard
      title="Post Your Job Announcement!"
      description="Post your own job announcements and reach thousands of candidates instantly!"
      variant="subtle"
      class="border-1 border-gray-500"
      icon="mdi-briefcase-plus-outline"
    />

    <UPageCard
      title="Browse Job Opportunities"
      description="Explore thousands of job opportunities tailored to your skills and location."
      variant="subtle"
      class="border-1 border-gray-500"
      icon="mdi-briefcase-search-outline"
    />
    <UPageCard
      title="Apply and Track Applications"
      description="Apply to jobs directly through NextJOB and track your applications easily!"
      variant="subtle"
      class="border-1 border-gray-500"
      icon="mdi-briefcase-check-outline"
    />

    <UPageCard
      title="Join NextJOB Now!"
      description="Join NextJOB today and start posting, browsing, and applying for jobs!"
      variant="subtle"
      icon="mdi-account-plus"
      class="!cursor-pointer border-1 border-primary"
      @click="toggle"
    />
  </div>
</template>
