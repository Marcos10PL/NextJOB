<script lang="ts" setup>
const user = false; // TODO: actual auth logic
</script>

<template>
  <UHeader>
    <template #title>
      <TheLogo />
    </template>

    <template #toggle>
      {{ " " }}
    </template>

    <template #right>
      <div class="flex items-center gap-x-2 *:w-10 *:flex *:justify-center">
        <LayoutDefaultPostButton v-if="user" />
        <LayoutDefaultSettingsButton v-if="user" />
        <LayoutDefaultLoginModal v-if="!user" />
        <LayoutDefaultLogoutButton v-else />
        <UColorModeButton />
      </div>
    </template>
  </UHeader>
</template>