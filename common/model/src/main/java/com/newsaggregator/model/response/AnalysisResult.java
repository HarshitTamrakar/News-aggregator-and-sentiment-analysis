package com.newsaggregator.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisResult {

    @Builder.Default
    String positive = "0%";
    @Builder.Default
    String negative = "0%";
    @Builder.Default
    String neutral = "0%";
    String message;

}