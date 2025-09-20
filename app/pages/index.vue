<script setup lang="ts">
const { data, pending, error } = await useFetch("/api/hello");
</script>

<template>
  <div>
    <pre>{{ data }}</pre>
    <div v-if="pending">Loading...</div>
    <div v-if="error">{{ error }}</div>
  </div>
</template>
