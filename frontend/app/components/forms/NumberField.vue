<script setup lang="ts">
const {
  label = "",
  step = 1,
  min = 1,
  stepSnapping = false,
  isCurrency = false,
  placeholder = "",
} = defineProps<{
  label?: string;
  name: string;
  step?: number;
  min?: number;
  stepSnapping?: boolean;
  isCurrency?: boolean;
  placeholder?: string;
}>();

const model = defineModel<number>();
</script>

<template>
  <UFormField :label="label" :name="name">
    <UInputNumber
      v-model="model"
      class="w-full"
      :step="step"
      :min="min"
      :step-snapping="stepSnapping"
      :format-options="
        isCurrency
          ? {
              style: 'currency',
              currency: 'EUR',
              currencyDisplay: 'code',
              currencySign: 'accounting',
            }
          : undefined
      "
      :placeholder="placeholder"
    />
  </UFormField>
</template>
