package com.example.bookmark.exception;

public class BookmarkNotFoundException extends RuntimeException {
    public BookmarkNotFoundException(String msg) {
        super(msg);
    }
}
