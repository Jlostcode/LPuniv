package com.project.lpuniv.minho.amc.controller;

import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.amc.service.AmcServiceMH;
import com.project.lpuniv.minho.listenLec.service.LecInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/amc")
public class AmcControllerMH {
    @Autowired
    AmcServiceMH amcServiceMH;
    @Autowired
    LecInfoService lecInfoService;

    @GetMapping("/amcList")
    public String amcList(Model model, @RequestParam(value = "occ_NO") int occ_NO){
        List<AmcDtoMH> amcList = amcServiceMH.selectAmcOccNo(occ_NO);
        model.addAttribute("amcList", amcList);
        return "minho/amc/amcList";
    }
}
