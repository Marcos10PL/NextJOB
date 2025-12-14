export type FetchError = {
  status: number;
  message: string;
};

enum AuthRole {
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
