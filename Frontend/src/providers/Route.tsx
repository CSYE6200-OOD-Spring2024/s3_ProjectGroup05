import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Signin from "@/components/Signin";
import Signup from "@/components/Signup";
import AdminDashboard from "@/components/AdminDashboard";
import UserCourses from "@/components/userCourses";
import ProtectedRoute from "@/components/ProtectedRoute";
import { useToken } from "@/store/AuthStore";

const Routes = () => {

  const token = useToken();

  // Define routes accessible only to authenticated users
  const routesForAuthenticatedOnly = [
    {
      path: "/",
      element: <ProtectedRoute token={token} />, // Wrap the component in ProtectedRoute
      children: [
        {
          path: "/",
          element: <AdminDashboard />,
        },
        {
          path: "/:user",
          element: <UserCourses />,
        },
      ],
    },
  ];

  // Define routes accessible only to non-authenticated users
  const routesForNotAuthenticatedOnly = [
    {
      path: "/signin",
      element: <Signin />,
    },
    {
      path: "/signup",
      element: <Signup />,
    },
  ];

  // Combine and conditionally include routes based on authentication status
  const router = createBrowserRouter([
    ...(!token ? routesForNotAuthenticatedOnly : []),
    ...routesForAuthenticatedOnly,
  ]);

  // Provide the router configuration using RouterProvider
  return <RouterProvider router={router} />;
};

export default Routes;