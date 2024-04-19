package com.example.Course.Registration.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Set;

@Document(collection = "Courses")
public class Courses implements Comparable<Courses> {
    @Id
    private String id;
    private String title;
    private Integer CRN;
    private String semester;
    private Integer hours;
    private Integer enrollment;
    private String instructor;
    private Integer seats;
    private ClassTiming classTiming;

    public Courses() {
    }

    public Courses(String title, Integer CRN, String semester, Integer hours, Integer enrollment, String instructor, Integer seats, ClassTiming classTiming) {
        this.title = title;
        this.CRN = CRN;
        this.semester = semester;
        this.hours = hours;
        this.enrollment = enrollment;
        this.instructor = instructor;
        this.seats = seats;
        this.classTiming = classTiming;
    }

    @Override
    public String toString() {
        return id+","+title+","+CRN+","+semester+","+hours+","+enrollment+","+instructor+","+seats+","+classTiming.toString();
    }

    @Override
    public int compareTo(Courses o) {
        return this.CRN.compareTo(o.CRN);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCRN(Integer CRN) {
        this.CRN = CRN;
    }


    public void setClassTiming(ClassTiming classTiming) {
        this.classTiming = classTiming;
    }

    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public void setHours(Integer hours) {
        this.hours = hours;
    }
    
    public void setEnrollment(Integer enrollment) {
        this.enrollment = enrollment;
    }

    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    public void setSeats(Integer seats) {
        this.seats = seats;
    }
    
    public String getTitle() {
        return title;
    }
    
    public Integer getCRN() {
        return CRN;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public Integer getHours() {
        return hours;
    }
    
    public Integer getEnrollment() {
        return enrollment;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public ClassTiming getClassTiming() {
        return classTiming;
    }
    // public String getstartTime() {
        //     return startTime;
    // }

    // public String getendTime() {
    //     return endTime;
    // }

    // public String getclassDay() {
    //     return classDay;
    // }

    public Integer getSeats() {
        return seats;
    }


}
