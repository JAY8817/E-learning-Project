package com.codewithjay.Exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("ResourceNotFoundException");
    }

    public ResourceNotFoundException(String courseNotFound) {
        super(courseNotFound);
    }
}
