package com.project.lpuniv.minho.file.controller;

import com.project.lpuniv.minho.file.service.FileServiceMH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FileControllerMH {
    @Autowired
    FileServiceMH fileServiceMH;
}
