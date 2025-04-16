package com.codewithjay.Exceptions;

import com.codewithjay.Dto.CustomMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException
    {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Bad credentials ! Invalid username or password");
        customMessage.setSuccess(false);

//      Convert Json to String............

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonToString = objectMapper.writeValueAsString(customMessage);

        PrintWriter writer = response.getWriter();
        writer.println(jsonToString);
    }
}


