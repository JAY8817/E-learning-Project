package com.codewithjay.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Data
@Getter
@Setter
public class ResourceContantType {


    private Resource resource;
    private String ContantType;
}
