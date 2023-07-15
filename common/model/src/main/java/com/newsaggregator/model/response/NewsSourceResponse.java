package com.newsaggregator.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsSourceResponse {

    List<News> news;

    @Data
    @Builder
    @Jacksonized
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class News {
        long id;
        String title;
        String text;
        String url;
        @JsonProperty("publish_date")
        String publishDate;
        String language;
        @JsonProperty("source_country")
        String sourceCountry;
        String author;
        AnalysisResult analysisResult;
    }

}