package com.example.springredditdemo.model;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;
    VoteType(int vote) {}
}
