package com.example.springjwt.control;

import com.example.springjwt.entity.Users;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @ResponseBody
    public ResultJson login(@RequestParam(name = "userId", required = true) String userId,
                            @RequestParam(name = "password", required = true) String password) {

        ResultJson resultJson = new ResultJson();
        Users users = userRepository.findUserByUserId(userId);
        String token = "";
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (users != null && passwordEncoder.matches(password, users.getPassword())) {
            token = jwtUtil.generateToken(users);
        } else {
            resultJson.setCode("900");
            resultJson.setMsg("Login Failed");
            return resultJson;
        }
        resultJson.setCode("200");
        resultJson.setMsg("Success");
        resultJson.setToken(token);
        return resultJson;
    }
}
