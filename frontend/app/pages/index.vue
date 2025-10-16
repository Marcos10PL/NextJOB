<script setup lang="ts">
const { data } = await useFetch("/api/hello");
</script>

<template>
  <div class="bg-white text-black">{{ data }}</div>
</template>
