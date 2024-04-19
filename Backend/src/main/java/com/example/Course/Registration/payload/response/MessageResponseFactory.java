package com.example.Course.Registration.payload.response;

public class MessageResponseFactory extends AbstractResponseFactory {
    @Override
    public AbstractResponse getResponse(String message) {
        return new MessageResponse(message);
    }
}
