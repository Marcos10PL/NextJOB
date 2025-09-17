<script setup lang="ts">
import type { FormSubmitEvent } from "@nuxt/ui";
import z from "zod";

const supabase = useSupabaseClient();

const loginSchema = z.object({
  email: z.email("Invalid email address"),
});

const state = reactive({
  email: undefined,
});

const loading = ref(false);
const success = ref(false);
const error = ref<string>();

const onSubmit = async (
  event: FormSubmitEvent<z.output<typeof loginSchema>>
) => {
  loading.value = true;
  try {
    const { email } = event.data;
    const { error: signInError } = await supabase.auth.signInWithOtp({
      email,
      options: {
        emailRedirectTo: "http://localhost:3000/confirm",
      },
    });
    if (signInError) error.value = signInError.message;
    else success.value = true;
  } catch (err) {
    console.log(err);
    error.value = "Something went wrong. Please try again.";
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div>
    <h1 class="text-2xl font-bold text-center">Sign in or Sign up</h1>
    <UForm
      class="w-full max-w-80 flex flex-col gap-4 items-center justify-center border border-accented p-8 rounded-lg mx-auto mt-4 shadow-xl"
      :schema="loginSchema"
      :state="state"
      @submit="onSubmit"
    >
      <UFormField name="email">
        <UInput
          v-model="state.email"
          trailing-icon="i-lucide-at-sign"
          placeholder="Email"
          class="w-full max-w-60"
          size="xl"
          variant="subtle"
        />
      </UFormField>

      <UButton
        class="w-full cursor-pointer"
        size="lg"
        type="submit"
        :disabled="loading || success"
      >
        {{ loading ? "Loading..." : success ? "Sent!" : "Send magic link" }}
      </UButton>

      <div
        v-if="error"
        class="text-sm text-error font-semibold max-w-56 break-words"
      >
        {{ error }}
      </div>

      <div
        v-show="success"
        class="text-sm text-secondary font-semibold text-justify max-w-56"
      >
        Check your inbox and click the link we sent you. You might need to check
        your spam folder. You don't need a password.
      </div>
    </UForm>
  </div>
</template>
