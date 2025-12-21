<script setup lang="ts">
defineProps<{
  label: string;
}>();
</script>

<template>
  <UButton
    variant="outline"
    size="lg"
    class="cursor-pointer max-w-fit"
    @click="$router.back()"
  >
    <UIcon name="material-symbols:arrow-back-ios-new" class="shrink-0" />
    {{ label }}
  </UButton>
</template>
