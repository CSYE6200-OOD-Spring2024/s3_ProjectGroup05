/* eslint-disable react-hooks/exhaustive-deps */

import Navbar from "./Navbar";
import Table from "./CourseTable/Table";
import { courseTable } from "@/types/courseTable";
import { useEffect } from "react";
import { useToken } from "@/store/AuthStore";
import axios from "axios";
import { useMutation } from "react-query";
import Sidebar from "./Sidebar";
import { course } from "@/types/course";
import {
  getAddAllCourse,
  getAllCourseReset,
  useAllCourse,
} from "@/store/AllCourseStore";
import moment from "moment";

export default function UserDashboard() {
  const token = useToken();
  const allCourses: courseTable[] = useAllCourse();
  const allCourseReset = getAllCourseReset();
  const addAllCourse = getAddAllCourse();

  const { mutate: getTableData } = useMutation({
    mutationFn: async () => {
      const { data } = await axios.get(
        import.meta.env.VITE_SPRING_URL + "/api/courses/getAllCourses",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return data;
    },
    onError: (error) => {
      console.log(error);
    },
    onSuccess: (data) => {
      console.log(data);
      const courses: courseTable[] = data.map((course: course) => {
        return {
          title: course.title,
          crn: course.crn,
          semester: course.semester,
          hours: course.hours,
          enrolled: course.enrollment,
          seats: course.seats,
          instructor: course.instructor,
          time: `${course.classTiming.day} ${course.classTiming.startTime} - ${course.classTiming.endTime}`,

          // time: `${course.classTiming.day} ${moment(new Date(course.classTiming.startTime).toISOString()).format('LT')} - ${moment(new Date(course.classTiming.endTime).toISOString()).format('LT')}`,
        };
      });
      allCourseReset();
      addAllCourse(courses);
    },
  });

  useEffect(() => {
    // get courses
    getTableData();
  }, []);

  return (
    <div className="h-screen">
      <Navbar />
      <div className="flex h-full">
        <Sidebar />
        <Table data={allCourses} from="userDashboard" />
      </div>
    </div>
  );
}
