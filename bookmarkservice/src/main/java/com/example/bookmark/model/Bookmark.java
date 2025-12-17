package com.example.bookmark.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "bookmarks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patientName;
    private String userId;          // from JWT
    private String treatmentId;     // external treatment id
    private String hospital;
    private String medicalCondition;
    private String insuranceProvider;

    private LocalDateTime createdAt;
}
