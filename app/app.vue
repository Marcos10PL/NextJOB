<script setup lang="ts">
const user = useSupabaseUser();

const layout = computed(() => (user.value ? "main" : "auth"));
</script>

<template>
  <UApp>
    <NuxtLayout :name="layout">
      <NuxtRouteAnnouncer />
      <NuxtPage />
    </NuxtLayout>
  </UApp>
</template>
