export type FetchError = {
  status: number;
  message: string;
};

export enum AuthRole {
  ADMIN = "ADMIN",
  USER = "USER",
}

export type User = {
  id: string;
  fullName: string;
  email: string;
  role: AuthRole;
  createdAt: string;
  updatedAt: string;

  address?: string;
  city?: string;
  country?: string;
};

export type Industry = {
  id: number;
  name: string;
};

export type ContractType = {
  id: number;
  name: string;
};

export type WorkloadType = {
  id: number;
  name: string;
};

export type WorkMode = {
  id: number;
  name: string;
};

export type PaymentType = {
  id: number;
  name: string;
};

export type PaginationResponse<T> = {
  content: T[];
  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  };
};

export type Company = {
  id: number;
  name: string;
  email: string;
  description: string;
  website: string;
  industryId: number;
  address?: string;
  city?: string;
  country?: string;
};

export type JobSeeker = {
  email: string;
  fullName: string;
  message: string;
};

export type JobAnnouncement = {
  id: number;
  title: string;
  description: string;
  salary_min?: number;
  salary_max?: number;
  is_cv_required: boolean;
  author_id: number;
  company_id: number | null;
  contract_type_id: number;
  workload_type_id: number;
  work_mode_id: number;
  payment_type_id: number;
  industry_id: number;

  address?: string;
  city?: string;
  country?: string;

  created_at: string;
  updated_at: string;
};

export type JobAnnouncementListItem = {
  id: number;
  title: string;
  city: string | null;
  country: string | null;
  salaryMin: number;
  salaryMax: number;
  authorId: number | null;
  authorName: string | null;
  companyId: number | null;
  companyName: string | null;
  industryName: string;
  contractType: string;
  workloadType: string;
  workMode: string;
  paymentType: string;
  createdAt: string;
  updatedAt: string;
};

type JobAnnouncementFilterBase = {
  title?: string;
  industryIds?: string[];
  contractTypeIds?: string[];
  workloadTypeIds?: string[];
  workModeIds?: string[];
  paymentTypeIds?: string[];
  salaryMin?: number;
  salaryMax?: number;
  city?: string;
  country?: string;
  isCvRequired?: boolean;
};

export type JobAnnouncementFilterDebounced = Pick<
  JobAnnouncementFilterBase,
  "title" | "country" | "city" | "salaryMin" | "salaryMax"
>;

export type JobAnnouncementFilter = Omit<
  JobAnnouncementFilterBase,
  keyof JobAnnouncementFilterDebounced
>;

export enum JobSort {
  CREATED_AT_DESC = "createdAt,desc",
  CREATED_AT_ASC = "createdAt,asc",
  UPDATED_AT_DESC = "updatedAt,desc",
  UPDATED_AT_ASC = "updatedAt,asc",
}

export enum WorkModeEnum {
  REMOTE = "remote",
  ONSITE = "on_site",
  HYBRID = "hybrid",
}

export type Author = {
  id: number;
  fullName: string;
  email: string;
  address?: string | null;
  city?: string | null;
  country?: string | null;
};

export type CompanyDetails = {
  id: number;
  name: string;
  description?: string | null;
  email: string;
  website?: string | null;
  industryName?: string | null;
  address?: string | null;
  city?: string | null;
  country?: string | null;
};

export type JobAnnouncementDetailsResponse = {
  id: number;
  title: string;
  description: string;
  salaryMin: number;
  salaryMax: number;
  isCvRequired: boolean;
  city?: string | null;
  country?: string | null;
  address?: string | null;
  updatedAt: string;
  industryName: string;
  contractTypeName: string;
  workloadTypeName: string;
  workModeName: string;
  paymentTypeName: string;
  company?: CompanyDetails | null;
  author?: Author | null;
};
