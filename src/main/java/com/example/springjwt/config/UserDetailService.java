package com.example.springjwt.config;

import com.example.springjwt.entity.User;
import com.example.springjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private static UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserId(username);
        if (user == null) {
            throw new UsernameNotFoundException("UsernameNotFound [" + username + "]");
        }
        return createUser(user);
    }

    private LoginUser createUser(User user) {
        LoginUser loginUser = new LoginUser(user);
        loginUser.setRoles(Collections.singletonList(loginUser.getRole()));
        return loginUser;
    }
}
