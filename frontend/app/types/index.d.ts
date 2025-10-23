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

  address?: string;
  city?: string;
  country?: string;
};

export type Industry = {
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
