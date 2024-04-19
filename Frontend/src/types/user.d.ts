import { Role } from "../lib/role";

export type User = {
  id: string;
  name: string;
  email: string;
  role: Role | null;
};
