package com.codewithjay.ServicesImpl;


import com.codewithjay.Services.FileServices;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileImpl implements FileServices {
    @Override
    public String saveFile(MultipartFile file, String outputDir, String filename) throws IOException {

        // creat path
        Path path = Paths.get(outputDir);

        //create output folder
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // path join with folder
        Path filePath = Paths.get(path.toString(), file.getOriginalFilename());

        //file write
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


        return filePath.toString();
    }
}
