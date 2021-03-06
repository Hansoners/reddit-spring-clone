package com.example.springredditdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.time.Instant;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private long userId;
    @NotBlank(message = "Please enter a username")
    private String username;
    @NotBlank(message = "Please enter a password")
    private String password;
    @Email
    @NotEmpty(message = "Please enter your email")
    private String email;
    private Instant created;
    private boolean enabled;
}
