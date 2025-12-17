package com.example.bookmark.dto;

import lombok.Data;

@Data
public class BookmarkRequestDTO {

    private String patientName;
    private String treatmentId;
    private String hospital;
    private String medicalCondition;
    private String insuranceProvider;
}
