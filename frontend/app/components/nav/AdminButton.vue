<template>
  <UTooltip text="Admin">
    <UButton
      color="neutral"
      variant="ghost"
      aria-label="Admin"
      class="cursor-pointer"
      @click="$router.push('/admin')"
    >
      <Icon name="eos-icons:admin-outlined" class="w-5 h-5" />
    </UButton>
  </UTooltip>
</template>
