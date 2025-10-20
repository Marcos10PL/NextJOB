<script setup lang="ts">
const { clearSession } = useAuth();
const toast = useToast();
const router = useRouter();

const signOut = async () => {
  clearSession();
  toast.add({ title: "Logged out successfully" });
  router.push("/");
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
      <Icon name="solar:logout-2-outline" class="rotate-180 w-5 h-5" />
    </UButton>
  </UTooltip>
</template>
