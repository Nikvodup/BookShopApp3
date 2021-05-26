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

    public BaseMainModelAttribute(BookService bookService) {
        this.bookService = bookService;
    }


    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }


}
