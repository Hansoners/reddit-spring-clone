package com.example.springredditdemo.mapper;

import com.example.springredditdemo.dto.CommentsDto;
import com.example.springredditdemo.model.Comment;
import com.example.springredditdemo.model.Post;
import com.example.springredditdemo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public class CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    Comment map(CommentsDto commentsDto, Post post, User user);
}
