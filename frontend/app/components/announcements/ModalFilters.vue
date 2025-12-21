<script setup lang="ts">
defineProps<{
  total: number;
}>();

const emit = defineEmits(["reset"]);

const open = defineModel<boolean>("open");

const reset = () => {
  open.value = false;
  emit("reset");
};
</script>

<template>
  <UModal
    v-model:open="open"
    title="Filters"
    :ui="{
      body: '!p-0 max-h-[70vh] ',
      footer: 'flex *:flex-1 *:justify-center *:cursor-pointer gap-2',
    }"
  >
    <UButton
      label="Open filters"
      color="neutral"
      variant="subtle"
      class="cursor-pointer"
      icon="material-symbols:filter-list"
    />

    <template #body>
      <AnnouncementsFilters class="border-0 rounded-none" :header="false" />
    </template>

    <template #footer>
      <UButton
        label="Wyczyść"
        size="lg"
        color="neutral"
        variant="subtle"
        @click="reset"
      />
      <UButton
        :label="`Pokaż wyniki (${total})`"
        size="lg"
        @click="open = false"
      />
    </template>
  </UModal>
</template>
