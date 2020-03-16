package com.example.springredditdemo.service;

import com.example.springredditdemo.dto.CommentsDto;
import com.example.springredditdemo.exception.PostNotFoundException;
import com.example.springredditdemo.mapper.CommentMapper;
import com.example.springredditdemo.model.Comment;
import com.example.springredditdemo.model.Post;
import com.example.springredditdemo.model.User;
import com.example.springredditdemo.repository.CommentRepository;
import com.example.springredditdemo.repository.PostRepository;
import com.example.springredditdemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentsService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public void createComment(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);
    }

    public List<CommentsDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository
                .findByPost(post)
                .stream()
                .map(commentMapper::maptoDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDto> getCommentsByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository
                .findAllByUser(user)
                .stream()
                .map(commentMapper::maptoDto)
                .collect(Collectors.toList());
    }
}
