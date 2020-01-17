package com.example.springredditdemo.repository;

import com.example.springredditdemo.models.Comment;
import com.example.springredditdemo.models.Post;
import com.example.springredditdemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
