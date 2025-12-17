package com.example.treatment.service;

import com.example.treatment.dto.Treatment;
import com.example.treatment.exception.ExternalApiException;
import com.example.treatment.exception.TreatmentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class TreatmentService {

    private final WebClient webClient;

    public TreatmentService(WebClient webClient) {
        this.webClient = webClient;
    }

    // GET /treatments
    public List<Treatment> getAllTreatments() {
        try {
            List<Treatment> treatments = webClient.get()
                    .uri("/treatments")
                    .retrieve()
                    .bodyToFlux(Treatment.class)
                    .collectList()
                    .block();

            if (treatments == null || treatments.isEmpty()) {
                throw new TreatmentNotFoundException("No treatment records found");
            }
            return treatments;

        } catch (WebClientResponseException e) {
            throw new ExternalApiException("Failed to fetch treatments from external API");
        }
    }

    // GET /treatments?property=value
    public List<Treatment> filterTreatments(String key, String value) {
        try {
            List<Treatment> treatments = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/treatments")
                            .queryParam(key, value)
                            .build())
                    .retrieve()
                    .bodyToFlux(Treatment.class)
                    .collectList()
                    .block();

            if (treatments == null || treatments.isEmpty()) {
                throw new TreatmentNotFoundException(
                        "No treatment found for " + key + " = " + value);
            }
            return treatments;

        } catch (WebClientResponseException e) {
            throw new ExternalApiException("Error while filtering treatments");
        }
    }

    // POST /treatments
    public Treatment createTreatment(Treatment treatment) {
        try {
            return webClient.post()
                    .uri("/treatments")
                    .bodyValue(treatment)
                    .retrieve()
                    .bodyToMono(Treatment.class)
                    .block();

        } catch (WebClientResponseException e) {
            throw new ExternalApiException("Failed to create treatment record");
        }
    }
}
