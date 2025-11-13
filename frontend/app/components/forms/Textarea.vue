<script setup lang="ts">
const {
  maxSize = 500,
  rows = 4,
  placeholder = "",
  label = "",
  description = "",
} = defineProps<{
  maxSize?: number;
  rows?: number;
  placeholder?: string;
  name: string;
  label?: string;
  description?: string;
}>();

const model = defineModel<string>();
</script>

<template>
  <UFormField
    :label="label"
    :name="name"
    :description="
      model
        ? `You have used ${model.length} out of ${maxSize} characters.`
        : description
    "
  >
    <UTextarea
      v-model="model"
      :rows="rows"
      class="w-full"
      :placeholder="placeholder"
      :maxlength="maxSize"
    />
  </UFormField>
</template>
