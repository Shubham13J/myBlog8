package com.myblog8.controller;

import com.myblog8.payload.CommentDto;
import com.myblog8.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/comment/{postId}
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{postId}")
    public ResponseEntity<?> saveComment(@Valid @RequestBody CommentDto commentDto,
                                         @PathVariable long postId, BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CommentDto comment = commentService.saveComment(commentDto, postId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    //http://localhost:8080/api/comment/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable long id){
        commentService.deleteById(id);
        return new ResponseEntity<>("Comment is delete: ", HttpStatus.OK);
    }

    //http://localhost:8080/api/comment/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<CommentDto> updateCommentById(@RequestBody CommentDto commentDto, @PathVariable long id){
        CommentDto dto = commentService.updateById(commentDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //http://localhost:8080/api/comment/{id}
    @GetMapping("{id}")
    public ResponseEntity<CommentDto> getDetailById(@PathVariable long id){
        CommentDto detail = commentService.getDetailById(id);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    //http://localhost:8080/api/comment
    @GetMapping
    public List<CommentDto> findAll(
            @RequestParam (value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam (value = "pageSize", defaultValue = "2", required = false) int pageSize
    ){
        List<CommentDto> allDetail = commentService.getAllDetail(pageNo, pageSize);
        return allDetail;
    }
}
