<script setup lang="ts">
import type { User } from "~/types";
import type { TableColumn } from "@nuxt/ui";

const { data: users, status } = useAPI("/api/users");

const parseDate = (dateStr: string) => {
  return new Date(dateStr).toLocaleString("en-US", {
    day: "numeric",
    month: "short",
    hour: "2-digit",
    minute: "2-digit",
    hour12: false,
  });
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
    cell: () => {
      return "—";
    },
  },
];
</script>

<template>
  <div>
    <UTable
      :data="users as User[]"
      :loading="status === 'pending'"
      :columns="columns"
    />
  </div>
</template>
