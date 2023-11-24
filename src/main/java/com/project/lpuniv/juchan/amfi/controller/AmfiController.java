package com.project.lpuniv.juchan.amfi.controller;

import com.project.lpuniv.juchan.amc.dto.AmcDto;
import com.project.lpuniv.juchan.amc.dto.AmcDtoPage;
import com.project.lpuniv.juchan.amc.service.AmcService;
import com.project.lpuniv.juchan.amfi.dao.AmfiDao;
import com.project.lpuniv.juchan.amfi.dto.AmfiDto;
import com.project.lpuniv.juchan.amfi.service.AmfiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@Slf4j
public class AmfiController {

    @Autowired
    private AmcService amcService;

    @Autowired
    private AmfiService amfiService;

    @GetMapping("/amfi/download/{amfiNo}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int amfiNo, HttpServletResponse response) throws IOException {
        try {
            // 해당 amfiNo에 해당하는 파일 정보를 가져옴
            AmfiDto amfiDto = amfiService.amfiOneSelect(amfiNo);

            if (amfiDto != null) {
                // 파일명 디코딩
                String amfiName = amfiDto.getAmfi_name();
                String originFileName = URLDecoder.decode(amfiName, StandardCharsets.UTF_8);
                System.out.println("amfiName:"+amfiName);
                // 파일 경로 설정
                Resource file = new FileSystemResource(System.getProperty("user.dir") +
                        "\\src\\main\\resources\\static\\juchan\\files" + File.separator + originFileName);
                System.out.println("file:"+file);
                if (file.exists()) {
                    // 파일명에서 확장자를 제거한 이름 추출
                    String onlyFileName = originFileName.substring(originFileName.lastIndexOf("_") + 1);
                    System.out.println("onlyFileName:" + onlyFileName);
                    // Content-Disposition 헤더 설정
                    response.setHeader("Content-Disposition", "attachment; filename=" + onlyFileName);
                    // 파일의 MIME 타입 설정
                    // 파일의 MIME 타입 설정
                    String mimeType;
                    if (originFileName.toLowerCase().endsWith(".png") || originFileName.toLowerCase().endsWith(".jpg") || originFileName.toLowerCase().endsWith(".jpeg")) {
                        mimeType = MediaType.IMAGE_PNG_VALUE;
                    } else {
                        mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                    }
                    System.out.println(mimeType);
                    response.setContentType(mimeType);



                    try (InputStream inputStream = file.getInputStream(); OutputStream outputStream = response.getOutputStream()) {
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
                // 해당 amfiNo에 해당하는 파일 정보가 없는 경우 404 상태코드 전송
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            // 예외 발생 시 500 상태코드 전송
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        AmfiDto amfiDto = amfiService.amfiOneSelect(amfiNo);
        String uploadFileName = amfiDto.getAmfi_og_name();
        String storeFileName = amfiDto.getAmfi_name();
        Resource fileResource = new FileSystemResource(System.getProperty("user.dir") +
                "\\src\\main\\resources\\static\\juchan\\files" + File.separator + storeFileName);

        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(fileResource);
    }


    @GetMapping("/amfi/amfi_info")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAmfiInfo(@RequestParam("amc_no") int amcNo) {
        Map<String, Object> response = new HashMap<>();

        // AmfiService를 사용하여 amcNo에 해당하는 데이터를 가져와서 응답 데이터에 추가
        List<AmfiDto> amfiDto = amfiService.amfiAllSelectDesc(amcNo);
        response.put("amfiDto", amfiDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/amfi/amfi_delete")
    @ResponseBody
    public ResponseEntity<Map<String, AmfiDto>> getAmfiDelete(
            @RequestParam("amfi_no") int amfi_no) {
        Map<String, AmfiDto> response = new HashMap<>();

        // AmfiService를 사용하여 amcNo에 해당하는 데이터를 가져와서 응답 데이터에 추가
        AmfiDto amfiDto = amfiService.amfiOneSelect(amfi_no);

        // 삭제한 amfi_no를 응답 데이터에 추가
        response.put("amfi", amfiDto);

        // 해당 amfi_no를 삭제
        amfiService.amfiDelete(amfi_no);

        System.out.println("amfiDto:" + amfiDto);
        System.out.println("amfi_no:" + amfi_no);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
