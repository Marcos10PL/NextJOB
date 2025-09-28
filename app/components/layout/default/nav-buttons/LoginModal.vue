<script setup lang="ts">
import type { FormSubmitEvent } from "@nuxt/ui";
import z from "zod";

const supabase = useSupabaseClient();
const toast = useToast();

const loginSchema = z.object({
  email: z.email("Invalid email address"),
});

const state = reactive({
  email: undefined,
});

const loading = ref(false);
const success = ref(false);
const error = ref<string>();

const confirmUrl = useRuntimeConfig().public.confirmUrl;

const onSubmit = async (
  event: FormSubmitEvent<z.output<typeof loginSchema>>
) => {
  loading.value = true;
  try {
    const { email } = event.data;
    const { error: signInError } = await supabase.auth.signInWithOtp({
      email,
      options: {
        emailRedirectTo: confirmUrl,
      },
    });
    if (signInError) error.value = signInError.message;
    else success.value = true;
  } catch (err) {
    console.log(err);
    error.value = "Something went wrong. Please try again.";
    toast.add({
      title: "Failed to send magic link",
      color: "error",
    });
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <UModal title="Sign in or Sign up">
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
      <UForm
        class="w-full flex flex-col gap-4 items-center justify-center rounded-lg mx-auto"
        :schema="loginSchema"
        :state="state"
        @submit="onSubmit"
      >
        <div class="w-full flex flex-col md:flex-row gap-y-4 relative">
          <UFormField name="email" class="w-full md:w-2/3 md:pr-6">
            <UInput
              v-model="state.email"
              trailing-icon="i-lucide-at-sign"
              placeholder="Email"
              class="w-full"
              size="xl"
              variant="subtle"
            />
          </UFormField>
          <UButton
            class="w-full md:w-1/3 cursor-pointer md:absolute h-10 right-0"
            size="lg"
            type="submit"
            :label="
              loading ? 'Loading...' : success ? 'Sent!' : 'Send magic link'
            "
            :disabled="loading || success"
            aria-label="Send magic link"
          />
        </div>

        <div
          v-if="error"
          class="text-sm text-error font-semibold max-w-56 break-words"
        >
          {{ error }}
        </div>

        <div
          v-show="success"
          class="text-sm text-secondary font-semibold text-justify"
        >
          Check your inbox and click the link we sent you. You might need to
          check your spam folder. You don't need a password.
        </div>
      </UForm>
    </template>
  </UModal>
</template>
