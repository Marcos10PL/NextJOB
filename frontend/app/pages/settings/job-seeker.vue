<script setup lang="ts">
import type z from "zod";
import type { FormSubmitEvent } from "@nuxt/ui";
import type { JobSeeker } from "~/types";
import deepEqual from "fast-deep-equal";
import { jobSeekerSchema } from "~/schemas";
import { JOB_SEEKER_DESC_MAX_SIZE } from "~/constants";

useHead({
  title: "Job Seeker",
});

const { user } = useAuth();

const toast = useToast();

const { jobSeeker, isJobSeekerExists } = useJobSeeker();

const schema = jobSeekerSchema;

type Schema = z.output<typeof jobSeekerSchema>;

const state = reactive<Partial<Schema>>({
  email: user.value?.email || "",
  fullName: user.value?.fullName || "",
  message: "",
  cv: undefined,
});

watch(
  jobSeeker,
  newVal => {
    isJobSeekerExists.value = !!newVal;
    Object.assign(state, newVal);
  },
  { immediate: true }
);

const loading = ref(false);

async function onSubmit(event: FormSubmitEvent<Schema>) {
  const hasChanges = !deepEqual(event.data, {
    ...jobSeeker.value,
    cv: undefined,
  });

  if (!hasChanges) {
    toast.add({
      title: "No changes to update",
      color: "info",
    });
    return;
  }

  if (!isJobSeekerExists.value) {
    loading.value = true;
    const { data, error } = await useAPI<JobSeeker>("/api/job-seekers", {
      method: "POST",
      body: event.data,
    });

    if (error.value) {
      toast.add({
        title: "Error updating profile",
        color: "error",
      });
      loading.value = false;
      return;
    }

    if (data.value) {
      Object.assign(state, data.value);
      jobSeeker.value = data.value;
      isJobSeekerExists.value = true;
    }
  } else {
    loading.value = true;
    const { data, error } = await useAPI<JobSeeker>(`/api/job-seekers/me`, {
      method: "PATCH",
      body: event.data,
    });

    if (error.value) {
      toast.add({
        title: "Error updating profile",
        color: "error",
      });
      loading.value = false;
      return;
    }

    if (data.value) {
      Object.assign(state, data.value);
      jobSeeker.value = data.value;
    }
  }

  toast.add({
    title: "Profile updated successfully",
    color: "success",
  });
  loading.value = false;
}
</script>

<template>
  <NuxtLayout name="settings">
    <UForm :schema="schema" :state="state" class="space-y-6" @submit="onSubmit">
      <div class="flex flex-col gap-8 *:w-full max-w-xl">
        <FormsHeader>
          This is your job seeker profile information. It will be used to
          display your details to potential employers when you apply for jobs.
        </FormsHeader>
        <UFormField label="Email" name="email">
          <UInput v-model="state.email" class="w-full" />
        </UFormField>
        <UFormField label="Full Name" name="fullName">
          <UInput v-model="state.fullName" class="w-full" />
        </UFormField>

        <FormsTextarea
          v-model="state.message"
          :max-size="JOB_SEEKER_DESC_MAX_SIZE"
          :rows="5"
          label="Message"
          name="message"
          placeholder="Your message..."
          description="Provide a brief message about yourself."
        />

        <UFormField name="cv" label="CV Upload">
          <UFileUpload
            v-model="state.cv"
            position="inside"
            layout="list"
            accept="application/pdf"
            class="w-full"
            label="Drop your CV here"
            description="PDF format only, max size 4MB"
            :file-names="state.cv ? [state.cv.name] : []"
          />
        </UFormField>

        <UButton
          type="submit"
          class="px-8 max-h-fit self-end justify-center cursor-pointer"
          :disabled="loading"
        >
          {{ isJobSeekerExists ? "Update Profile" : "Create Profile" }}
        </UButton>
      </div>
    </UForm>
  </NuxtLayout>
</template>
