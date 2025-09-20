<template>
  <UTooltip text="Post a job">
    <UButton
      color="neutral"
      variant="ghost"
      aria-label="Post a job"
      class="cursor-pointer"
      @click="$router.push('/post-job')"
    >
      <Icon
        name="solar:home-add-angle-bold"
        class="w-5 h-5 text-primary"
      />
    </UButton>
  </UTooltip>
</template>
