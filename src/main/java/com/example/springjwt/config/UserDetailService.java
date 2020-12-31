package com.example.springjwt.config;

import com.example.springjwt.entity.Users;
import com.example.springjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findUserByUserId(username);
        if (users == null) {
            throw new UsernameNotFoundException("UsernameNotFound [" + username + "]");
        }
        return createUser(users);
    }

    private LoginUser createUser(Users users) {
        LoginUser loginUser = new LoginUser(users);
        loginUser.setRoles(Collections.singletonList(loginUser.getRole()));
        System.out.println(loginUser.getRoles().stream().collect(Collectors.toList()));
        return loginUser;
    }
}
