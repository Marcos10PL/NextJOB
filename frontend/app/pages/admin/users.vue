<script setup lang="ts">
import type { User } from "~/types";
import type { TableColumn } from "@nuxt/ui";

const { data: users, status, refresh } = await useAPI<User[]>("/api/users");

const toast = useToast();

const { user } = useAuth();

const parseDate = (dateStr: string) => {
  return new Date(dateStr).toLocaleString("en-US", {
    day: "numeric",
    month: "short",
    hour: "2-digit",
    minute: "2-digit",
    hour12: false,
  });
};

const UButton = resolveComponent("UButton");

const deleteUser = async (userId: number) => {
  const { execute, error } = useAPI(`/api/users/${userId}`, {
    method: "DELETE",
    immediate: false,
  });

  await execute();

  if (error.value) {
    console.error(error.value);
    toast.add({
      title: "Error deleting user",
      color: "error",
    });
    return;
  }

  toast.add({ title: "User Deleted" });
  await refresh();
};

const columns: TableColumn<User>[] = [
  {
    accessorKey: "id",
    header: "#",
    cell: ({ row }) => `#${row.getValue("id")}`,
  },
  {
    accessorKey: "email",
    header: "Email",
    cell: ({ row }) =>
      row.getValue("email") +
      (row.getValue("id") === user?.value?.id ? " (you)" : ""),
  },
  {
    accessorKey: "fullName",
    header: "Full Name",
  },
  {
    accessorKey: "role",
    header: "Role",
  },
  {
    accessorKey: "updatedAt",
    header: "Updated At",
    cell: ({ row }) => {
      return parseDate(row.getValue("updatedAt"));
    },
  },
  {
    accessorKey: "createdAt",
    header: "Created At",
    cell: ({ row }) => {
      return parseDate(row.getValue("createdAt"));
    },
  },
  {
    accessorKey: "",
    header: "Actions",
    cell: ({ row }) => {
      const userRow = row.original;

      return h("div", [
        h(
          UButton,
          {
            color: "neutral",
            variant: "ghost",
            class: "text-red-400 cursor-pointer",
            disabled: userRow.role === "ADMIN",
            "aria-label": "Actions dropdown",
            onClick: () => deleteUser(Number(userRow.id)),
          },
          "Delete"
        ),
      ]);
    },
  },
];
</script>

<template>
  <NuxtLayout name="admin">
    <UTable :data="users" :loading="status === 'pending'" :columns="columns" />
  </NuxtLayout>
</template>
