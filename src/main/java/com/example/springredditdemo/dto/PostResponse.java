package com.example.springredditdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private long id;
    private long title;
    private long url;
    private long description;
    private String username;
    private String subredditName;
}
