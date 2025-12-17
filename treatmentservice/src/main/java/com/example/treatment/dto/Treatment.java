package com.example.treatment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Treatment {

    @JsonProperty("id")
    private String id;

    @NotBlank(message = "Patient name must not be blank")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Age must be provided")
    @JsonProperty("age")
    private Integer age;

    @NotBlank(message = "Gender must not be blank")
    @JsonProperty("gender")
    private String gender;

    @JsonProperty("blood_type")
    private String bloodType;

    @JsonProperty("medical_Condition")
    private String medicalCondition;

    @JsonProperty("date_admission")
    private String dateAdmission;

    @NotBlank(message = "Doctor name must not be blank")
    @JsonProperty("doctor")
    private String doctor;

    @NotBlank(message = "Hospital name must not be blank")
    @JsonProperty("hospital")
    private String hospital;

    @JsonProperty("insurance_provider")
    private String insuranceProvider;

    @JsonProperty("billing_amount")
    private Double billingAmount;

    @JsonProperty("room_number")
    private Integer roomNumber;

    @JsonProperty("admission_type")
    private String admissionType;

    @JsonProperty("discharge_date")
    private String dischargeDate;

    @NotBlank(message = "Medication must not be blank")
    @JsonProperty("medication")
    private String medication;

    @JsonProperty("test_results")
    private String testResults;
}
