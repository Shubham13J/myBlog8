package com.myblog8.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 4 characters")
    private String name;
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 15 characters")
    private String email;
    @NotEmpty
    private String body;

}
