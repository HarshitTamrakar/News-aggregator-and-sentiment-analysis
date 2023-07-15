package com.newsaggregator.client;

import java.net.URI;
import java.net.http.HttpRequest;

import com.newsaggregator.HttpClient;
import com.newsaggregator.model.request.AnalysisRequest;
import com.newsaggregator.model.response.AnalysisResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * {@link HttpClient} for making request to Analysis service.
 */
@Component
@Slf4j
public class AnalysisServiceHttpClient extends HttpClient {

    private static final String ANALYSIS_PATH = "/api/analysis";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String APPLICATION_JSON_VALUE = "application/json";
    private final String ANALYSIS_SERVICE_HOST;

    public AnalysisServiceHttpClient(
        @Value("${analysis.service.host}") String analysisServiceHost) {
        super();
        this.ANALYSIS_SERVICE_HOST = analysisServiceHost;
    }

    /**
     * Method to analyse text and produce {@link AnalysisResult}.
     *
     * @param text {@link String} representing text to be analysed
     * @return {@link AnalysisResult} for given input
     */
    public AnalysisResult analyseText(String text) {
        log.info("Received text: {} for analysis", text);
        try {
            String uriString = UriComponentsBuilder.fromHttpUrl(ANALYSIS_SERVICE_HOST)
                .path(ANALYSIS_PATH)
                .toUriString();
            URI uri = new URI(uriString);
            String jsonRequest = parseToJson(AnalysisRequest.builder()
                .text(text)
                .build());
            HttpRequest analysisRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE)
                .uri(uri)
                .build();
            return sendRequest(analysisRequest, AnalysisResult.class);
        } catch (Exception e) {
            log.error("Error in analysing text: {}, error: {}", text, e.getMessage(), e);
        }
        return null;
    }

}