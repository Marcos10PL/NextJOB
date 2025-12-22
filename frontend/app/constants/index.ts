import type { NavigationMenuItem } from "@nuxt/ui";
import type { WorkModeEnum } from "~/types";

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
    // {
    //   label: "Delete Account",
    //   icon: "solar:trash-bin-2-bold",
    //   to: "/settings/delete-account",
    // },
    {
      label: "My applications",
      icon: "solar:document-add-bold",
      to: "/settings/my-applications",
    },
    {
      label: "Applications",
      icon: "solar:document-bold",
      to: "/settings/applications",
    },
  ],
]);

export const POST_JOB_DESC_MAX_SIZE = 1500;
export const COMPANY_DESC_MAX_SIZE = 500;
export const JOB_SEEKER_DESC_MAX_SIZE = 500;

export const MAX_FILE_SIZE = 4 * 1024 * 1024;
export const ACCEPTED_FILE_TYPES = ["application/pdf"];

export const contractTypeLabels: Record<string, string> = {
  b2b: "B2B",
  employment_contract: "Employment Contract",
  mandate_contract: "Mandate Contract",
  specific_task_contract: "Specific Task Contract",
  internship_contract: "Internship Contract",
  other: "Other",
};

export const workloadTypeLabels: Record<string, string> = {
  full_time: "Full Time",
  part_time: "Part Time",
  temporary: "Temporary",
  internship: "Internship",
};

export const workModeLabels: Record<WorkModeEnum, string> = {
  remote: "Remote",
  on_site: "On Site",
  hybrid: "Hybrid",
};

export const paymentTypeLabels: Record<string, string> = {
  hourly: "Hourly",
  weekly: "Weekly",
  monthly: "Monthly",
};
