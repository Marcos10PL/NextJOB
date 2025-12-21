import type { WorkModeEnum } from "~/types";

export interface Option {
  value: number;
  label: string;
}

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

export function mapToOptions(
  data: { id: number; name: string }[],
  labels: Record<string, string>
): Option[] {
  return data.map(item => ({
    value: Number(item.id),
    label: labels[item.name] || item.name,
  }));
}