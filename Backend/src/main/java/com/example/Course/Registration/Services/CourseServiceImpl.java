package com.example.Course.Registration.Services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.example.Course.Registration.models.Courses;
import com.example.Course.Registration.repository.CourseRepository;
// import com.example.Course.Registration.Repositorxy.CourseRepository;
@Service
public class CourseServiceImpl implements CourseService {

    private  CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Courses> getCourses(){
        return courseRepository.findAll();
    }

    @Override
    public Courses getCourseById(String id){
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Courses> getCourseByTitle(String title){
        // return courseRepository.findByTitle(title);
        List<Courses> res = courseRepository.findByTitle(title);
        return !res.isEmpty() ?res:null;
    }

    @Override
    public Courses getCourseByCRN(Integer CRN){
        List<Courses> res = courseRepository.findByCRN(CRN);
        return !res.isEmpty() ?res.get(0):null;
    }

    @Override
    public List<Courses> getCourseBySemester(String semester){
        return courseRepository.findBySemester(semester);
    }


    @Override
    public List<Courses> getCourseByInstructor(String instructor){
        // return courseRepository.findByInstructor(instructor);
        List<Courses> res = courseRepository.findByInstructor(instructor);
        return !res.isEmpty() ?res:null;
    }

    // public Courses getCourseBySeats(Integer seats){
    //     return courseRepository.findBySeats(seats).get(0);
    // }

    @Override
    public Courses addCourse(Courses course){
        return courseRepository.save(course);
    }

    @Override
    public Courses deleteCourse(Courses course){
        courseRepository.delete(course);
        return course;
    }

    

    
}
