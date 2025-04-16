package com.codewithjay.Controller;

import com.codewithjay.Config.JwtHelper;
import com.codewithjay.Dto.CustomUserDetail;
import com.codewithjay.Dto.JWTRequest;
import com.codewithjay.Dto.JWTResponse;
import com.codewithjay.Dto.UserDto;
import com.codewithjay.Entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private AuthenticationManager manager;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private JwtHelper helper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JWTRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());

        CustomUserDetail customUserDetail = (CustomUserDetail) userDetailsService.loadUserByUsername(request.getEmail());
        User user = customUserDetail.getUser();
        String token = this.helper.generateToken(user.getEmail());

        JWTResponse response = JWTResponse.builder()
                .token(token)
                .userDto(modelMapper.map(user, UserDto.class))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
            manager.authenticate(authentication);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(" Invalid User Details  !!");
        }

    }

}
