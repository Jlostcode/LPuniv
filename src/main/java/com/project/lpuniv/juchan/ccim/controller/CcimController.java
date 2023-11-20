package com.project.lpuniv.juchan.ccim.controller;

import com.project.lpuniv.Dto;
import com.project.lpuniv.juchan.ccim.dto.CcimDto;
import com.project.lpuniv.juchan.ccim.dto.CcimDtoPage;
import com.project.lpuniv.juchan.ccim.service.CcimService;
import com.project.lpuniv.juchan.occ.dto.OccDto;
import com.project.lpuniv.juchan.occ.dto.OccDtoPage;
import com.project.lpuniv.juchan.occ.service.OccService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
public class CcimController {

    @Autowired
    private CcimService ccimService;

    private int size = 3;

    // 페이지당 회원 수를 기준으로 사용자 페이지 정보를 반환하는 메서드
    public CcimDtoPage getCcimDtoPage(int occ_NO, int pageNum, int pageSize) {
        int total = ccimService.ccimAllCount(occ_NO); // 전체 사용자 수 조회
        List<CcimDto> ccimDtos = ccimService.ccimAllSelectDescPage(occ_NO,(pageNum - 1) * size, size); // 페이지에 해당하는 사용자 목록 조회
        return new CcimDtoPage(total, pageNum, size, ccimDtos); // 사용자 페이지 정보를 생성하여 반환
    }
    @GetMapping("ccim")
    public String ccimMain(Model model, @RequestParam(name = "pageNo", required = false) String pageNo, @RequestParam(value = "occ_NO") int occ_NO) {
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
        CcimDtoPage ccimDtoPage = getCcimDtoPage(occ_NO, pageNum, pageSize); // 사용자 페이지 정보를 조회
        model.addAttribute("listPage", ccimDtoPage);
        model.addAttribute("occ_NO", occ_NO);
    return "/juchan/ccim/ccim_main";
    }

    @GetMapping("ccim/ccim_insert")
    public String ccimInsert(Model model, @RequestParam(value = "occ_NO") int occ_NO) {
        System.out.println(occ_NO);
        model.addAttribute("occ_NO", occ_NO);
        return "/juchan/ccim/ccim_insert";
    }
    @PostMapping("ccim/ccim_insert")
    public String ccimInsertP(CcimDto ccimDto, @RequestParam(value = "occ_NO") int occ_NO) {
        System.out.println(occ_NO);
        ccimDto.setOcc_NO(occ_NO);
        System.out.println(ccimDto);
        ccimService.ccimInsert(ccimDto);
        return "redirect:/ccim?occ_NO=" + occ_NO;
    }

    @GetMapping("ccim/ccim_modify")
    public String ccimModify(Model model, @RequestParam(value = "ccim_NO") int ccim_NO) {
        CcimDto ccimDto = ccimService.ccimOneSelect(ccim_NO);
        System.out.println(ccimDto);
        model.addAttribute("ccimdto", ccimDto);

        return "/juchan/ccim/ccim_modify";
    }

    @PostMapping("ccim/ccim_modify")
    public String ccimModifyP(CcimDto ccimDto){
        ccimService.ccimModify(ccimDto);
        return "redirect:/ccim?occ_NO=" + ccimDto.getOcc_NO();
    }

    @GetMapping("/ccim/ccim_delete")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> ccimDelete(
            @RequestParam("occ_no") int occ_no, @RequestParam("ccim_no") int ccim_no, @RequestParam("ccim_title") String ccim_title) {
        Map<String, Object> response = new HashMap<>();

        // 삭제한 amfi_no를 응답 데이터에 추가
        response.put("occ_no", occ_no);
        response.put("ccim_no", ccim_no);
        response.put("ccim_title",ccim_title);

        // 해당 amfi_no를 삭제
        ccimService.ccimDelete(ccim_no);

        System.out.println("ccim_no:" + ccim_no);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
