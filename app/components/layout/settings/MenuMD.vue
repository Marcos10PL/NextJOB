<script setup lang="ts">
import { settingsItems } from "~/constants";
</script>

<template>
  <aside class="hidden md:block">
    <UNavigationMenu
      orientation="vertical"
      :items="settingsItems"
      class="data-[orientation=vertical]:w-52 border-r border-accented pr-6"
    />
  </aside>
</template>
