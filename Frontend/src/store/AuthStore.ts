
import { User } from "@/types/user";
import { createStore, useStore } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";
import { getReset } from "./CoureseStore";
import { getAllCourseReset } from "./AllCourseStore";

export interface authStoreType {
  token: string;
  user: User;
  signIn: (data: { token: string; user: User; }) => void;
  signOut: () => void;
}

const resetCourse = getReset();
const resetAllCourse = getAllCourseReset();

const authStore = createStore<authStoreType>()(
  persist(
    (set) => ({
      token: '',
      user: {
        id: '',
        name: '',
        email: '',
        role: null,
      },
      signIn: (data) => {
        set(() => ({
          token: data.token,
          user: data.user,
        }))
      },
      signOut: () => {
        resetCourse();
        resetAllCourse();
        set(() => ({
          token: '',
          user: {
            id: '',
            name: '',
            email: '',
            role: null,
            courses: [],
          },
        }))
      },
    }),
    {
      name: "auth-storage",
      storage: createJSONStorage(() => sessionStorage),
    }
  )
)

export type ExtractState<S> = S extends {
  getState: () => infer T;
}
? T
: never;

//selector
const tokenSelector = (state: ExtractState<typeof authStore>) => state.token;
const userSelector = (state: ExtractState<typeof authStore>) => state.user;
const signInSelector = (state: ExtractState<typeof authStore>) => state.signIn;
const signOutSelector = (state: ExtractState<typeof authStore>) => state.signOut;

//getter
export const getToken = () => tokenSelector(authStore.getState());
export const getUser = () => userSelector(authStore.getState());
export const getSignIn = () => signInSelector(authStore.getState());
export const getSignOut = () => signOutSelector(authStore.getState());

//hooks
export const useToken = () => useStore(authStore, tokenSelector);
export const useUser = () => useStore(authStore, userSelector);
export const useSignIn = () => useStore(authStore, signInSelector);
export const useSignOut = () => useStore(authStore, signOutSelector);

