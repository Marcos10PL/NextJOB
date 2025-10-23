<script setup lang="ts">
const items = [
  {
    label: "Sign In",
    slot: "signin",
    value: "signin",
  },
  {
    label: "Sign Up",
    slot: "signup",
    value: "signup",
  },
];

const route = useRoute();
const router = useRouter();

const { isOpen } = useAuthModal();

const active = computed({
  get() {
    return (route.query.tab as string) || "signin";
  },
  set(tab) {
    router.push({
      path: "/",
      query: { tab },
      hash: "#control-active-item",
    });
  },
});
</script>

<template>
  <UModal
    v-model:open="isOpen"
    title="Sign In or Sign Up"
    :ui="{
      content: 'max-w-[24rem]',
      body: 'p-0 max-w-[24rem]',
    }"
  >
    <UTooltip text="Login">
      <UButton
        color="neutral"
        variant="ghost"
        icon="solar:user-linear"
        aria-label="Login"
        class="cursor-pointer"
      />
    </UTooltip>

    <template #body>
      <UTabs v-model="active" :items="items">
        <template #signin>
          <AuthLoginForm />
        </template>
        <template #signup>
          <AuthRegisterForm />
        </template>
      </UTabs>
    </template>
  </UModal>
</template>
