package com.myblog8.service;

import com.myblog8.payload.PostDto;
import com.myblog8.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    void DeletePostById(int postId);

    PostDto getDetailPostById(int postId);


    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto updateById(PostDto postDto, long id);
}
