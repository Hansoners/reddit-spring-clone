package com.example.springredditdemo.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String s) {
            super(s);
    }
}
