package com.example.springredditdemo.repository;

import com.example.springredditdemo.models.Post;
import com.example.springredditdemo.models.Subreddit;
import com.example.springredditdemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
