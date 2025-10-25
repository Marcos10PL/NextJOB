<script setup lang="ts">
import z from "zod";
import type { FormSubmitEvent } from "@nuxt/ui";
import { ACCEPTED_FILE_TYPES, MAX_FILE_SIZE } from "~/constants"

useHead({
  title: "Job Seeker Profile",
});

const { user } = useAuth();

// const toast = useToast();

const ProfileSchema = z.object({
  email: z.email("Invalid email address"),
  fullName: z.string().min(2, "Full name must be at least 2 characters long"),
  text: z.string().min(10, "Message must be at least 10 characters long"),
  cv: z
    .instanceof(File, {
      message: "Please select a PDF file.",
    })
    .refine(file => file.size <= MAX_FILE_SIZE, {
      message: `The file is too large. Please choose a file smaller than ${formatBytes(MAX_FILE_SIZE)}.`,
    })
    .refine(file => ACCEPTED_FILE_TYPES.includes(file.type), {
      message: "Please upload a valid PDF file.",
    })
    .optional(),
});

const state = reactive<Partial<z.output<typeof ProfileSchema>>>({
  email: user.value?.email || "",
  fullName: user.value?.fullName || "",
  text: "",
  cv: undefined,
});

async function onSubmit(
  event: FormSubmitEvent<z.output<typeof ProfileSchema>>
) {
  console.log(event.data.cv);

  // if (error.value) {
  //   toast.add({
  //     title: "Error updating profile",
  //     color: "error",
  //   });
  //   return;
  // }

  // toast.add({
  //   title: "Profile updated successfully",
  //   color: "success",
  // });
}
</script>

<template>
  <NuxtLayout name="settings">
    <UForm
      :schema="ProfileSchema"
      :state="state"
      class="space-y-6"
      @submit="onSubmit"
    >
      <div class="flex flex-col gap-8 *:w-full max-w-xl">
        <SettingsFormHeader>
          This is your job seeker profile information. It will be used to
          display your details to potential employers when you apply for jobs.
        </SettingsFormHeader>
        <UFormField label="Email" name="email">
          <UInput v-model="state.email" class="w-full" />
        </UFormField>
        <UFormField label="Full Name" name="fullName">
          <UInput v-model="state.fullName" class="w-full" />
        </UFormField>

        <UFormField label="Message" name="text">
          <UTextarea v-model="state.text" :rows="6" class="w-full" />
        </UFormField>

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
        >
          Create
        </UButton>
      </div>
    </UForm>
  </NuxtLayout>
</template>
