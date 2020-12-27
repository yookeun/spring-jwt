package com.example.springjwt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringJwtApplicationTests {

    @Test
    void 비밀번호생() {
        PasswordEncoder bcr = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password = bcr.encode("1234");
        System.out.println("password = " + password);
    }

}
