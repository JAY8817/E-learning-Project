package com.codewithjay.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTResponse {

    String token;
    UserDto userDto;
}
