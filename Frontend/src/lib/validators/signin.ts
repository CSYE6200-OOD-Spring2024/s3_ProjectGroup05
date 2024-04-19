import { z } from "zod";

export const SigninForm = z.object({
    email: z.string().email(),
    password: z.string(),
});

export type SigninFormType = z.infer<typeof SigninForm>;