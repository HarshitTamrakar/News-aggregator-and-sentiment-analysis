package com.newsaggregator.service;

import com.newsaggregator.client.OpenAIClient;
import com.newsaggregator.model.response.AnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for analysis.
 */
@Service
public class AnalysisService {

    @Autowired
    private OpenAIClient openAIClient;

    /**
     * Method to analyse given text.
     *
     * @param text {@link String} representing text to be analysed
     * @return {@link AnalysisResult} for the given request
     */
    public AnalysisResult analyse(String text) {
        return openAIClient.analyseText(text);
    }

}