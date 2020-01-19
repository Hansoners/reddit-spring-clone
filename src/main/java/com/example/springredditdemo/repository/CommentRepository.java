package com.example.springredditdemo.repository;

import com.example.springredditdemo.model.Comment;
import com.example.springredditdemo.model.Post;
import com.example.springredditdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
