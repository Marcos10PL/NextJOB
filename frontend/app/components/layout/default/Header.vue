<script lang="ts" setup>
const { user } = useAuth();
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
      <div class="flex items-center sm:gap-x-2 *:w-10 *:flex *:justify-center">
        <NavAdminButton v-if="user && user.role === 'ADMIN'" />

        <NavPostButton v-if="user" />
        <NavSettingsButton v-if="user" />

        <NavLoginModal v-if="!user" />
        <NavLogoutButton v-else />

        <UColorModeButton />
      </div>
    </template>
  </UHeader>
</template>
