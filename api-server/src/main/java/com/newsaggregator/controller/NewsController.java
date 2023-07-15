package com.newsaggregator.controller;

import com.newsaggregator.model.response.NewsSourceResponse;
import com.newsaggregator.service.NewsService;
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
 * Rest controller for News APIs.
 */
@RestController
@RequestMapping("/api/news")
@Slf4j
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * API to search news for given query.
     *
     * @param query             {@link String} representing query for which news needs to be
     *                          searched
     * @param sentimentAnalysis {@link Boolean} representing weather news content needs to be
     *                          sentimentally analysed
     *                          if true: it will populate
     *                          {@link com.newsaggregator.model.response.AnalysisResult} in
     *                          {@link NewsSourceResponse}
     * @throws ResponseStatusException in case of any exceptions
     */
    @GetMapping("/search")
    public ResponseEntity<NewsSourceResponse> searchNews(@RequestParam String query,
                                                         @RequestParam(defaultValue = "false")
                                                         Boolean sentimentAnalysis) {
        try {
            log.info("Received request for search news for query: {}, sentimentAnalysis: {}", query,
                sentimentAnalysis);
            if (StringUtils.isEmpty(query)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Query cannot be empty");
            }
            return ResponseEntity.ok(newsService.fetchNews(query, sentimentAnalysis));
        } catch (Exception e) {
            log.error("Error searching news for query: {}, error: {}", query, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}