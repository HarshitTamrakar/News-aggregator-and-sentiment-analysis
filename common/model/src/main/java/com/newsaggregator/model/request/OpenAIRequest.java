package com.newsaggregator.model.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class OpenAIRequest {

    String model;
    List<Message> messages;
    int temperature;

    @Value
    @Builder
    @AllArgsConstructor
    public static class Message {
        String role;
        String content;
    }

}