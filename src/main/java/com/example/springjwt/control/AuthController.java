package com.example.springjwt.control;

import com.example.springjwt.entity.User;
import com.example.springjwt.repository.UserRepository;
import com.example.springjwt.util.JwtUtil;
import com.example.springjwt.util.ResultJson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    @PostMapping("/login")
    @ResponseBody
    public ResultJson login(@RequestParam(name = "userId", required = true) String userId,
                            @RequestParam(name = "password", required = true) String password) {

        ResultJson resultJson = new ResultJson();
        User user = userRepository.findUserByUserId(userId);
        String token = "";
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            JwtUtil jwtUtil = new JwtUtil();
            token = jwtUtil.generateToken(user);
        }
        resultJson.setCode("100");
        resultJson.setMsg("Success");
        resultJson.setToken(token);
        return null;

    }
}
