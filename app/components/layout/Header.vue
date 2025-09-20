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
        <LayoutNavButtonsPostButton v-if="user" />
        <LayoutNavButtonsSettingsButton v-if="user" />
        <LayoutNavButtonsLoginModal v-if="!user" />
        <LayoutNavButtonsLogoutButton v-else />
        <UColorModeButton />
      </div>
    </template>
  </UHeader>
</template>
