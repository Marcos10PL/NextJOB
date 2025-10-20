export type FetchError = {
  status: number;
  message: string;
};

export type User = {
  fullName: string;
  email: string;
  role: "ADMIN" | "USER";
  createdAt: string;
  updatedAt: string;

  address?: string;
  city?: string;
  country?: string;
};