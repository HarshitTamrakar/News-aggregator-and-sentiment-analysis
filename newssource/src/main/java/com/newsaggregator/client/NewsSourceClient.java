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
 * {@link HttpClient} for making request to News Source service
 */
@Component
@Slf4j
public class NewsSourceClient extends HttpClient {

    private static final String SEARCH_NEWS_URI = "/search-news";
    private static final String TEXT_QUERY_PARAM = "text";
    private static final String NUMBER_QUERY_PARAM = "number";
    private static final String SORT_QUERY_PARAM = "sort";
    private static final String SORT_DIRECTION_QUERY_PARAM = "sort-direction";
    private static final String PUBLISH_TIME_QUERY_PARAM_VALUE = "publish-time";
    private static final String DESC_QUERY_PARAM_VALUE = "DESC";
    private static final String X_API_KEY_HEADER = "x-api-key";
    private final String NEWS_SOURCE_SERVER_HOST;
    private final String NEWS_SOURCE_API_KEY;

    public NewsSourceClient(@Value("${news.source.host.name}") String newsSourceHost,
                            @Value("${news.source.api.key}") String newsSourceApiKey) {
        super();
        this.NEWS_SOURCE_API_KEY = newsSourceApiKey;
        this.NEWS_SOURCE_SERVER_HOST = newsSourceHost;
    }

    /**
     * Method to fetch news for given query
     *
     * @param query {@link String} representing query for which news needs to be fetched
     * @return {@link NewsSourceResponse} representing response for given query
     */
    public NewsSourceResponse fetchNews(String query) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(NEWS_SOURCE_SERVER_HOST)
                .path(SEARCH_NEWS_URI)
                .queryParam(TEXT_QUERY_PARAM, query)
                .queryParam(NUMBER_QUERY_PARAM, 1)
                .queryParam(SORT_QUERY_PARAM, PUBLISH_TIME_QUERY_PARAM_VALUE)
                .queryParam(SORT_DIRECTION_QUERY_PARAM, DESC_QUERY_PARAM_VALUE)
                .toUriString();
            URI uri = new URI(url);
            HttpRequest fetchNewsRequest = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header(X_API_KEY_HEADER, NEWS_SOURCE_API_KEY)
                .build();
            log.debug("Sending request: {}", fetchNewsRequest);
            return sendRequest(fetchNewsRequest, NewsSourceResponse.class);
        } catch (Exception e) {
            log.error("Error fetching news for query: {}, error: {}", query, e.getMessage(), e);
        }
        return null;
    }

}