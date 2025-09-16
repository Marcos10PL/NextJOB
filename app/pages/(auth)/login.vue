<script setup lang="ts">
const supabase = useSupabaseClient();
const email = ref("");

const signInWithOtp = async () => {
  const { error } = await supabase.auth.signInWithOtp({
    email: email.value,
    options: {
      emailRedirectTo: "http://localhost:3000/confirm",
    },
  });
  if (error) console.log(error);
};
</script>

<template>
  <div class="flex flex-col items-center justify-center space-y-4">
    <UInput
      v-model="email"
      trailing-icon="i-lucide-at-sign"
      placeholder="Email"
      class="!text-xl"
      size="xl"
      variant="subtle"
    />
    <UButton @click="signInWithOtp">Sign In with E-Mail</UButton>
    <div class="text-sm text-secondary font-semibold max-w-sm text-center">
      (Check your inbox and click the link we sent you. You might need to check
      your spam folder. You don't need a password.)
    </div>
  </div>
</template>
