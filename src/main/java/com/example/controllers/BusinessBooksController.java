package com.example.controllers;


import com.example.data.Book;
import com.example.data.BookService;
import com.example.data.GenresService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class BusinessBooksController {

    private final GenresService genresService;
    private final BookService bookService;

    public BusinessBooksController(GenresService genresService, BookService bookService) {
        this.genresService = genresService;
        this.bookService = bookService;
    }

    @GetMapping("/business_books")
    public String getBusinessBooks(){
        return "/genres/business_books";
    }

    //---------------------------------About business-----------------

    @ModelAttribute("aboutbusiness")
    public List<Book> aboutbusinessBooks(){
        return genresService.getGenreBooks("about_business",0,6).getContent();
    }

    @ModelAttribute("countAboutBusiness")
    public Integer countABBooks(){
        return bookService.getCount("about_business");
    }

    //-------------------------------Stocks and Bonds---------------

    @ModelAttribute("stocksbonds")
    public List<Book> stocksbondsBooks(){
        return genresService.getGenreBooks("stocks_and_bonds",0,6).getContent();
    }

    @ModelAttribute("countStocksBonds")
    public Integer countStocksBonds(){
        return bookService.getCount("stocks_and_bonds");
    }

    //--------------------------------Accounting and Taxes------------------

    @ModelAttribute("accounting")
    public List<Book> accountingBooks(){
        return genresService.getGenreBooks("stocks_and_bonds",0,6).getContent();
    }

    @ModelAttribute("countAccounting")
    public Integer countAccounting(){
        return bookService.getCount("stocks_and_bonds");
    }

    //-------------------------Russian Business-------------

    @ModelAttribute("russianBusiness")
    public List<Book> russianBusinessBooks(){
        return genresService.getGenreBooks("russian_business",0,6).getContent();
    }

    @ModelAttribute("countRussianBusiness")
    public Integer countRussianBusiness(){
        return bookService.getCount("russian_business");
    }

    //-----------------------------Success Story---------------------

    @ModelAttribute("successStory")
    public List<Book> successStoryBooks(){
        return genresService.getGenreBooks("success_story",0,6).getContent();
    }

    @ModelAttribute("countSuccessStory")
    public Integer countSuccessStory(){
        return bookService.getCount("success_story");
    }

}
