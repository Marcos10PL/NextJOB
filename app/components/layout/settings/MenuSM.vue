<script setup lang="ts">
import type { NavigationMenuItem } from "@nuxt/ui";
import { settingsItems } from "~/constants";

const route = useRoute();

const title = computed(() => {
  const arr = settingsItems.value[0] as NavigationMenuItem[];
  return arr?.find(i => i.to === route.path)?.label || "Settings";
});
</script>

<template>
  <header
    class="flex items-center justify-between w-full bg-neutral-800 fixed top-16 left-0 px-4 py-2 md:hidden"
  >
    <h1>{{ title }}</h1>
    <UDropdownMenu
      :items="settingsItems"
      :content="{
        align: 'start',
        side: 'bottom',
        sideOffset: 8,
      }"
      :ui="{
        content: 'w-48',
      }"
    >
      <UButton icon="i-lucide-menu" color="neutral" variant="outline" />
    </UDropdownMenu>
  </header>
</template>
