package com.example.springredditdemo.service;

import com.example.springredditdemo.dto.AuthenticationResponse;
import com.example.springredditdemo.dto.LoginRequest;
import com.example.springredditdemo.dto.RegisterRequest;
import com.example.springredditdemo.exception.RedditException;
import com.example.springredditdemo.model.AuthenticationToken;
import com.example.springredditdemo.model.NotificationEmail;
import com.example.springredditdemo.model.User;
import com.example.springredditdemo.repository.AuthenticationRepository;
import com.example.springredditdemo.repository.UserRepository;
import com.example.springredditdemo.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.example.springredditdemo.util.Constants.ACTIVATION_EMAIL;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationRepository authenticationRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setCreated(Instant.now());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEmail((registerRequest.getEmail()));
        user.setEnabled(false);
        userRepository.save(user);
        log.info("User Registered Successfully, Sending Authentication Email");
        String authToken = generateAuthToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                + ACTIVATION_EMAIL + "/" + authToken);
        mailService.sendMail(new NotificationEmail(user.getEmail(),"Please activate your account!", message));
    }

    private String generateAuthToken(User user) {
        String token = UUID.randomUUID().toString();
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(token);
        authenticationToken.setUser(user);
        authenticationRepository.save(authenticationToken);
        return token;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void verifyAccount(String token) {
        Optional<AuthenticationToken> authenticationTokenOptional = authenticationRepository.findByToken(token);
        fetchAndEnable(authenticationTokenOptional.orElseThrow(() -> new RedditException("Invalid Token")));
    }

    @Transactional
    void fetchAndEnable(AuthenticationToken authenticationToken) {
        String username = authenticationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RedditException("User not found. ID: " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + principal.getUsername()));

        }
}
