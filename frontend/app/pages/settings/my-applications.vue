<script setup lang="ts">
import type { TableColumn } from "@nuxt/ui";
import type { Application } from "~/types";

import { UButton } from "#components";

const { applications } = useApplications();

useHead({
  title: "My applications",
});

const columns: TableColumn<Application>[] = [
  {
    accessorKey: "id",
    header: "#",
    cell: ({ row }) => row.getValue("id"),
  },
  {
    accessorKey: "jobAnnouncementId",
    header: "Announcement",
    cell: ({ row }) => {
      const id = row.getValue("jobAnnouncementId");
      return h(
        UButton,
        {
          variant: "link",
          to: `/announcements/${id}`,
          class: "pl-0",
          target: "_blank",
        },
        { default: () => `View Announcement` }
      );
    },
  },
  {
    accessorKey: "status",
    header: "Status",
    cell: () => {
      return "TODO";
    },
  },
  {
    accessorKey: "appliedAt",
    header: "Applied at",
    cell: ({ row }) => {
      return new Date(row.getValue("appliedAt")).toLocaleString();
    },
  },
];
</script>

<template>
  <NuxtLayout name="settings">
    <UTable :data="applications" :columns="columns" class="flex-1" />
  </NuxtLayout>
</template>
