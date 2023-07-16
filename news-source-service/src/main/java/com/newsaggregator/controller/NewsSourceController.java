package com.newsaggregator.controller;

import com.newsaggregator.model.response.NewsSourceResponse;
import com.newsaggregator.service.NewsSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Rest Controller for news source APIs.
 */
@RestController
@RequestMapping("/api/news-source")
@Slf4j
public class NewsSourceController {

    private static final String BAD_REQUEST_MESSAGE = "query cannot be empty";
    @Autowired
    private NewsSourceService newsSourceService;

    /**
     * API to fetch news for given query.
     *
     * @param query {@link String} representing query for which news needs to be fetched
     * @return {@link ResponseEntity} containing {@link NewsSourceResponse} for the given query
     * @throws ResponseStatusException in case of any exception
     */
    @GetMapping("/fetch-news")
    public ResponseEntity<NewsSourceResponse> fetchNews(@RequestParam String query) {
        try {
            log.info("Received request to fetch news for query: {}", query);
            if (StringUtils.isEmpty(query)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
            }
            return ResponseEntity.ok(newsSourceService.fetchNews(query));
        } catch (ResponseStatusException e) {
            log.error("Error while fetching news for query: {}, error: {}", query, e.getMessage(),
                e);
            throw e;
        } catch (Exception e) {
            log.error("Error while fetching news for query: {}, error: {}", query, e.getMessage(),
                e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}