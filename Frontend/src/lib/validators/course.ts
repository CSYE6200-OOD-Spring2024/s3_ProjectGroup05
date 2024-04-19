import { z } from "zod";

export const courseAdd = z.object({
  title: z.string(),
  semester: z.string(),
  hours: z.coerce.number(),
  crn: z.coerce.number(),
  enrollment: z.coerce.number(),
  seats: z.coerce.number(),
  instructor: z.string(),
  day: z.string(),
  startTime: z.string(),
  endTime: z.string(),
});

export type courseAddType = z.infer<typeof courseAdd>;