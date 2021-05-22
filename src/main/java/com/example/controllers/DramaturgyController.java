package com.example.controllers;

import com.example.data.Book;
import com.example.data.BookService;
import com.example.data.BooksPageDto;
import com.example.data.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DramaturgyController {

    private final GenresService genresService;
    private final BookService bookService;

    @Autowired
    public DramaturgyController(GenresService genresService, BookService bookService){
        this.genresService=genresService;
        this.bookService = bookService;
    }

    @GetMapping("/genres/dramaturgy")
    public String ancientDramaPage(){
        return "/genres/dramaturgy";
    }

    //-----------------ancient drama----------------
    @ModelAttribute("ancientdrama")
    public List<Book> ancientDrama(){
        return genresService.getGenreBooks("ancient_drama",0,6).getContent();
    }

    @ModelAttribute("countAncientDrama")
    public Integer countADBooks(){
        return bookService.getCount("ancient_drama");
    }

    @GetMapping("/books/ancientdramaLine")
    @ResponseBody
    public BooksPageDto getAncientDramaPage(String genre,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getGenreBooks(genre,offset,limit).getContent());
    }
}
