package com.example.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MultiController {

    @ModelAttribute("serverTime")
    public String  date(){
        return new SimpleDateFormat("hh:mm:ss").format(new Date());
    }

    @GetMapping("/cart")
    public String cartPage(){

        return "/cart/cart";
    }

    @GetMapping("/contacts")
    public String contactsPage(){

        return "contacts";
    }

    @GetMapping("/faq")
    public String faqPage(){

        return "faq";
    }

    @GetMapping("/signin")
    public String signinPage(){

        return "/signin/signin";
    }



    @GetMapping("/about")
    public String aboutPage(){

        return "about";
    }

    @GetMapping("/signup")
    public String signupPage(){

        return "signup";
    }

    @GetMapping("/tags")
    public String indexPage(){

        return "/tags/index";
    }

}
