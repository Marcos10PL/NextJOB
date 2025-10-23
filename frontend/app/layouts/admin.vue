<script setup lang="ts">
const links = [
  { label: "Users", to: "/admin/users" },
  { label: "Industries", to: "/admin/industries" },
];
</script>

<template>
  <div>
    <nav class="flex gap-x-2 mb-6">
      <UButton
        v-for="link in links"
        :key="link.to"
        :to="link.to"
        variant="outline"
        :class="{ 'bg-green-900/40': $route.path === link.to }"
      >
        {{ link.label }}
      </UButton>
    </nav>
    <slot />
  </div>
</template>
