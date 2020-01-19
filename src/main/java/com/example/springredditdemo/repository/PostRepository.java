package com.example.springredditdemo.repository;

import com.example.springredditdemo.model.Post;
import com.example.springredditdemo.model.Subreddit;
import com.example.springredditdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
