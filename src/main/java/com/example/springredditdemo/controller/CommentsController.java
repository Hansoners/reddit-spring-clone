package com.example.springredditdemo.controller;

import com.example.springredditdemo.dto.CommentsDto;
import com.example.springredditdemo.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentsService.createComment(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentsDto>> getAllCommentsByPost(@RequestParam("postId") Long postId) {
        return status(HttpStatus.OK).body(commentsService.getCommentsByPost(postId));
    }

    @GetMapping
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@RequestParam("user") String user) {
        return status(HttpStatus.OK).body(commentsService.getCommentsByUser(user));
    }
}
