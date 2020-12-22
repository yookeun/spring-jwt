package com.example.springjwt.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResultJson {
    private String code;
    private String msg;
    private String token;
}
