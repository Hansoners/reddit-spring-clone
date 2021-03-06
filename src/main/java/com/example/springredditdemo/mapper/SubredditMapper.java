package com.example.springredditdemo.mapper;


import com.example.springredditdemo.dto.SubredditDto;
import com.example.springredditdemo.model.Post;
import com.example.springredditdemo.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "postCount", expression = "java(mapPosts(subreddit.getPostsList()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "postsList", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}