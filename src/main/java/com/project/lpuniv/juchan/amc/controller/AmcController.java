package com.project.lpuniv.juchan.amc.controller;

import com.project.lpuniv.juchan.amc.dto.AmcDto;
import com.project.lpuniv.juchan.amc.dto.AmcDtoPage;
import com.project.lpuniv.juchan.amc.service.AmcService;
import com.project.lpuniv.juchan.amfi.dto.AmfiDto;
import com.project.lpuniv.juchan.amfi.service.AmfiService;
import com.project.lpuniv.juchan.ccim.dto.CcimDto;
import com.project.lpuniv.juchan.ccim.dto.CcimDtoPage;
import com.project.lpuniv.junhyuk.dto.FileAttachment;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@Slf4j
public class AmcController {

    @Autowired
    private AmcService amcService;

    @Autowired
    private AmfiService amfiService;

    private int size = 3;

//     페이지당 회원 수를 기준으로 사용자 페이지 정보를 반환하는 메서드
    public AmcDtoPage getAmcDtoPage(int occ_NO, int ccim_no, int pageNum, int pageSize) {
        int total = amcService.amcAllCount(occ_NO, ccim_no); // 전체 사용자 수 조회
        System.out.println("total" + total);
        List<AmcDto> amcDtos = amcService.amcAllSelectDescPage(occ_NO,ccim_no,(pageNum - 1) * size, size); // 페이지에 해당하는 사용자 목록 조회
        return new AmcDtoPage(total, pageNum, size, amcDtos); // 사용자 페이지 정보를 생성하여 반환
    }
    @GetMapping("amc")
    public String amcMain(Model model, @RequestParam(name = "pageNo", required = false) String pageNo,
                          @RequestParam(value = "occ_no") int occ_no, @RequestParam(value = "ccim_no") int ccim_no){
        int pageSize = 3; // 페이지 크기 설정 (한 페이지에 보여줄 회원 수)
        int pageNum = 1;
        try {
            if (pageNo != null) {
                pageNum = Integer.parseInt(pageNo);
            }
        } catch (NumberFormatException e) {
            // 페이지 번호가 숫자로 변환되지 않을 때 처리할 코드 추가
            // 예를 들어, 기본 페이지 번호를 설정하거나 에러 핸들링을 수행합니다.
            pageNum = 1; // 기본 페이지 번호 설정
        }
//		  if (pageNo != null) {
//	            pageNum = Integer.parseInt(pageNo);
//	        }


        System.out.println(pageNo);
        AmcDtoPage amcDtoPage = getAmcDtoPage(occ_no, ccim_no, pageNum, pageSize); // 사용자 페이지 정보를 조회
//        amfiService.amcAllSelectDesc(ccim_no);
        System.out.println("occ_no:" + occ_no);
        System.out.println("ccim_no:" + ccim_no);
        model.addAttribute("listPage", amcDtoPage);
        model.addAttribute("occ_no", occ_no);
        model.addAttribute("ccim_no", ccim_no);
        return "/juchan/amc/amc_main";
    }


    @GetMapping("amc/amc_insert")
    public String amcInsert(Model model,@RequestParam("occ_no") int occ_no ,@RequestParam("ccim_no") int ccim_no) {
        model.addAttribute("occ_no", occ_no);
        model.addAttribute("ccim_no", ccim_no);
        return "/juchan/amc/amc_insert";
    }

    @PostMapping("amc/upload")
    public String amcInsertP( @RequestParam(name = "files", required = false) List<MultipartFile> files,
                             @RequestParam("occ_no") int occ_no ,@RequestParam("ccim_no") int ccim_no,
                              @RequestParam("amc_at") String amc_at, @RequestParam("amc_ac") String amc_ac
                             ) throws IOException {
        System.out.println("Files: " + files);
        try {
            if (files == null) {
                files = Collections.emptyList(); // 파일이 전송되지 않은 경우 빈 리스트로 초기화
            }
            AmcDto amcDto = new AmcDto();
            amcDto.setAmc_at(amc_at);
            amcDto.setAmc_ac(amc_ac);
            amcDto.setOcc_no(occ_no);
            amcDto.setCcim_no(ccim_no);
            amcDto.setAmc_no(0);
            System.out.println(amcDto);
            amcService.amcInsert(amcDto);
            System.out.println(amcDto);
            int amc_no = amcDto.getAmc_no();
            System.out.println(amc_no);

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    amfiService.amfiInsert(amc_no, file);
                }
            }

            return "redirect:/amc?occ_no=" + occ_no + "&ccim_no=" + ccim_no;
        } catch (NullPointerException e) {
            // 예외 처리 (로그 기록 또는 다른 방법으로 예외를 처리하세요)
            log.error("NullPointerException in amcInsertP", e);
            return "errorPage"; // 예외가 발생한 경우 에러 페이지로 리다이렉트 또는 에러 메시지를 보여줄 수 있습니다.
        }
    }

    @GetMapping("amc/amc_modify")
    public String amcModify(Model model, @RequestParam("amc_no") int amc_no) {
        AmcDto amcDto = amcService.amcOneSelect(amc_no);
        List<AmfiDto> amfiDto = amfiService.amfiAllSelectDesc(amc_no);
        System.out.println("amc_modify Get amc_no:" + amc_no);
        model.addAttribute("amcDto", amcDto);
        model.addAttribute("amc_no", amc_no);
        model.addAttribute("amfiDtos", amfiDto);
        model.addAttribute("occ_no", amcDto.getOcc_no());
        model.addAttribute("ccim_no", amcDto.getCcim_no());
        return "/juchan/amc/amc_modify";
    }

    @PostMapping("amc/modify")
    public String amcModifyP(@RequestParam(name = "files", required = false) List<MultipartFile> files,
                            @RequestParam(value = "occ_no") int occ_no, @RequestParam(value = "ccim_no") int ccim_no, @RequestParam(value = "amc_no") int amc_no,
                            @RequestParam(value = "amc_at") String amc_at, @RequestParam(value = "amc_ac") String amc_ac) {
        System.out.println("Files: " + files);
        System.out.println("occ_no" + occ_no);
        System.out.println("ccim_no" + ccim_no);
        System.out.println("amc_no" + amc_no);
        System.out.println("amc_at" + amc_at);
        System.out.println("amc_ac" + amc_ac);
        try {
            if (files == null) {
                files = Collections.emptyList(); // 파일이 전송되지 않은 경우 빈 리스트로 초기화
            }
            AmcDto amcDto = new AmcDto();
            amcDto.setAmc_no(amc_no);
            amcDto.setAmc_at(amc_at);
            amcDto.setAmc_ac(amc_ac);
            amcDto.setOcc_no(occ_no);
            amcDto.setCcim_no(ccim_no);
            System.out.println("수정 amcDto:"+amcDto);
            amcService.amcModify(amcDto.getAmc_at(), amcDto.getAmc_ac(),amcDto.getAmc_no());


            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    amfiService.amfiInsert(amc_no, file);
                }
            }

            return "redirect:/amc?occ_no=" + occ_no + "&ccim_no=" + ccim_no;
        } catch (NullPointerException | IOException e) {
            // 예외 처리 (로그 기록 또는 다른 방법으로 예외를 처리하세요)
            log.error("NullPointerException in amcInsertP", e);
            return "errorPage"; // 예외가 발생한 경우 에러 페이지로 리다이렉트 또는 에러 메시지를 보여줄 수 있습니다.
        }


    }

    @GetMapping("/amc/amc_delete")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> amcDelete(
            @RequestParam("amc_no") int amc_no, @RequestParam("amc_at") String amc_at) {
        Map<String, Object> response = new HashMap<>();

        // 삭제한 amfi_no를 응답 데이터에 추가
        response.put("amc_no", amc_no);
        response.put("amc_at", amc_at);

        // 해당 amfi_no를 삭제
        amcService.amcDelete(amc_no);

        System.out.println("amc_no:" + amc_no);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/loginModal")
    public String modal() {
        return "/juchan/amc/amc_main_modal";
    }
}
