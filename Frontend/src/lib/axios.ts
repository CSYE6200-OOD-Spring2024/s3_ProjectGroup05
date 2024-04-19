// import { getSignOut, getToken } from "@/store/AuthStore";
// import axios from "axios";

// export const axiosInstance = axios.create(
//   {
//     baseURL: import.meta.env.VITE_SPRING_URL as string,
//     headers: {
//       "Content-Type": "application/json",
//     },
//   }
// );

// axiosInstance.interceptors.request.use(
//   async (config) => {
//     const token = getToken();
//     config.headers.set("Authorization", `Bearer ${token}`);
//     return config;
//   },
//   (error) => {
//     return Promise.reject(error);
//   }
// );

// axiosInstance.interceptors.response.use(
//   (response) => {
//     return response;
//   },
//   (error) => {
//     const signOut = getSignOut();
//     if (error.response.status === 401) {
//       signOut();
//     }
//     return Promise.reject(error);
//   }
// );