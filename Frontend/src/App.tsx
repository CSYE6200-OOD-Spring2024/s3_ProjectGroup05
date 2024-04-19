
import { QueryClient, QueryClientProvider } from 'react-query';
import './App.css';
import { ThemeProvider } from './providers/ThemeProvider';
import { getUser, useToken } from './store/AuthStore';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Signin from './components/Signin';
import ProtectedRoute from './components/ProtectedRoute';
import Signup from './components/Signup';
import AdminDashboard from './components/AdminDashboard';
import { Role } from './lib/role';
import UserDashboard from './components/UserDashboard';
import UserCourses from './components/UserCourses';
import { useEffect, useState } from 'react';
import { Toaster } from 'sonner';

function App() {
  const queryClient = new QueryClient();
  const token = useToken();
  const [isUserAnAdmin, setIsUserAnAdmin] = useState(false);

  useEffect(() => {
    const userDetails = getUser();
    setIsUserAnAdmin(userDetails.role === Role.ADMIN);
  }, [token])

  return (
    <ThemeProvider defaultTheme="system" storageKey="vite-ui-theme">
      <QueryClientProvider client={queryClient}>
        <Toaster richColors/>
        <BrowserRouter>
          <Routes>
            <Route path={'/signup'} element={<Signup />}/>
            <Route path={'/signin'} element={<Signin />}/>
              <Route element={<ProtectedRoute token={token}/>}>
                <Route path={'/'} element={isUserAnAdmin ? <AdminDashboard /> : <UserDashboard />} />
                <Route path={'/:user'} element={<UserCourses />}/>
              </Route>
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </ThemeProvider>
  );
}

export default App;
