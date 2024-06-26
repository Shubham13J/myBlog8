package com.myblog8.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private long id;
    private String name;
    private String username;
    private String password;
    private String email;
}
