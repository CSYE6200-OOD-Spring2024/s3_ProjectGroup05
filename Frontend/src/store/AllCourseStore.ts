
import { courseTable } from "@/types/courseTable";
import { createStore, useStore } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";

interface AllCourseStore {
  allCourses: courseTable[];
  addAllCourse: (course: courseTable[]) => void;
  incrementAllEnrolled: (crn: number[]) => void;
  decrementAllEnrolled: (crn: number[]) => void;
  reset: () => void;
}

const allCourseStore = createStore<AllCourseStore>()(
  persist(
    (set) => ({
      allCourses: [],
      addAllCourse: (course: courseTable[]) => set((state) => ({
        allCourses: [...state.allCourses, ...course]
      })),
      incrementAllEnrolled: (crn: number[]) => set((state) => ({
        allCourses: state.allCourses.map((c) => {
          if (crn.includes(c.crn)) {
            return {
              ...c,
              enrolled: c.enrolled + 1,
            };
          }
          return c;
        })
      })),
      decrementAllEnrolled: (crn: number[]) => set((state) => ({
        allCourses: state.allCourses.map((c) => {
          if (crn.includes(c.crn)) {
            return {
              ...c,
              enrolled: c.enrolled - 1,
            };
          }
          return c;
        })
      })),
      reset: () => set(() => ({ allCourses: [] })),
    }),
    {
      name: "all-course-storage",
      storage: createJSONStorage(() => sessionStorage),
    }
  )
)

//selector
const allCoursesSelector = (state: AllCourseStore) => state.allCourses;
const addAllCourseSelector = (state: AllCourseStore) => state.addAllCourse;
const allCourseResetSelector = (state: AllCourseStore) => state.reset;
const incrementAllEnrolledSelector = (state: AllCourseStore) => state.incrementAllEnrolled;
const decrementAllEnrolledSelector = (state: AllCourseStore) => state.decrementAllEnrolled;

//getter
export const getAllCourses = () => allCoursesSelector(allCourseStore.getState());
export const getAddAllCourse = () => addAllCourseSelector(allCourseStore.getState());
export const getAllCourseReset = () => allCourseResetSelector(allCourseStore.getState());
export const getIncrementAllEnrolled = () => incrementAllEnrolledSelector(allCourseStore.getState());
export const getDecrementAllEnrolled = () => decrementAllEnrolledSelector(allCourseStore.getState());

//hooks
export const useAllCourse = () => useStore(allCourseStore, allCoursesSelector);
export const useAddAllCourse = () => useStore(allCourseStore, addAllCourseSelector);
export const useAllCourseReset = () => useStore(allCourseStore, allCourseResetSelector);
export const useIncrementAllEnrolled = () => useStore(allCourseStore, incrementAllEnrolledSelector);
export const useDecrementAllEnrolled = () => useStore(allCourseStore, decrementAllEnrolledSelector);