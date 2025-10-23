<script setup lang="ts">
const { data: industries } = useAPI("/api/industries");
</script>

<template>
  <div>
    {{ industries }}
  </div>
</template>
