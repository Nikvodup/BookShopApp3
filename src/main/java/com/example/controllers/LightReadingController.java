package com.example.controllers;


import com.example.data.Book;
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
 class LightReadingPageController {

    private final GenresService genresService;

    @Autowired
    public LightReadingPageController(GenresService genresService){
        this.genresService=genresService;
    }

    @GetMapping("/genres/light_reading")
    public String lightReadingPage(){
        return "/genres/light_reading";
    }


    //------------------science-fiction--------------

   @ModelAttribute("sciencefiction")
    public List<Book> scienceFiction(){
        return genresService.getScienceFictionBooksPage(0,6).getContent();
     }


     @GetMapping("/books/sciencefictionLine")
     @ResponseBody
     public BooksPageDto getSFPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getScienceFictionBooksPage(offset,limit).getContent());
     }

     //--------------------------action-story-------------------

    @ModelAttribute("actionstory")
    public List<Book> actionStory(){
      return genresService.getActionStoryBooksPage(0,6).getContent();
    }

    @GetMapping("/books/actionstoryLine")
    @ResponseBody
    public BooksPageDto getActionStoryPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getActionStoryBooksPage(offset,limit).getContent());
    }

    //------------------------------fantasy--------------------

    @ModelAttribute("fantasy")
    public List<Book> fantasy(){
        return genresService.getFantasyBooksPage(0,6).getContent();
    }

    @GetMapping("/books/fantasy")
    @ResponseBody
    public BooksPageDto getFantasyPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getFantasyBooksPage(offset,limit).getContent());
    }

    //--------------------------horror-story------------------

    @ModelAttribute("horror")
    public List<Book> horror(){
        return genresService.getHorrorBooksPage(0,6).getContent();
    }

    @GetMapping("/books/horror")
    @ResponseBody
    public BooksPageDto getHorrorPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        return new BooksPageDto(genresService.getHorrorBooksPage(offset,limit).getContent());
    }

    //------------------------------adventures-----------------


    @ModelAttribute("adventures")
    public List<Book> adventures(){
        return genresService.getAdventuresBooksPage(0,6).getContent();
    }

    @GetMapping("/books/adventuresLine")
    @ResponseBody
    public BooksPageDto getAdventuresPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        return new BooksPageDto(genresService.getAdventuresBooksPage(offset,limit).getContent());
    }


}
