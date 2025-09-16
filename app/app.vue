<script setup lang="ts">
const layout = useSupabaseUser().value ? "main" : "auth";
</script>

<template>
  <UApp>
    <NuxtLayout :name="layout">
      <NuxtRouteAnnouncer />
      <NuxtPage />
    </NuxtLayout>
  </UApp>
</template>
