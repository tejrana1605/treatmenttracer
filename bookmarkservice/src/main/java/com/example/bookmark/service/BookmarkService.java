package com.example.bookmark.service;

import com.example.bookmark.dto.BookmarkRequestDTO;
import com.example.bookmark.exception.BookmarkNotFoundException;
import com.example.bookmark.model.Bookmark;
import com.example.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository repository;

    public Bookmark save(String userId, BookmarkRequestDTO dto) {

        Bookmark bookmark = Bookmark.builder()
                .patientName(dto.getPatientName())
                .userId(userId)
                .treatmentId(dto.getTreatmentId())
                .hospital(dto.getHospital())
                .medicalCondition(dto.getMedicalCondition())
                .insuranceProvider(dto.getInsuranceProvider())
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(bookmark);
    }

    public List<Bookmark> getUserBookmarks(String userId) {
        List<Bookmark> list = repository.findByUserId(userId);
        if (list.isEmpty()) {
            throw new BookmarkNotFoundException("No bookmarks found");
        }
        return list;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
