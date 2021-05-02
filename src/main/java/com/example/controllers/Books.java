package com.example.controllers;


import com.example.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Controller
@RequestMapping("/books")
public class Books {

    private BookService bookService;

     @Autowired
    public Books(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/recent")
    public String recentPage(Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("from", LocalDate.now().minus(Period.ofMonths(6)));
        model.addAttribute("upTo", LocalDate.now());
        model.addAttribute("recent", bookService.getRecent());
        return "/books/recent";
    }


    @GetMapping("/popular")
    public String popularPage( Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));

        model.addAttribute("bestsellers", bookService.getBestsellers());
        return "/books/popular";
    }

    @GetMapping("/slug")
    public String slugPage(Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "/books/slug";
    }

    @GetMapping("/author")
    public String authorPage(Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "/books/author";
    }

}
