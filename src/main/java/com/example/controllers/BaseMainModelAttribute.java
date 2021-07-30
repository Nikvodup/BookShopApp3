package com.example.controllers;


import com.example.data.*;
import com.example.security.BookstoreUser;
import com.example.security.BookstoreUserDetails;
import com.example.security.BookstoreUserRegister;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

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

    @ModelAttribute("cartSize")
    public Integer getCartSize(@AuthenticationPrincipal BookstoreUserDetails user) {
        if (nonNull(user)) {
            return bookService.getCartBooks(user.getBookstoreUser().getId()).size();
        }
        return 0;
    }

    @ModelAttribute("postponedSize")
    public Integer getPostponedSize(@AuthenticationPrincipal BookstoreUserDetails user) {
        if (nonNull(user)) {
            return bookService.getPostponedBooks(user.getBookstoreUser().getId()).size();
        }
        return 0;
    }

    @ModelAttribute("tags")
    public List<TagCountI> tags() {
        return tagService.getTagSize();
    }
}
