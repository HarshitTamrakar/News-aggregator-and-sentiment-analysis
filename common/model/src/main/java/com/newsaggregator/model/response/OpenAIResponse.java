package com.newsaggregator.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIResponse {

    String id;
    String object;
    long created;
    String model;
    List<Choice> choices;

    @Jacksonized
    @Value
    @Builder
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {

        int index;
        Message message;

        @Jacksonized
        @Value
        @Builder
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Message {

            String role;
            String content;

        }

    }

}
