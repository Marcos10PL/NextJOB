<script setup lang="ts">
import type { Industry, PaginationResponse } from "~/types";
import type { TableColumn } from "@nuxt/ui";

const page = ref(1);
const size = 6;

const {
  data: industries,
  status,
  refresh,
} = useAPI<PaginationResponse<Industry>>("/api/industries", {
  query: computed(() => ({
    page: page.value - 1,
    size,
    sort: "id,asc",
  })),
});

const UButton = resolveComponent("UButton");
const openModal = ref(false);
const industryToEdit = ref<Industry | null>(null);

const toast = useToast();

const deleteIndustry = async (industryId: number) => {
  const { execute, error } = useAPI(`/api/industries/${industryId}`, {
    method: "DELETE",
    immediate: false,
  });
  await execute();

  if (error.value) {
    console.error(error.value);
    toast.add({
      title: "Error deleting industry",
      color: "error",
    });
    return;
  }

  await refresh();
  toast.add({ title: "Industry Deleted" });
};

const columns: TableColumn<Industry>[] = [
  {
    accessorKey: "id",
    header: "#",
    cell: ({ row }) => `#${row.getValue("id")}`,
  },
  {
    accessorKey: "name",
    header: "Industry Name",
  },
  {
    accessorKey: "",
    header: "Actions",
    cell: ({ row }) => {
      return h("div", { class: "flex gap-2" }, [
        h(
          UButton,
          {
            color: "neutral",
            variant: "ghost",
            class: "text-red-400 cursor-pointer",
            "aria-label": "Actions dropdown",
            onClick: () => deleteIndustry(row.getValue("id")),
          },
          { default: () => "Delete" }
        ),
        h(
          UButton,
          {
            color: "neutral",
            variant: "ghost",
            class: "text-blue-300 cursor-pointer",
            "aria-label": "Actions dropdown",
            onClick: () => (
              (openModal.value = true), (industryToEdit.value = row.original)
            ),
          },
          { default: () => "Update" }
        ),
      ]);
    },
  },
];
</script>

<template>
  <NuxtLayout name="admin">
    <AdminIndustryAddModal
      v-model:open="openModal"
      :refresh-industries="refresh"
      :industry-to-edit="industryToEdit"
    />
    <UTable
      :data="industries?.content"
      :loading="status === 'pending'"
      :columns="columns"
    />
    <div class="w-full flex justify-center my-4">
      <UPagination
        v-model:page="page"
        :total="industries?.page.totalElements"
        :items-per-page="size"
      />
    </div>
  </NuxtLayout>
</template>
