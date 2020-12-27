package com.example.springjwt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key", length = 50, nullable = false)
    private Long userKey;

    @Column(name = "user_id", length = 50, nullable = false, unique = true)
    private String userId;

    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "role", length = 20, nullable = false)
    private String role;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

}
