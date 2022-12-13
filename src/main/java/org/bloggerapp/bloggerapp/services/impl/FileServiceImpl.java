package org.bloggerapp.bloggerapp.services.impl;

import org.bloggerapp.bloggerapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFilename.substring(originalFilename.indexOf(".")));
        String filePath = path + File.separator + fileName;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
