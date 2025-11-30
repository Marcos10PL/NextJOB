export type FetchError = {
  status: number;
  message: string;
};

export type User = {
  id: string;
  fullName: string;
  email: string;
  role: "ADMIN" | "USER";
  createdAt: string;
  updatedAt: string;
  
  // Optional location fields
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
}

