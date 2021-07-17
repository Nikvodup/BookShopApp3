package com.example.controllers;


import com.example.data.*;
import com.example.security.BookstoreUser;
import com.example.security.BookstoreUserRegister;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class BaseMainModelAttribute {

    private final BookService bookService;
    private final BookshpCartController bookshpCartController;
    private final PostponedPageController postponedPageController;
    private final TagService tagService;
    private final BookstoreUserRegister userRegister;



    public BaseMainModelAttribute(BookService bookService, BookshpCartController bookshpCartController, PostponedPageController postponedPageController, TagService tagService, BookstoreUserRegister userRegister) {
        this.bookService = bookService;
        this.bookshpCartController = bookshpCartController;
        this.postponedPageController = postponedPageController;
        this.tagService = tagService;
        this.userRegister = userRegister;

    }




    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }

    @PostConstruct
   @ModelAttribute("cartSize")
    public Integer getCartSize(){
        return bookshpCartController.getBooksFromCookieSlugs().size();
    }

    @PostConstruct
    @ModelAttribute("postponedSize")
    public Integer getPostponedSize(){
        return postponedPageController.getBooksFromCookieSlugs().size();
    }

    @ModelAttribute("tags")
    public List<TagCountI> tags() {
        return tagService.getTagSize();
    }
}
