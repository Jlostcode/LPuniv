package com.project.lpuniv.minho.amc.controller;

import com.project.lpuniv.juchan.amfi.dto.AmfiDto;
import com.project.lpuniv.juchan.amfi.service.AmfiService;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.amc.service.AmcServiceMH;
import com.project.lpuniv.minho.listenLec.service.LecInfoService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    @GetMapping("/amcList")
    public String amcList(Model model, @RequestParam(value = "occ_NO") int occ_NO){
        List<AmcDtoMH> amcList = amcServiceMH.selectAmcOccNo(occ_NO);
        model.addAttribute("amcList", amcList);
        return "minho/amc/amcList";
    }

    @GetMapping("/amcView")
    public String selectOneAmc(Model model, @RequestParam(value = "amc_no") int amc_no){
        AmcDtoMH amcDtoMH = amcServiceMH.selectOneAmc(amc_no);
        model.addAttribute("amcDtoMH", amcDtoMH);
        AmfiDto amfiDto = amcServiceMH.selectOneAmfi(amc_no);
        model.addAttribute("amfiDto", amfiDto);
        return "minho/amc/amcView";
    }

    @GetMapping("/amfi/download/{amfi_no}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int amfi_no, HttpServletResponse response, Model model) throws IOException {
        AmfiDto amfiDto = amfiService.amfiOneSelect(amfi_no);
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
