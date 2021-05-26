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
public class SeriousReadingController {
    private final GenresService genresService;

    @Autowired
    public SeriousReadingController(GenresService genresService){
        this.genresService=genresService;
    }

    @GetMapping("/genres/serious_reading")
    public String seriousReadingPage(){
        return "/genres/serious_reading";
    }

//----------------------life-story---------------

    @ModelAttribute("lifestory")
    public List<Book> lifeStory(){
        return genresService.getLifeStoryPage(0,6).getContent();
    }

    @GetMapping("/books/lifestoryLine")
    @ResponseBody
    public BooksPageDto getLifePage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getLifeStoryPage(offset,limit).getContent());
    }
}
