package com.example.controllers;


import com.example.data.*;
import com.example.errors.EmptySearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final PopularityAndRatingService popularityAndRatingService;
    private final TagService tagService;
    final Calendar calendar = Calendar.getInstance();

    @Autowired
    public MainPageController(BookService bookService, BookRepository bookRepository, PopularityAndRatingService popularityAndRatingService, TagService tagService) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.popularityAndRatingService = popularityAndRatingService;
        this.tagService = tagService;
        calendar.add(Calendar.MONTH, -12);

    }

    @PostConstruct
    public void updatePopRating(){
        popularityAndRatingService.updatePopRating();
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "index";
    }




   //-------------------Popular----------------

    @ModelAttribute("bestsellers")
    public List<Book> bestsellers(){
        return  bookService.getPopularBooks(0,6).getContent();
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getBestsellersPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPopularBooks(offset, limit).getContent());
    }


    //-------------------Recent------------------

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.getPageOfRecentBooksData(calendar.getTime(), new Date(), 0, 6).getContent();
    }

    @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto getRecentBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecentBooksData(calendar.getTime(), new Date(), offset, limit).getContent());
    }

    //------------------------Recommended carousal-----------------------


    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();
    }


    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }


   //----------------------------Searching by title-----------------------


    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }


    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }




    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                   Model model) throws EmptySearchException {
        if (searchWordDto != null) {
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResults",
                    bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 5).getContent());
            return "/search/index";
        } else {
            throw new EmptySearchException("No search word entered!");
        }
    }




    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), offset, limit).getContent());
    }

    //-------------------------

    @ModelAttribute("tags")
    public List<TagCountI> tags() {
        return tagService.getTagSize();
    }



}
