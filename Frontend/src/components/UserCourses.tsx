import Navbar from "./Navbar";
import Table from "./CourseTable/Table";
import axios from "axios";
import { useMutation } from "react-query";
import { useToken } from "@/store/AuthStore";
import { useEffect } from "react";
import { courseTable } from "@/types/courseTable";
import Sidebar from "./Sidebar";
import { course } from "@/types/course";
import { getReset, useAddCourse, useCourse } from "@/store/CoureseStore";
import moment from "moment";

export default function UserCourses() {
  const courses: courseTable[] = useCourse();
  const addCourse = useAddCourse();
  const reset = getReset();
  const token = useToken();

  const { mutate: getTableData } = useMutation({
    mutationFn: async () => {
      console.log("calling");
      const { data } = await axios.get(
        import.meta.env.VITE_SPRING_URL + "/api/courses/userCourses",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log(data);
      return data;
    },
    onError: (error) => {
      console.log(error);
    },
    onSuccess: (data) => {
      console.log(data);
      reset();
      data.forEach((course: course) => {
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
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className="h-screen">
      <Navbar />
      <div className="flex h-full">
        <Sidebar />
        <Table data={courses} from="drop" />
      </div>
    </div>
  );
}
