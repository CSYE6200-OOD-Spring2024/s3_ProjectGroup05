package com.example.Course.Registration.payload.response;

public class JwtResponseFactory extends AbstractResponseFactory {
    @Override
    public AbstractResponse getResponse(String message) {
        return new JwtResponse(message);
    }
}
