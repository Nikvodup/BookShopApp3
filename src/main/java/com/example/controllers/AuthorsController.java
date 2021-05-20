package com.example.controllers;

import com.example.data.Author;
import com.example.data.AuthorRepository;
import com.example.data.AuthorService;
import com.example.data.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorService authorService;
    private final AuthorRepository authorRepository;


    @Autowired
    public AuthorsController(AuthorService authorService, AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.authorRepository = authorRepository;
    }

    @ModelAttribute("authorsMap")
    public Map<String,List<Author>> authorsMap(){
        return authorService.getAuthorsMap();
    }

    @ModelAttribute("serverTime")
    public String  date(){
        return new SimpleDateFormat("hh:mm:ss").format(new Date());
    }


    @GetMapping("")
    public String authorsPage(){
        return "/authors/index";
    }

   // @GetMapping("/slug")
  //  public String slugPage(){return "/authors/slug";}

    @GetMapping("/{lastname}/{firstname}")
    public String authorPage(@PathVariable("lastname") String lastName, @PathVariable("firstname") String firstName,  Model model){


        Author author = authorRepository.findAuthorByLastNameAndFirstNameContaining(lastName,firstName);

        model.addAttribute("authorSlug", author);

        return "/authors/slug";
    }



}
