import type { ProfileSchema } from "~/schemas";

export type Profile = z.infer<typeof ProfileSchema>;
