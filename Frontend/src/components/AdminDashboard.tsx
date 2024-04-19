/* eslint-disable react-hooks/exhaustive-deps */

import Navbar from "./Navbar";
import Table from "./CourseTable/Table";
import { courseTable } from "@/types/courseTable";
import { useEffect } from "react";
import { useToken } from "@/store/AuthStore";
import axios from "axios";
import { useMutation } from "react-query";
import { course } from "@/types/course";
import moment from "moment";
import { getReset, useAddCourse, useCourse } from "@/store/CoureseStore";

export default function AdminDashboard() {
  const token = useToken();
  const course: courseTable[] = useCourse();
  const addCourse = useAddCourse();
  const reset = getReset();

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
      reset();
      data.map((course: course) => {
        addCourse({
          title: course.title,
          crn: course.crn,
          semester: course.semester,
          hours: course.hours,
          enrolled: course.enrollment,
          seats: course.seats,
          instructor: course.instructor,
          time: `${course.classTiming.day} ${course.classTiming.startTime} - ${course.classTiming.endTime}`,
        });
      });
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
        <Table data={course} from="adminDashboard" />
      </div>
    </div>
  );
}
