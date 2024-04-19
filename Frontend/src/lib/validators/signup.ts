import { z } from "zod";

export const SignupForm = z.object({
    name: z.string().min(3, { message: "Name must be at least 3 characters long" }),
    email: z.string().email({ message: "Please enter a valid email address" }),
    password: z.string().min(8, { message: "Password must be at least 8 characters long" }),
    role: z.array(z.enum(["user", "admin"])),
    branch: z.string().min(3, { message: "Branch must be at least 3 characters long" }),
    degree: z.string().min(2, { message: "Degree must be at least 2 characters long" }),
});

export type SignupFormType = z.infer<typeof SignupForm>;