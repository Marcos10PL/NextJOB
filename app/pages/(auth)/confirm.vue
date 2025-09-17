<script setup lang="ts">
const user = useSupabaseUser();

watch(
  user,
  () => {
    if (user.value) {
      return navigateTo("/");
    }
  },
  { immediate: true }
);
</script>

<template>
  <div class="text-center flex flex-col items-center space-y-4">
    <p class="text-info">Waiting for login...</p>
    <p class="flex flex-col opacity-50">
      If you reached this page by mistake, <br>
      please navigate away.
      <UiLink to="/">Go to homepage</UiLink>
    </p>
  </div>
</template>
