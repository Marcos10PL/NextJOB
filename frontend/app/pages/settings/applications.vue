<script setup lang="ts">
import type { TableColumn } from "@nuxt/ui";
import type { Application } from "~/types";
import { UButton } from "#components";

useHead({
  title: "Applications",
});

const toast = useToast();

const loading = ref(true);
const applications = ref<Application[]>([]);

onMounted(async () => {
  try {
    loading.value = true;

    const res = await $fetch<Application[]>("/api/applications/employer", {
      headers: {
        Authorization: `Bearer ${useAuth().session.value?.token}`,
      },
    });

    applications.value = res;
  } catch (error) {
    console.error("Failed to fetch applications:", error);
    toast.add({
      title: "An error occurred while fetching applications.",
    });
  } finally {
    loading.value = false;
  }
});

const showMessageModal = ref(false);
const messageContent = ref("");

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
    accessorKey: "jobSeekerEmail",
    header: "Applicant Email",
    cell: ({ row }) => row.getValue("jobSeekerEmail"),
  },
  {
    accessorKey: "jobSeekerFullName",
    header: "Applicant Name",
    cell: ({ row }) => row.getValue("jobSeekerFullName"),
  },
  {
    accessorKey: "jobSeekerMessage",
    header: "Applicant Message",
    cell: ({ row }) => {
      const message = row.getValue("jobSeekerMessage");
      return message
        ? h(
            UButton,
            {
              variant: "link",
              class: "pl-0 cursor-pointer",
              onClick: () => {
                showMessageModal.value = true;
                messageContent.value = message as string;
              },
            },
            { default: () => "View Message" }
          )
        : "No message";
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
    <UTable
      :data="applications"
      :columns="columns"
      class="flex-1"
      :loading="loading"
    />

    <UModal v-model:open="showMessageModal">
      <template #header>Applicant Message</template>
      <template #body>
        <p>
          {{ messageContent }}
        </p>
      </template>
    </UModal>
  </NuxtLayout>
</template>
