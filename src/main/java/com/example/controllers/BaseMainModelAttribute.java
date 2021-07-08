package com.example.controllers;


import com.example.data.Book;
import com.example.data.BookService;
import com.example.data.SearchWordDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class BaseMainModelAttribute {

    private final BookService bookService;
    private final BookshpCartController bookshpCartController;
    private final PostponedPageController postponedPageController;

    public BaseMainModelAttribute(BookService bookService, BookshpCartController bookshpCartController, PostponedPageController postponedPageController) {
        this.bookService = bookService;
        this.bookshpCartController = bookshpCartController;
        this.postponedPageController = postponedPageController;
    }


    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }

   @ModelAttribute("cartSize")
    public Integer getCartSize(){
        return bookshpCartController.getBooksFromCookieSlugs().size();
    }

    @ModelAttribute("postponedSize")
    public Integer getPostponedSize(){
        return postponedPageController.getBooksFromCookieSlugs().size();
    }
}
