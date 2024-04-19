package com.example.Course.Registration.payload.response;

public class CourseResponseFactory extends AbstractResponseFactory {
    @Override
    public AbstractResponse getResponse(String message) {
        return new CourseResponse(message);
    }
}
