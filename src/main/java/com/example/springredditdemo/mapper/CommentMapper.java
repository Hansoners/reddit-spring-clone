package com.example.springredditdemo.mapper;

import com.example.springredditdemo.dto.CommentsDto;
import com.example.springredditdemo.model.Comment;
import com.example.springredditdemo.model.Post;
import com.example.springredditdemo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId()")
    @Mapping(target = "user", expression = "java(comment.getUser().getUsername()")
    CommentsDto maptoDto(Comment comment);
}
