<script setup lang="ts">
defineProps<{
  industryName: string;
}>();
</script>

<template>
  <UBadge variant="subtle" color="neutral" class="font-semibold max-w-fit">
    {{ industryName }}
  </UBadge>
</template>
