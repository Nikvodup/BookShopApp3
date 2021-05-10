package com.example.controllers;


import com.example.data.Book;
import com.example.data.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/genres")
public class GenresController {

    private BookService bookService;

    public GenresController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();
    }

    @GetMapping("")
    public String genresPage(Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "/genres/index";
    }

    @GetMapping("/slug")
    public String slugPage(Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "/genres/slug";
    }

    @GetMapping("/light_reading")
    public String lightReadingPage(){
        return "/genres/light_reading";
    }

    @GetMapping("/serious_reading")
    public String seriousReadingPage(){
        return "/genres/serious_reading";
    }

    @GetMapping("/business_books")
    public String businessReadingPage(){
        return "/genres/business_books";
    }

    @GetMapping("/drama")
    public String dramaReadingPage(){
        return "/genres/drama";
    }

    @GetMapping("/crime_story")
    public String crimeStoryPage(){
        return "/genres/crime_story";
    }




}