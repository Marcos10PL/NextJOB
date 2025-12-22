<script setup lang="ts">
import type z from "zod";
import type { FormSubmitEvent } from "@nuxt/ui";
import type { Industry } from "~/types";
import { industryNameSchema } from "~/schemas";

const { refreshIndustries, industryToEdit = null } = defineProps<{
  refreshIndustries: () => Promise<void>;
  industryToEdit?: Industry | null;
}>();

watch(
  () => industryToEdit,
  newVal => {
    state.name = newVal?.name ?? "";
  }
);
const modal = defineModel<boolean>("modal");

const schema = industryNameSchema;

const toast = useToast();

type Schema = z.output<typeof schema>;

const state = reactive<Partial<Schema>>({
  name: "",
});

const updateIndustry = async (industryId: number, name: string) => {
  if (name === industryToEdit?.name) {
    toast.add({ title: "No changes made", color: "info" });
    return;
  }

  const { execute, error } = useAPI(`/api/industries/${industryId}`, {
    method: "PUT",
    immediate: false,
    body: { name },
  });
  await execute();

  if (error.value) {
    console.error(error.value);
    toast.add({
      title: "Error updating industry",
      color: "error",
    });
    return;
  }

  modal.value = false;
  await refreshIndustries();
  toast.add({ title: "Industry Updated" });
};

async function onSubmit(event: FormSubmitEvent<Schema>) {
  if (industryToEdit) {
    await updateIndustry(industryToEdit.id, event.data.name);
    return;
  }

  const { execute, error } = useAPI("/api/industries", {
    method: "POST",
    immediate: false,
    body: event.data,
  });
  await execute();

  if (error.value) {
    console.error(error.value);
    toast.add({
      title: "Error adding industry",
      description: "Probably name already in use",
      color: "error",
    });
    return;
  }

  await refreshIndustries();
  toast.add({
    title: "Added new industry",
  });
  state.name = "";
}
</script>

<template>
  <UModal v-model:open="modal" title="Add new industry">
    <UButton
      label="Add new"
      color="neutral"
      variant="subtle"
      class="cursor-pointer"
    />

    <template #body>
      <UForm
        :schema="schema"
        :state="state"
        class="space-y-4"
        @submit="onSubmit"
      >
        <UFormField label="Name" name="name" class="w-full">
          <UInput v-model="state.name" class="w-full" />
        </UFormField>

        <UButton type="submit" class="w-full cursor-pointer">
          {{ industryToEdit ? "Update" : "Add" }}
        </UButton>
      </UForm>
    </template>
  </UModal>
</template>
