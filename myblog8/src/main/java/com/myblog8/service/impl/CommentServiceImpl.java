package com.myblog8.service.impl;

import com.myblog8.entity.Comment;
import com.myblog8.entity.Post;
import com.myblog8.exception.ResourceNotFound;
import com.myblog8.payload.CommentDto;
import com.myblog8.repository.CommentRepository;
import com.myblog8.repository.PostRepository;
import com.myblog8.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepo;
    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    @Override
    public CommentDto saveComment(CommentDto dto, long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found with id: " + postId)
        );
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        comment.setBody(dto.getBody());
        comment.setPost(post);

        Comment saveComment = commentRepo.save(comment);

        CommentDto cd = new CommentDto();
        cd.setId(saveComment.getId());
        cd.setName(saveComment.getName());
        cd.setEmail(saveComment.getEmail());
        cd.setBody(saveComment.getBody());

        return cd;
    }

    @Override
    public void deleteById(long id) {
        commentRepo.deleteById(id);
    }

    @Override
    public CommentDto updateById(CommentDto commentDto, long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Comment not found for this id: " + id)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment savedComment = commentRepo.save(comment);

        CommentDto dto= new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setEmail(savedComment.getEmail());
        dto.setBody(savedComment.getBody());
        return dto;
    }

    @Override
    public CommentDto getDetailById(long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("comment not found for this id: " + id)
        );
        return mapToDto(comment);
    }

    @Override
    public List<CommentDto> getAllDetail(int pageNo, int pageSize) {
        PageRequest pagable = PageRequest.of(pageNo, pageSize);
        Page<Comment> data = commentRepo.findAll(pagable);
        List<Comment> content = data.getContent();
        List<CommentDto> collect = content.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return collect;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }
}
