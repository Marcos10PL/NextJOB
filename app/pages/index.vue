<script setup lang="ts">
const { data, pending, error } = await useFetch("/api/hello");

const supabase = useSupabaseClient();

const signOut = async () => {
  await supabase.auth.signOut();
  navigateTo("/login"); // po wylogowaniu wracamy na stronę logowania
};
</script>

<template>
  <UApp>
    <NuxtRouteAnnouncer />
    <NuxtPage />
    <pre>{{ data }}</pre>
    <div v-if="pending">Loading...</div>
    <div v-if="error">{{ error }}</div>

    <button @click="signOut">Wyloguj</button>
  </UApp>
</template>
