package com.example.springredditdemo.service;

import com.example.springredditdemo.repository.PostRepository;
import com.example.springredditdemo.repository.VotingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteService {

    private final VotingRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional


}
