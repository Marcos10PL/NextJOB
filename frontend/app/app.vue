<script setup lang="ts">
useHead({
  titleTemplate: titleChunk => {
    return titleChunk ? `${titleChunk} | NextJOB` : "NextJOB";
  },
});

</script>

<template>
  <NuxtLayout>
    <NuxtLoadingIndicator />
    <NuxtPage />
    <NuxtRouteAnnouncer />
  </NuxtLayout>
</template>
