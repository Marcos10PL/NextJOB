<script setup lang="ts">
defineProps<{
  data?: string | null;
  icon: string;
}>();
</script>

<template>
  <div
    v-if="data"
    class="flex items-center gap-1 py-1 px-2 rounded-md border border-gray-300 dark:border-gray-600"
  >
    <UIcon :name="icon" />
    <slot>
      {{ data }}
    </slot>
  </div>
</template>
