<script setup lang="ts">
defineProps<{
  to: string;
}>();
</script>

<template>
  <NuxtLink :to="to" class="text-primary hover:text-primary-300 duration-150">
    <slot />
  </NuxtLink>
</template>
