package com.example.treatment.controller;

import com.example.treatment.dto.Treatment;
import com.example.treatment.service.TreatmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentController {

    private final TreatmentService service;

    public TreatmentController(TreatmentService service) {
        this.service = service;
    }

    /**
     * GET /treatments
     * GET /treatments?property=value
     */
    @GetMapping
    public List<Treatment> getTreatments(
            @RequestParam(required = false) Map<String, String> params) {

        if (params.isEmpty()) {
            return service.getAllTreatments();
        }

        // Only one filter parameter supported
        String key = params.keySet().iterator().next();
        String value = params.get(key);

        return service.filterTreatments(key, value);
    }

    /**
     * POST /treatments
     */
    @PostMapping
    public Treatment createTreatment(@RequestBody Treatment treatment) {
        return service.createTreatment(treatment);
    }
}
