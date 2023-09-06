package com.kh.youtube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("test", "집에 가쟈ㅑㅑㅑ");
        return "index";
    }
}
