<script setup lang="ts">
const { size = "sm" } = defineProps<{
  tiles: string[];
  size?: "sm" | "md" | "lg";
}>();

const textClass = computed(() => {
  switch (size) {
    case "sm":
      return "text-sm";
    case "lg":
      return "text-lg";
    case "md":
    default:
      return "text-base";
  }
});
</script>

<template>
  <div class="flex flex-wrap gap-2">
    <div
      v-for="value in tiles"
      :key="value"
      class="dark:text-gray-300 border border-gray-300 dark:border-gray-600 rounded-md px-2 py-1"
      :class="textClass"
    >
      {{ value }}
    </div>
  </div>
</template>
