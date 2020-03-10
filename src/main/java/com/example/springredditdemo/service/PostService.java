package com.example.springredditdemo.service;

import com.example.springredditdemo.dto.PostRequest;
import com.example.springredditdemo.dto.PostResponse;
import com.example.springredditdemo.exception.PostException;
import com.example.springredditdemo.exception.SubredditException;
import com.example.springredditdemo.mapper.PostMapper;
import com.example.springredditdemo.model.Post;
import com.example.springredditdemo.model.Subreddit;
import com.example.springredditdemo.model.User;
import com.example.springredditdemo.repository.PostRepository;
import com.example.springredditdemo.repository.SubredditRepository;
import com.example.springredditdemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;


    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostException(id.toString()));
        return postMapper.maptoDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(postMapper::maptoDto).collect(toList());
    }

    @Transactional
    public void save(PostRequest postRequest) {
        postRepository.save(mapToPost(postRequest));
    }
    private Post mapToPost(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditException(postRequest.getSubredditName()));
        return Post.builder()
                .postName(postRequest.getPostName())
                .description(postRequest.getDescription())
                .url(postRequest.getUrl())
                .createdDate(Instant.now())
                .voteCount(0)
                .subreddit(subreddit)
                .user(authService.getCurrentUser())
                .build();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SubredditException(id.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::maptoDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::maptoDto)
                .collect(toList());
    }

}
