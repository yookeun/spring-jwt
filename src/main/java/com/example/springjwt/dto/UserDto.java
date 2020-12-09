package com.example.springjwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userId;
    private String password;
    private String userName;
    private String role;
    private String regDate;
}
