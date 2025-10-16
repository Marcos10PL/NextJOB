<script setup lang="ts">
// const toast = useToast();
const loading = ref(false);

const signOut = async () => {
  console.log("TO DO");
};
</script>

<template>
  <UTooltip text="Logout">
    <UButton
      color="neutral"
      variant="ghost"
      class="cursor-pointer"
      aria-label="Logout"
      @click="signOut"
    >
      <Icon
        v-if="!loading"
        name="solar:logout-2-outline"
        class="rotate-180 w-5 h-5"
      />
      <SharedSpinner v-else class="w-5 h-5" />
    </UButton>
  </UTooltip>
</template>
