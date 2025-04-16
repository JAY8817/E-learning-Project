package com.codewithjay;

import com.codewithjay.Config.JwtHelper;
import com.codewithjay.Entities.User;
import com.codewithjay.Reposetories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ELearningApplicationTests {

    @Test
    void contextLoads() {


    }

    @Autowired
    JwtHelper jwtHelper;



    @Test
    public void testJwt(){

        System.out.println("GWT test......");

        String token = jwtHelper.generateToken("Jay Shiroya");

        System.out.println("Token is:- "+ token);

        System.out.println(jwtHelper.validateToken(token, "Jay Shiroya"));
        System.out.println(jwtHelper.getExpirationDateFromToken(token));
        System.out.println(jwtHelper.getUsernameFromToken(token));


    }

}
