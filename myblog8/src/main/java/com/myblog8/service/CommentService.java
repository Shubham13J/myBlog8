package com.myblog8.service;

import com.myblog8.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto saveComment(CommentDto dto, long postId);

    void deleteById(long id);

    CommentDto updateById(CommentDto commentDto, long id);

    CommentDto getDetailById(long id);

    List<CommentDto> getAllDetail(int pageNo, int pageSize);
}
