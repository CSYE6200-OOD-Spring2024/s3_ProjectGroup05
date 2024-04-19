package com.example.Course.Registration.Services;

import com.example.Course.Registration.models.Courses;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CourseService {
    List<Courses> getCourses();

    Courses getCourseById(String id);

    List<Courses> getCourseByTitle(String title);

    Courses getCourseByCRN(Integer CRN);

    List<Courses> getCourseBySemester(String semester);

    List<Courses> getCourseByInstructor(String instructor);

    Courses addCourse(Courses course);

    Courses deleteCourse(Courses course);
}
