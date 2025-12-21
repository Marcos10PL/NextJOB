<script setup lang="ts">
const { companyName } = defineProps<{
  companyName: string | null;
  authorName: string | null;
}>();

const isCompany = computed(() => !!companyName);
</script>

<template>
  <UBadge
    variant="subtle"
    :color="isCompany ? 'primary' : 'secondary'"
    class="font-semibold max-w-fit"
    :icon="isCompany ? 'material-symbols:apartment' : 'material-symbols:person'"
  >
    {{ isCompany ? companyName : authorName }}
  </UBadge>
</template>
