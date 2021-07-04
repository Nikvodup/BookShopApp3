package com.example.controllers;


import com.example.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.sql.Date;

@Controller
@RequestMapping("/books")
public class Books {

    private BookService bookService;
    private AuthorRepository authorRepository;
    private AuthorsController authorsController;

     @Autowired
    public Books(BookService bookService) {
        this.bookService = bookService;
    }

//--------------------------Popular Page--------------------------

 /**   @ModelAttribute("bestsellers")
    public List bestsellerBooks(){
        return bookService.getPageOfBestsellers(0,6).getContent();
    }  **/


    @GetMapping("/popular_page")
    public String popularPage(Date date, Model model){
      //  model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("bestsellers", bookService.getPageOfBestsellers(0,6).getContent());
        return "/books/popular_page";
    }


    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getBestSellersPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfBestsellers(offset, limit).getContent());
    }

    //------------------------------Slug Page----------------------------
 /**   @GetMapping("/slug")
    public String slugPage(Date date,Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(date));
        return "/books/slug";
    }  **/



}
