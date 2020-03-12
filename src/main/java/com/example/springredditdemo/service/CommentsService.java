package com.example.springredditdemo.service;

import com.example.springredditdemo.mapper.CommentMapper;
import com.example.springredditdemo.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentsService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

}
