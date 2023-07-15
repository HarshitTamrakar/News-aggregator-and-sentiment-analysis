package com.newsaggregator;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsaggregator.model.request.AnalysisRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * Base class for Http client containing common methods.
 */
@Slf4j
public class HttpClient {

    private final java.net.http.HttpClient httpClient;

    protected HttpClient() {
        httpClient = java.net.http.HttpClient.newBuilder().build();
    }

    /**
     * Method to parse given {@link AnalysisRequest} to json String.
     *
     * @param analysisRequest {@link AnalysisRequest} that needs to be parsed as json
     * @return {@link String} representing json for given request
     */
    protected String parseToJson(AnalysisRequest analysisRequest) throws JsonProcessingException {
        log.debug("Parsing request: {} to json string", analysisRequest);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(analysisRequest);
    }

    /**
     * Method to send a {@link HttpRequest} and get response.
     *
     * @param request   {@link HttpRequest} that needs to be sent
     * @param classType target {@link Class} type for response
     */
    protected <T> T sendRequest(HttpRequest request, Class<T> classType) {
        log.debug("Sending request: {}", request);
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("Received response: {} for request: {}", response, request);
            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new IOException(response.body());
            }
            return parseResponse(response, classType);
        } catch (JsonProcessingException e) {
            log.error("Error parsing response: {} to class type: {}, error: {}", response,
                classType.getName(), e.getMessage(), e);
        } catch (IOException | InterruptedException e) {
            log.error("Error sending request: {}, received response: {}, error: {}", request,
                response, e.getMessage(), e);
        }
        return null;
    }

    /**
     * Method to parse response to given {@link Class} type.
     *
     * @param response  {@link HttpResponse} that needs to be parsed
     * @param classType target {@link Class} type for response
     */
    protected <T> T parseResponse(HttpResponse<String> response, Class<T> classType)
        throws JsonProcessingException {
        log.debug("Parsing response: {} to class type: {}", response, classType.getName());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), classType);
    }

}