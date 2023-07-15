package com.newsaggregator.client;

import java.net.URI;
import java.net.http.HttpRequest;

import com.newsaggregator.HttpClient;
import com.newsaggregator.model.response.NewsSourceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * {@link HttpClient} for making request to News Source service.
 */
@Component
@Slf4j
public class NewsSourceServiceHttpClient extends HttpClient {

    private static final String FETCH_NEWS_PATH = "/api/news-source/fetch-news";
    private static final String QUERY = "query";
    private final String NEWS_SOURCE_SERVICE_HOST;

    public NewsSourceServiceHttpClient(
        @Value("${news.source.service.host}") String newsSourceServiceHost) {
        super();
        this.NEWS_SOURCE_SERVICE_HOST = newsSourceServiceHost;
    }

    /**
     * Method to fetch news for given query.
     *
     * @param query {@link String} representing keyword for which news needs to be searched
     * @return {@link NewsSourceResponse} containing response for query
     */
    public NewsSourceResponse fetchNews(String query) {
        try {
            String uriString = UriComponentsBuilder.fromHttpUrl(NEWS_SOURCE_SERVICE_HOST)
                .path(FETCH_NEWS_PATH)
                .queryParam(QUERY, query)
                .toUriString();
            URI uri = new URI(uriString);
            HttpRequest fetchNewsRequest = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
            log.debug("Sending request: {}", fetchNewsRequest);
            return sendRequest(fetchNewsRequest, NewsSourceResponse.class);
        } catch (Exception e) {
            log.error("Error fetching news for query: {}, error: {}", query, e.getMessage(), e);
        }
        return null;
    }

}