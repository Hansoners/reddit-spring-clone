package com.example.springredditdemo.repository;

import com.example.springredditdemo.models.Comment;
import com.example.springredditdemo.models.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Comment, Long> {
    Optional<Subreddit> findByName(String subreddit);
}
