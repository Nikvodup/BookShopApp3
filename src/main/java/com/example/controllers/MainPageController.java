package com.example.controllers;


import com.example.data.Book;
import com.example.data.BookService;
import com.example.data.BooksPageDto;
import com.example.data.SearchWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();

    }

    @ModelAttribute("bestsellers")
    public List<Book> bestsellers(){
        return  bookService.getPageOfBestsellers(0,6).getContent();

    }

    @ModelAttribute("recent")
    public List<Book> recent(){
        return bookService.getRecentPage(0,6).getContent();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "index";
    }


    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }


      @GetMapping("/books/bestsellersLine")
     @ResponseBody
     public BooksPageDto getBestsellersPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
     return new BooksPageDto(bookService.getPageOfBestsellers(offset,limit).getContent());
     }

     @GetMapping("/books/recentLine")
     @ResponseBody
     public BooksPageDto getPageOfRecentBooks(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getRecentPage(offset,limit).getContent());
     }



    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                   Model model) {
        model.addAttribute("searchWordDto", searchWordDto);
        model.addAttribute("searchResults",
                bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 5).getContent());
        return "/search/index";
    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), offset, limit).getContent());
    }
}

