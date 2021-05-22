package com.example.controllers;

import com.example.data.*;
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
    private final BookService bookService;


    @Autowired
    public AuthorsController(AuthorService authorService, AuthorRepository authorRepository, BookService bookService) {
        this.authorService = authorService;
        this.authorRepository = authorRepository;
        this.bookService = bookService;
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

    @GetMapping("/{id}")
    public String authorPage(@PathVariable("id") Integer id,  Model model){


        Author author = authorRepository.findAuthorById(id);

        model.addAttribute("authorSlug", author);
        model.addAttribute("thisauthorLine", bookService.findBooksByAuthorId(0,6,id));

        return "/authors/slug";
    }



}
