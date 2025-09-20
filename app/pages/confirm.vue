<script setup lang="ts">
definePageMeta({
  layout: "auth",
});

const user = useSupabaseUser();
const toast = useToast();

watch(
  user,
  () => {
    if (user.value) {
      toast.add({
        title: "You have been logged in",
      });
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
