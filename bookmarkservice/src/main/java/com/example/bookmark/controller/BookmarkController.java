package com.example.bookmark.controller;

import com.example.bookmark.dto.BookmarkRequestDTO;
import com.example.bookmark.model.Bookmark;
import com.example.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmark")
//@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService service;

    public BookmarkController(BookmarkService service){
        this.service=service;
    }

    @PostMapping
    public Bookmark saveBookmark(
            @RequestParam(required = false) String userId,
            @RequestBody BookmarkRequestDTO dto) {

        return service.save(userId, dto);
    }

    @GetMapping
    public List<Bookmark> getBookmarks(
            @RequestParam(required = false) String userId) {

        return service.getUserBookmarks(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteBookmark(@PathVariable Long id) {
        service.delete(id);
    }
}
