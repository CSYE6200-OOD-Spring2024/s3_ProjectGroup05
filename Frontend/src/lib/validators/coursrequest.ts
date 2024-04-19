import { z } from "zod";

export const courseRequestType = z.object({
  courseCRNS: z.array(z.number().int().positive()),
});

export type CourseRequestType = z.infer<typeof courseRequestType>;
