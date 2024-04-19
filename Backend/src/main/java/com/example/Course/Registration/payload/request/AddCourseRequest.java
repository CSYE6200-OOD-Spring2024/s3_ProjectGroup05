package com.example.Course.Registration.payload.request;

import java.util.List;  
import com.example.Course.Registration.models.Courses;

public class AddCourseRequest extends Request {
    private List<Courses> courses;

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    
}
