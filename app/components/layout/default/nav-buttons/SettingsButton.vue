<template>
  <UTooltip text="Settings">
    <UButton
      color="neutral"
      variant="ghost"
      aria-label="Settings"
      class="cursor-pointer"
      @click="$router.push('/settings')"
    >
      <Icon name="solar:settings-linear" class="rotate-180 w-5 h-5" />
    </UButton>
  </UTooltip>
</template>
