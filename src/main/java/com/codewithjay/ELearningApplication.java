package com.codewithjay;

import com.codewithjay.Config.AppConstant;
import com.codewithjay.Entities.Roles;
import com.codewithjay.Entities.User;
import com.codewithjay.Reposetories.RoleRepo;
import com.codewithjay.Reposetories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;


@SpringBootApplication
public class ELearningApplication implements CommandLineRunner{
//public class ELearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(ELearningApplication.class, args);
    }

    @Bean
    ModelMapper modelMaper() {
        return new ModelMapper();
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {

        Roles role1 = new Roles();
        role1.setRoleId(UUID.randomUUID().toString());
        role1.setRoleName(AppConstant.ROLE_ADMIN);

        Roles role2 = new Roles();
        role2.setRoleId(UUID.randomUUID().toString());
        role2.setRoleName(AppConstant.ROLE_USER);

        roleRepo.findByRoleName(AppConstant.ROLE_ADMIN).ifPresentOrElse(roles1 -> {
            System.out.println("Admin is present");

        },()->{
            roleRepo.save(role1);
            System.out.println("Admin is saved successfully");
        });


        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setActive(true);
        user1.setPhoneNumber("147852369");
        user1.setEmail("Admin@gmail.com");
        user1.setName("Admin");
        user1.addRole(role1);

        userRepo.findByEmail("Admin@gmail.com").ifPresentOrElse(users1 -> {
            System.out.println("Admin is present");

        },()->{
            userRepo.save(user1);
            System.out.println("Admin is saved successfully");
        });

        User user2 = new User();
        user2.setUserId(UUID.randomUUID().toString());
        user2.setPassword(passwordEncoder.encode("password"));
        user2.setActive(true);
        user2.setPhoneNumber("147852369");
        user2.setEmail("User@gmail.com");
        user2.setName("User");

        user2.addRole(role2);

        userRepo.findByEmail("User@gmail.com").ifPresentOrElse(users1 -> {
            System.out.println("User is present");

        },()->{
            userRepo.save(user2);
            System.out.println(user2.getName()+" User is saved successfully");
        });
    }
}
