package com.project.lpuniv.minho.file.controller;

import com.project.lpuniv.minho.file.dto.FileDtoMH;
import com.project.lpuniv.minho.file.service.FileServiceMH;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/file")
public class FileControllerMH {
    @Autowired
    FileServiceMH fileServiceMH;

    @GetMapping("/download/{file_no}")
    public ResponseEntity<Resource> downSubmit(@PathVariable int file_no,
                                               HttpServletResponse response) throws IOException {
        try {
            // 해당 file_no 해당하는 파일 정보를 가져옴
            FileDtoMH fileDtoMH = fileServiceMH.downloadFile(file_no);
            if (fileDtoMH != null) {
                // 파일명 디코딩
                String fileNm = fileDtoMH.getFile_nm();
                String originFileNm = URLDecoder.decode(fileNm, StandardCharsets.UTF_8);
                //파일 경로 설정
                Resource filePath = new FileSystemResource(System.getProperty("user.dir") +
                        "\\src\\main\\resources\\static\\minho\\files" + File.separator + originFileNm);
                if (filePath.exists()) {
                    // 파일명에서 확장자를 제거한 이름 추출
                    String onlyFileNm = originFileNm.substring(originFileNm.lastIndexOf("_") + 1);
                    // Content-Disposition 헤더 설정
                    response.setHeader("Content-Disposition", "attachment; filename=" + onlyFileNm);
                    // 파일의 MIME 타입 설정
                    // 파일의 MIME 타입 설정
                    String mimeType;
                    if (originFileNm.toLowerCase().endsWith(".png") || originFileNm.toLowerCase().endsWith(".jpg") || originFileNm.toLowerCase().endsWith(".jpeg")) {
                        mimeType = MediaType.IMAGE_PNG_VALUE;
                    } else {
                        mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                    }
                    response.setContentType(mimeType);

                    try (InputStream inputStream = filePath.getInputStream(); OutputStream outputStream = response.getOutputStream()) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        // 파일을 읽어서 브라우저로 전송
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        response.flushBuffer();
                    }
                } else {
                    // 파일이 존재하지 않는 경우 404 상태코드 전송
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                // 해당 file_no 해당하는 파일 정보가 없는 경우 404 상태코드 전송
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            // 예외 발생 시 500 상태코드 전송
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        FileDtoMH fileDtoMH = fileServiceMH.downloadFile(file_no);
        String uploadFileNm = fileDtoMH.getFile_nm();
        String storeFileNm = fileDtoMH.getFile_uuid();
        Resource fileResource = new FileSystemResource(System.getProperty("user.dir") +
                "\\src\\main\\resources\\static\\minho\\files" + File.separator + storeFileNm);
        String encodeUploadFileNm = UriUtils.encode(uploadFileNm, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileNm + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(fileResource);
    }
}
