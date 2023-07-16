package com.newsaggregator.controller;

import com.newsaggregator.model.request.AnalysisRequest;
import com.newsaggregator.model.response.AnalysisResult;
import com.newsaggregator.service.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Rest Controller for analysis APIs.
 */
@RestController
@RequestMapping("/api/analysis")
@Slf4j
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    /**
     * API to analyse {@link AnalysisRequest}.
     *
     * @param analysisRequest {@link AnalysisRequest} that needs to be analysed
     * @return {@link ResponseEntity} containing {@link AnalysisResult} for the given request
     * @throws ResponseStatusException in case of any error
     */
    @PostMapping
    public ResponseEntity<AnalysisResult> analyse(@RequestBody AnalysisRequest analysisRequest) {
        try {
            log.info("Received request: {} to analyse", analysisRequest);
            if (analysisRequest == null || StringUtils.isEmpty(analysisRequest.getText())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Text cannot be empty");
            }
            return ResponseEntity.ok(analysisService.analyse(analysisRequest.getText()));
        } catch (ResponseStatusException e) {
            log.error("Error analysing request: {}, error: {}", analysisRequest, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error analysing request: {}, error: {}", analysisRequest, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}