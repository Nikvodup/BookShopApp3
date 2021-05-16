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
public class CrimeStoryController {
    private final GenresService genresService;

    @Autowired
    public CrimeStoryController(GenresService genresService){
        this.genresService=genresService;
    }

    @GetMapping("/genres/crime_story")
    public String crimeStoryPage(){
        return "/genres/crime_story";
    }

    //---------------------Thriller--------------

    @ModelAttribute("thriller")
    public List<Book> lifeStory(){
        return genresService.getLifeStoryPage(0,6).getContent();
    }

    @GetMapping("/books/thrillerLine")
    @ResponseBody
    public BooksPageDto getThrillerPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getThrillerPage(offset,limit).getContent());
    }

    //-----------------------Spy Story---------------------------

    @ModelAttribute("spystory")
    public List<Book> spyStory(){
        return genresService.getSpyStoryPage(0,6).getContent();
    }

    @GetMapping("/books/spystoryLine")
    @ResponseBody
    public BooksPageDto getSpyPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getSpyStoryPage(offset,limit).getContent());
    }

    //------------Political Crime-----------------

    @ModelAttribute("politicalcrime")
    public List<Book> politicalCrime(){
        return genresService.getPoliticalCrimePage(0,6).getContent();
    }

    @GetMapping("/books/politicalcrimeLine")
    @ResponseBody
    public BooksPageDto getPoliticalCrimePage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getPoliticalCrimePage(offset,limit).getContent());
    }
    //--------------Classical Crime--------------

    @ModelAttribute("classicalcrime")
    public List<Book> classicalCrime(){
        return genresService.getClassicalCrimePage(0,6).getContent();
    }

    @GetMapping("/books/classicalcrimeLine")
    @ResponseBody
    public BooksPageDto getClassicalCrimePage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getClassicalCrimePage(offset,limit).getContent());
    }

    //--------------Ironic Crime----------------

    @ModelAttribute("ironiccrime")
    public List<Book> ironicCrime(){
        return genresService.getIronicCrimePage(0,6).getContent();
    }

    @GetMapping("/books/ironiccrimeLine")
    @ResponseBody
    public BooksPageDto getIronicCrimePage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getIronicCrimePage(offset,limit).getContent());
    }

}
