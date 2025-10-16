import type { NavigationMenuItem } from "@nuxt/ui"

export const settingsItems = ref<NavigationMenuItem[][]>([
  [
    {
      label: "Profile",
      icon: "solar:user-circle-bold",
      to: "/settings/profile",
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