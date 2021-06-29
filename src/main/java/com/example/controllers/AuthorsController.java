package com.example.controllers;

import com.example.data.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/authors")
@Api(description = "authors data")
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


    @GetMapping("/authors")
    public String authorsPage(){
        return "/authors/index";
    }

   // @GetMapping("/slug")
  //  public String slugPage(){return "/authors/slug";}

    @GetMapping("/authors/{id}")
    public String authorPage(@PathVariable("id") Integer id,  Model model){


        Author author = authorRepository.findAuthorById(id);

        model.addAttribute("authorSlug", author);
        model.addAttribute("thisauthorLine", bookService.findBooksByAuthorId(0,6,id));
        model.addAttribute("countBooksByAuthorId", bookService.getCount(id));

        return "/authors/slug";
    }



    @ApiOperation("method to get a map of authors")
     @GetMapping("/api/authors")
     @ResponseBody  // something to memorize!!!
    public Map<String,List<Author>> authors(){
        return authorService.getAuthorsMap();
     }



   @GetMapping("/author/{id}")
    public String authorListPage(@PathVariable Integer id, Model model){
        Author  author = authorRepository.findAuthorById(id);
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("author", author);
        model.addAttribute("authorBooks", bookService.findBooksByAuthorId(0,6,id).getContent());
        return "/author/author";
    }






  @GetMapping(value = "/author/author/{id}")
    @ResponseBody
    public BooksPageDto getNextAuthorBooksPage(
            @PathVariable Integer id,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ) {
        return new BooksPageDto( bookService.findBooksByAuthorId(id, offset, limit).getContent());
    }








}
