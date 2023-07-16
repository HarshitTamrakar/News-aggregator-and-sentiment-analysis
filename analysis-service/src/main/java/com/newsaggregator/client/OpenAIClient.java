package com.newsaggregator.client;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.newsaggregator.HttpClient;
import com.newsaggregator.model.response.AnalysisResult;
import com.newsaggregator.model.response.OpenAIResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * {@link HttpClient} for making request to OpenAI servers.
 */
@Component
@Slf4j
public class OpenAIClient extends HttpClient {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String APPLICATION_JSON_HEADER_VALUE = "application/json";
    private static final String LINE_BREAK_CHARACTER = "\n";
    private static final String REGEX_FOR_PERCENTAGE = "(\\d+)%";
    private static final String POSITIVE = "Positive";
    private static final String NEGATIVE = "Negative";
    private static final String NEUTRAL = "Neutral";
    private static final String DEFAULT_PERCENT = "0%";
    private final String OPEN_AI_API_KEY;
    private final String OPEN_AI_API_HOST;

    public OpenAIClient(@Value("${open.ai.api.key}") String apiKey,
                        @Value("${open.ai.host}") String host) {
        super();
        this.OPEN_AI_API_KEY = apiKey;
        this.OPEN_AI_API_HOST = host;
    }

    /**
     * Method to analyse text given as input.
     *
     * @param text {@link String} representing text to be analysed
     * @return {@link AnalysisResult} for given text
     */
    public AnalysisResult analyseText(String text) {
        try {
            String uri = UriComponentsBuilder.fromHttpUrl(OPEN_AI_API_HOST)
                .toUriString();
            URI url = new URI(uri);
            String payload = getPayload(text);
            HttpRequest.BodyPublisher requestBody = HttpRequest.BodyPublishers.ofString(payload);
            HttpRequest analysisRequest = HttpRequest.newBuilder()
                .POST(requestBody)
                .uri(url)
                .header(AUTHORIZATION_HEADER, BEARER + OPEN_AI_API_KEY)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_HEADER_VALUE)
                .build();
            log.debug("Sending request: {}", analysisRequest);
            OpenAIResponse response = sendRequest(analysisRequest, OpenAIResponse.class);
            return getAnalysisResult(response);
        } catch (Exception e) {
            log.error("Error in analysing text: {}, error: {}", text, e.getMessage(), e);
        }
        return null;
    }

    /**
     * Method to build payload for request.
     *
     * @param text {@link String} representing text to be analysed.
     */
    private String getPayload(String text) {
        return "{\n"
            + "  \"model\": \"gpt-3.5-turbo\",\n"
            + "  \"messages\": [{\"role\": \"system\", \"content\": \"You are an AI language "
            + "model trained to analyze and detect the sentiment of articles. Return only "
            + "percentage of Positive, Negative and Neutral sentiments in the article.\"}, \n"
            + "  {\"role\": \"user\", \"content\": \"Article: " + text + "\"}],\n"
            + "  \"temperature\": 1\n"
            + "}";
    }

    /**
     * Method to produce {@link com.newsaggregator.model.request.AnalysisRequest} from
     * {@link OpenAIResponse}
     *
     * @param openAIResponse {@link OpenAIResponse} representing response from OpenAI sever
     * @return {@link AnalysisResult} built from {@link OpenAIResponse}
     */
    private AnalysisResult getAnalysisResult(OpenAIResponse openAIResponse) {
        log.debug("Analysing response: {}", openAIResponse);
        try {
            String content = Optional.ofNullable(openAIResponse)
                .map(OpenAIResponse::getChoices)
                .map(choices -> choices.get(0))
                .map(OpenAIResponse.Choice::getMessage)
                .map(OpenAIResponse.Choice.Message::getContent)
                .orElse(null);
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            String[] lines = content.split(LINE_BREAK_CHARACTER);
            Pattern pattern = Pattern.compile(REGEX_FOR_PERCENTAGE);
            AnalysisResult.AnalysisResultBuilder analysisResultBuilder = AnalysisResult.builder();
            for (String line : lines) {
                if (line.contains(POSITIVE)) {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        analysisResultBuilder.positive(matcher.group(1));
                    }
                } else if (line.contains(NEGATIVE)) {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        analysisResultBuilder.negative(matcher.group(1));
                    }
                } else if (line.contains(NEUTRAL)) {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        analysisResultBuilder.neutral(matcher.group(1));
                    }
                }
            }
            AnalysisResult analysisResult = analysisResultBuilder.build();
            if (analysisResult.getPositive().equals(DEFAULT_PERCENT)
                && analysisResult.getNegative().equals(DEFAULT_PERCENT)
                && analysisResult.getNeutral().equals(DEFAULT_PERCENT)) {
                analysisResult.setMessage(content);
            }
            log.debug("Analysed result: {} for response: {}", analysisResult, openAIResponse);
            return analysisResult;
        } catch (Exception e) {
            log.error("Error generating analysis result for given response: {}, error: {}",
                openAIResponse, e.getMessage(), e);
        }
        return null;
    }

}