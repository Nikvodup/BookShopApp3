package com.example.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.text.SimpleDateFormat;
import java.util.Date;



@Controller
public class GenresController {



    @GetMapping("/genres")
    public String authorsPage(Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "/genres/index";
    }
}