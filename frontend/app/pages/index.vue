<script setup lang="ts">
const { data } = await useAPI("/api/hello");
</script>

<template>
  <div class="bg-white text-black">{{ data }}</div>
</template>
