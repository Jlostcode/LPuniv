package com.project.lpuniv.junhyuk.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.junhyuk.service.FileService;
import com.project.lpuniv.junhyuk.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@RestController
public class FileDownloadController {

    @Autowired
    FileService fileService;

    // 파일 다운로드
    @GetMapping("/files/download/{file_name}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String file_name, HttpServletResponse response) {
        try {
            String originalFileName = fileService.getOriginalFileName(file_name);

            Path file = Paths.get("C:\\upload\\" + file_name);
            if (!Files.exists(file)) {
                throw new FileNotFoundException("File not found with filename: " + file_name);
            }

            File fileToDownload = file.toFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(fileToDownload));

            System.out.println("test");


            String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20");


            String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName;
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);


            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/files/delete/{fileName}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileName, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        // 파일 소유권 확인 로직 필요
        try {
            fileService.deleteFile(fileName, authInfo.getUser_no());
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }
    }



}
