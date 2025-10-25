import type { NavigationMenuItem } from "@nuxt/ui";

// Settings menu items
export const settingsItems = ref<NavigationMenuItem[][]>([
  [
    {
      label: "Profile",
      icon: "solar:user-circle-bold",
      to: "/settings/profile",
    },
    {
      label: "Company Profile",
      icon: "solar:buildings-2-bold",
      to: "/settings/company-profile",
    },
    {
      label: "Job Seeker",
      icon: "solar:suitcase-bold",
      to: "/settings/job-seeker",
    },
    {
      label: "Delete Account",
      icon: "solar:trash-bin-2-bold",
      to: "/settings/delete-account",
    },
  ],
]);

// File upload constants
export const MAX_FILE_SIZE = 4 * 1024 * 1024; 
export const ACCEPTED_FILE_TYPES = ["application/pdf"];