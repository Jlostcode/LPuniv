package com.project.lpuniv.minho.amc.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.juchan.amfi.dto.AmfiDto;
import com.project.lpuniv.juchan.amfi.service.AmfiService;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.amc.service.AmcServiceMH;
import com.project.lpuniv.minho.file.service.FileServiceMH;
import com.project.lpuniv.minho.listenLec.service.LecInfoService;
import com.project.lpuniv.minho.submit.dto.SubmitDto;
import com.project.lpuniv.minho.submit.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/amc")
public class AmcControllerMH {
    @Autowired
    AmcServiceMH amcServiceMH;
    @Autowired
    LecInfoService lecInfoService;
    @Autowired
    private AmfiService amfiService;
    @Autowired
    SubmitService submitService;

    @GetMapping("/amcList")
    public String amcList(Model model, @RequestParam(value = "occ_NO") int occ_NO,HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        List<AmcDtoMH> amcList = amcServiceMH.selectAmcOccNo(occ_NO);
        model.addAttribute("amcList", amcList);
        return "minho/amc/amcList";
    }

    @GetMapping("/amcView")
    public String selectOneAmc(Model model, HttpSession session, @RequestParam(value = "amc_no") int amc_no){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        AmcDtoMH amcDtoMH = amcServiceMH.selectOneAmc(amc_no);
        List<AmfiDto> amfiDto = amcServiceMH.selectOneAmfi(amc_no);
        int user_no = authInfo.getUser_no();
        SubmitDto submitDto = submitService.selectSubmitAmcno(user_no, amc_no);
        model.addAttribute("amcDtoMH", amcDtoMH);
        model.addAttribute("amfiDto", amfiDto);
        model.addAttribute("submitDto", submitDto);
        return "minho/amc/amcView";
    }

    @GetMapping("/amfi/download/{amfiNo}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int amfiNo, HttpServletResponse response, Model model) throws IOException {
        AmfiDto amfiDto = amfiService.amfiOneSelect(amfiNo);
        model.addAttribute("amfiDto", amfiDto);
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
}
