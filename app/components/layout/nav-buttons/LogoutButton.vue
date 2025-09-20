<script setup lang="ts">
const supabase = useSupabaseClient();
const toast = useToast();
const loading = ref(false);

const signOut = async () => {
  try {
    loading.value = true;
    await supabase.auth.signOut();
    toast.add({
      title: "You have been logged out",
    });
  } catch (error) {
    console.log(error);
  } finally {
    loading.value = false;
  }
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
