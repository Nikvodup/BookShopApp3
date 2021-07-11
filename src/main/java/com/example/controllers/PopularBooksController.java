package com.example.controllers;

import com.example.data.BookService;
import com.example.data.BooksPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PopularBooksController {
    private final BookService bookService;

    @Autowired
    public PopularBooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/popular")
    public String popularPage(Model model){
        model.addAttribute("bestsellers", bookService.getPopularBooks(0,6).getContent());
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "/books/popular";
    }


    @GetMapping("/books/popular/page")
    @ResponseBody
    public BooksPageDto getNextPopularPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPopularBooks(offset, limit).getContent());
    }
}
