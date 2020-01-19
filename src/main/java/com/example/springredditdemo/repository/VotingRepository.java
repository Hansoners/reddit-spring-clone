package com.example.springredditdemo.repository;

import com.example.springredditdemo.model.Post;
import com.example.springredditdemo.model.User;
import com.example.springredditdemo.model.Voting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotingRepository extends JpaRepository<Voting, Long> {
    Optional<Voting> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
