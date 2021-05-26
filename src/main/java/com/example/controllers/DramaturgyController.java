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

    //--------------------------Comedy--------------------
    @ModelAttribute("comedy")
    public List<Book> comedyPage(){
        return genresService.getGenreBooks("ironic_detective",0,6).getContent();
    }

    @ModelAttribute("countComedy")
    public Integer countComedyBooks(){
        return bookService.getCount("ironic_detective");
    }

    @GetMapping("/books/drama")
    @ResponseBody
    public BooksPageDto getComedy(String genre,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getGenreBooks(genre,offset,limit).getContent());
    }

    //------------------------Modern Drama------------------

    @ModelAttribute("drama")
    public List<Book> dramaPage(){
        return genresService.getGenreBooks("drama",0,6).getContent();
    }

    @ModelAttribute("countDrama")
    public Integer countDramaBooks(){
        return bookService.getCount("drama");
    }

    @GetMapping("/books/dramaLine")
    @ResponseBody
    public BooksPageDto getDramaPage(String genre,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getGenreBooks(genre,offset,limit).getContent());
    }

    //---------------------------Movie Script------------------
    @ModelAttribute("script")
    public List<Book> scriptPage(){
        return genresService.getGenreBooks("movie_script",0,6).getContent();
    }

    @ModelAttribute("countScript")
    public Integer countMovieScript(){
        return bookService.getCount("movie_script");
    }

    @GetMapping("/books/movie_script")
    @ResponseBody
    public BooksPageDto getScriptPage(String genre,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getGenreBooks(genre,offset,limit).getContent());
    }

}
