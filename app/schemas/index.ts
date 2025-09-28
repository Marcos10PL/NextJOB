import z from "zod";

export const ProfileSchema = z.object({
  full_name: z.string().min(2).max(100),
  email: z.email("Invalid email address"),
  phone: z.string().min(5).max(20).optional(),
  created_at: z.string().optional(),

  address: z.string().min(5).max(200).optional(),
  city: z.string().min(2).max(100).optional(),
  country: z.string().min(2).max(100).optional(),
});
