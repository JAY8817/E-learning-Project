package com.codewithjay.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileServices {

    String saveFile(MultipartFile file, String outputDir, String filename) throws IOException;
}
