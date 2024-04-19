import { SignupForm, SignupFormType } from "@/lib/validators/signup";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "./ui/form";
import { Input } from "./ui/input";
import { Button } from "./ui/button";
import { useMutation } from "react-query";
import axios from "axios";
import { Link } from "react-router-dom";
import { Loader2 } from "lucide-react";
import { toast } from "sonner";


export default function Signup() {

  const form = useForm<SignupFormType>({
    resolver: zodResolver(SignupForm),
    defaultValues: {
      name: "",
      email: "",
      password: "",
      role: ["user"],
      branch: "",
      degree: "",
    },
  });

  const {mutate: signUp, isLoading } = useMutation({
    mutationFn: async ({name, email, password, role, branch, degree}: SignupFormType) => {
      const payload = {
        name,
        email,
        password,
        role,
        branch,
        degree,
      };
      const { data } = await axios.post(import.meta.env.VITE_SPRING_URL + "/api/auth/signup", payload);
      return data;
    },
    onError: (error) => {
      console.log(error);
      toast.error("Something went wrong");
    },
    onSuccess: (data) => {
      console.log(data);
      toast.success("Account created successfully");
    }
  });
  

  return (
    <div className="flex min-h-screen">
      <div className="lg:block hidden">
        <img 
          src="https://images.unsplash.com/photo-1577643446073-8444f02f540b?q=80&w=3570&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" 
          className="object-cover h-screen w-full" 
        />
      </div>
      <div className="flex flex-col lg:w-6/12 w-full p-10 sm:space-y-0 space-y-10">
        <Link to={"/signin"} className="self-end">
            <Button variant={'ghost'}>
              Sign In
            </Button>
        </Link>
        <div className="flex flex-1 flex-col max-w-5xl mx-auto justify-center items-center space-y-6">
          <h1 className="text-2xl font-semibold">Sign Up</h1>
          <Form {...form}>
            <form onSubmit={form.handleSubmit((e) => signUp(e))} className="sm:w-96 w-72 space-y-6">
              <FormField
                control={form.control}
                name="name"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Name</FormLabel>
                    <FormControl>
                      <Input placeholder="John Doe" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Email</FormLabel>
                    <FormControl>
                      <Input placeholder="example@northeastern.edu" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Password</FormLabel>
                    <FormControl>
                      <Input type="password" placeholder="password" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="branch"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Branch</FormLabel>
                    <FormControl>
                      <Input placeholder="CSE" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="degree"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Degree</FormLabel>
                    <FormControl>
                      <Input placeholder="B.Tech" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <Button type="submit" disabled={isLoading} className="w-full">
                {isLoading && <Loader2 size={18} className="mr-2 animate-spin"/>}
                Sign Up
              </Button>
            </form>
          </Form>
        </div>
      </div>
    </div>
  )
}