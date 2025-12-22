import z from "zod";
import {
  ACCEPTED_FILE_TYPES,
  COMPANY_DESC_MAX_SIZE,
  JOB_SEEKER_DESC_MAX_SIZE,
  MAX_FILE_SIZE,
  POST_JOB_DESC_MAX_SIZE,
} from "~/constants";

const fullNameSchema = z
  .string()
  .min(2, "Full name must be at least 2 characters long")
  .max(100, "Full name must be at most 100 characters long");

const passwordSchema = z
  .string()
  .min(8, "Password must be at least 8 characters long");

const emailSchema = z.email("Invalid email address");

export const industryNameSchema = z.object({
  name: z
    .string()
    .min(2, "Name should be at least 2 characters long")
    .max(50, "Name should be at most 50 characters long"),
});

export const loginSchema = z.object({
  email: emailSchema,
  password: passwordSchema,
});

export const registerSchema = z
  .object({
    fullName: fullNameSchema,
    email: emailSchema,
    password: passwordSchema,
    confirmPassword: passwordSchema,
  })
  .refine(data => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });

export const postJobSchema = z
  .object({
    asCompany: z.boolean(),
    title: z
      .string()
      .min(5, "Title must be at least 5 characters long")
      .max(100, "Title must be at most 100 characters long"),
    description: z
      .string()
      .min(8, "Description must be at least 8 characters long")
      .max(
        POST_JOB_DESC_MAX_SIZE,
        `Description must be at most ${POST_JOB_DESC_MAX_SIZE} characters long`
      ),

    salaryMin: z
      .number("Minimum salary is required")
      .min(0, "Minimum salary cannot be negative"),
    salaryMax: z
      .number("Minimum salary is required")
      .min(0, "Maximum salary cannot be negative"),

    industryId: z.number("Industry is required").int("Industry is required"),

    isCvRequired: z.boolean(),

    contractTypeId: z
      .number("Contract type is required")
      .int("Contract type is required"),
    workModeId: z.number("Work mode is required").int("Work mode is required"),
    paymentTypeId: z
      .number("Payment type is required")
      .int("Payment type is required"),
    workloadTypeId: z
      .number("Workload type is required")
      .int("Workload type is required"),

    address: z.string().optional(),
    city: z.string().optional(),
    country: z.string().optional(),
  })
  .refine(
    data =>
      data.salaryMin === undefined ||
      data.salaryMax === undefined ||
      data.salaryMin <= data.salaryMax,
    {
      message: "Minimum salary cannot be greater than maximum salary",
      path: ["salaryMax"],
    }
  );

export const companyProfileSchema = z.object({
  name: z
    .string()
    .min(2, "Name must be at least 2 characters long")
    .max(100, "Name must be at most 100 characters long"),
  description: z
    .string()
    .min(8, "Description must be at least 8 characters long")
    .max(
      COMPANY_DESC_MAX_SIZE,
      `Description must be at most ${COMPANY_DESC_MAX_SIZE} characters long`
    ),

  email: emailSchema,
  website: z.url("Invalid website URL").or(z.literal("")),
  industryId: z.number("Industry is required").int("Industry is required"),

  address: z.string().optional(),
  city: z.string().optional(),
  country: z.string().optional(),
});

export const jobSeekerSchema = z.object({
  email: emailSchema,
  fullName: fullNameSchema,
  message: z
    .string()
    .min(10, "Message must be at least 10 characters long")
    .max(
      JOB_SEEKER_DESC_MAX_SIZE,
      `Message must be at most ${JOB_SEEKER_DESC_MAX_SIZE} characters long`
    ),
  cv: z
    .instanceof(File, {
      message: "Please select a PDF file.",
    })
    .refine(file => file.size <= MAX_FILE_SIZE, {
      message: `The file is too large. Please choose a file smaller than ${formatBytes(
        MAX_FILE_SIZE
      )}.`,
    })
    .refine(file => ACCEPTED_FILE_TYPES.includes(file.type), {
      message: "Please upload a valid PDF file.",
    })
    .optional(),
});

export const profileSchema = z.object({
  email: emailSchema,
  fullName: fullNameSchema,

  address: z.string().optional(),
  city: z.string().optional(),
  country: z.string().optional(),
});
