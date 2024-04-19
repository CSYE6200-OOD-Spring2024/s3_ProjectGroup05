package com.example.Course.Registration.models;

public class ClassTiming {

    private String startTime;
    private String endTime;
    private String day;

    public ClassTiming(String startTime, String endTime, String day) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    @Override
    public String toString() {
        return startTime + "-" + endTime + "-" + day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setday(String day) {
        this.day = day;
    }

}
