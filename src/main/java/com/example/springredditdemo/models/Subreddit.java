package com.example.springredditdemo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
class Subreddit {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private long id;
    @NotBlank(message = "Subreddit name is required")
    private String subredditName;
    @NotBlank(message = "Subreddit description is required")
    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> postsList;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
