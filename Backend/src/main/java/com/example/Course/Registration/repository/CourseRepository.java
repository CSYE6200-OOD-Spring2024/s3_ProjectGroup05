package com.example.Course.Registration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

import com.example.Course.Registration.models.Courses;

public interface CourseRepository extends MongoRepository<Courses, String> {
    List<Courses> findByTitle(String title);
    List<Courses> findByCRN(Integer CRN);
    List<Courses> findBySemester(String semester);
    // List<Courses> findByHours(Integer hours);
    // List<Courses> findByEnrollment(String enrollment);
    List<Courses> findByInstructor(String instructor);
    // List<Courses> findBySeats(Integer seats);

}