
import { Navigate, Outlet } from "react-router-dom";

interface ProtectedRouteProps {
  token: string;
}

export default function ProtectedRoute({ token }: ProtectedRouteProps) {


  if (!token) {
    return <Navigate to="/signin" />
  }

  return (
    <Outlet />
  )
}
