<script lang="ts" setup>
const user = useSupabaseUser();
</script>

<template>
  <UHeader>
    <template #title>
      <SharedLogo />
    </template>

    <template #toggle>
      {{ " " }}
    </template>

    <template #right>
      <div class="flex items-center gap-x-2 *:w-10 *:flex *:justify-center">
        <LayoutDefaultNavButtonsPostButton v-if="user" />
        <LayoutDefaultNavButtonsSettingsButton v-if="user" />
        <LayoutDefaultNavButtonsLoginModal v-if="!user" />
        <LayoutDefaultNavButtonsLogoutButton v-else />
        <UColorModeButton />
      </div>
    </template>
  </UHeader>
</template>
