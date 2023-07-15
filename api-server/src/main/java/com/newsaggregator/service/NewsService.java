package com.newsaggregator.service;

import java.util.Objects;

import com.newsaggregator.client.AnalysisServiceHttpClient;
import com.newsaggregator.client.NewsSourceServiceHttpClient;
import com.newsaggregator.model.response.NewsSourceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for News.
 */
@Service
public class NewsService {

    @Autowired
    private NewsSourceServiceHttpClient newsSourceServiceClient;
    @Autowired
    private AnalysisServiceHttpClient analysisServiceClient;

    /**
     * Method fetch news for given query.
     *
     * @param query         {@link String} representing query for which news needs to be fetched
     * @param shouldAnalyse {@link Boolean} representing whether the response should semantically
     *                                    analyse
     */
    public NewsSourceResponse fetchNews(String query, boolean shouldAnalyse) {
        NewsSourceResponse response = newsSourceServiceClient.fetchNews(query);
        if (response != null && shouldAnalyse) {
            response.setNews(response.getNews()
                .stream()
                .filter(Objects::nonNull)
                .peek(news -> {
                    news.setAnalysisResult(analysisServiceClient.analyseText(news.getText()));
                }).toList());
        }
        return response;
    }

}