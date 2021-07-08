package com.example.controllers;

import com.example.data.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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



    @GetMapping("/authors/slug/{id}")
    public String authorPage(@PathVariable("id") Integer id,  Model model){
        Page<Book> bookPage = bookService.getBooksByAuthorId(id, 0, 6);
        model.addAttribute("authorSlug",authorRepository.findAuthorById(id));
        model.addAttribute("thisauthorLine", bookPage.getContent());
        model.addAttribute("countBooksByAuthorId", bookPage.getTotalElements());
        return "/authors/slug";
    }



    @ApiOperation("method to get a map of authors")
     @GetMapping("/api/authors")
     @ResponseBody  // something to memorize!!!
    public Map<String,List<Author>> authors(){
        return authorService.getAuthorsMap();
     }



   @GetMapping("/books/author/page/{id}")
   @ResponseBody
   public BooksPageDto getNextAuthorBooksPage(
           @PathVariable Integer id,
           @RequestParam("offset") Integer offset,
           @RequestParam("limit") Integer limit
   ) {
       return new BooksPageDto( bookService.getBooksByAuthorId(id, offset, limit).getContent());
   }



    @GetMapping("/books/author/{authorId}")
    public String authorBooks(@PathVariable Integer authorId, Model model) {
        model.addAttribute("author", authorService.getAuthorsById(authorId));
        model.addAttribute("authorBooks", bookService.getBooksByAuthorId(authorId, 0, 5).getContent());
        return "/books/author";
    }

}
