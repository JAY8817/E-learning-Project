package com.codewithjay.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JWTRequest {

    String email;
    String password;
}
