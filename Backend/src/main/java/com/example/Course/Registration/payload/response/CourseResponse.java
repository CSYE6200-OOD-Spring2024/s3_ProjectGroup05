package com.example.Course.Registration.payload.response;
import com.example.Course.Registration.models.ClassTiming;

import java.util.Set;

public class CourseResponse extends AbstractResponse {
    private String id;
    private String title;
    private Integer CRN;
    private String semester;
    private Integer hours;
    private Integer enrollment;
    private String instructor;
    private Integer seats;
    private ClassTiming classTiming;

    public CourseResponse(String id, String title, Integer CRN, String semester, Integer hours, Integer enrollment, String instructor, Integer seats, ClassTiming classTiming) {
        this.id = id;
        this.title = title;
        this.CRN = CRN;
        this.semester = semester;
        this.hours = hours;
        this.enrollment = enrollment;
        this.instructor = instructor;
        this.seats = seats;
        this.classTiming = classTiming;
    }

    public CourseResponse(String message) {
        super();
        String[] tokens = message.split(",");
        this.id = tokens[0];
        this.title = tokens[1];
        this.CRN = Integer.parseInt(tokens[2]);
        this.semester = tokens[3];
        this.hours = Integer.parseInt(tokens[4]);
        this.enrollment = Integer.parseInt(tokens[5]);
        this.instructor = tokens[6];
        this.seats = Integer.parseInt(tokens[7]);
        String[] classTimingTokens = tokens[8].split("-");
        this.classTiming = new ClassTiming(classTimingTokens[0], classTimingTokens[1], classTimingTokens[2]);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCRN(Integer CRN) {
        this.CRN = CRN;
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

    public Integer getSeats() {
        return seats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClassTiming getClassTiming() {
        return classTiming;
    }

    public void setClassTiming(ClassTiming classTiming) {
        this.classTiming = classTiming;
    }
}
