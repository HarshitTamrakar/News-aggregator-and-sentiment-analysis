package com.newsaggregator.service;

import com.newsaggregator.client.NewsSourceClient;
import com.newsaggregator.model.response.NewsSourceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for fetching news.
 */
@Service
@Slf4j
public class NewsSourceService {

    @Autowired
    private NewsSourceClient newsSourceClient;

    /**
     * Method to fetch news for given query
     *
     * @param query {@link String} representing query for which news needs to be fetched
     * @return {@link NewsSourceResponse} for the given query
     */
    public NewsSourceResponse fetchNews(String query) {
        return newsSourceClient.fetchNews(query);
    }

}