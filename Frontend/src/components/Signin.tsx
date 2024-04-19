import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form"
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "./ui/form";
import { Input } from "./ui/input";
import { Button } from "./ui/button";
import { SigninForm, SigninFormType } from "@/lib/validators/signin";
import axios from "axios";
import { useMutation } from "react-query";
import { Role } from "@/lib/role";
import { Link, useNavigate } from "react-router-dom";
import { Loader2 } from "lucide-react";
import { getSignIn } from "@/store/AuthStore";
import { toast } from "sonner";


export default function Signin() {

  const signin = getSignIn();
  const navigate = useNavigate();

  const form = useForm<SigninFormType>({
    resolver: zodResolver(SigninForm),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const {mutate: signIn, isLoading } = useMutation({
    mutationFn: async ({email, password}: SigninFormType) => {
      const payload = {
        email,
        password,
      };
      const { data } = await axios.post(import.meta.env.VITE_SPRING_URL + "/api/auth/signin", payload);
      return data;
    },
    onError: (error) => {
      console.log(error);
      toast.error("Something went wrong");
    },
    onSuccess: (data) => {
      console.log(data);
      const role = data.roles == "ROLE_ADMIN" ? Role.ADMIN : Role.USER;
      signin({ token: data.accessToken, user: { id: data.id , name: data.name, email: data.email, role: role}});
      toast.success("Signed in successfully");
      navigate("/", { replace: true });
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
      <div className="flex flex-col lg:w-6/12 w-full p-10">
        <Link to={"/signup"} className="self-end">
          <Button variant={'ghost'}>
            Sign Up
          </Button>
        </Link>
        <div className="flex flex-1 flex-col max-w-5xl mx-auto justify-center items-center space-y-6">
          <h1 className="text-4xl font-semibold">Sign In</h1>
          <Form {...form}>
            <form onSubmit={form.handleSubmit((e) => signIn(e))} className="sm:w-96 w-72 space-y-6">
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
              <Button type="submit" disabled={isLoading} className="w-full">
                {isLoading && <Loader2 size={18} className="mr-2 animate-spin"/>}
                Sign In
              </Button>
            </form>
          </Form>
        </div>
      </div>
    </div>

  )
}
