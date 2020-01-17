package com.example.springredditdemo.models;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;
    VoteType(int vote) {}
}
