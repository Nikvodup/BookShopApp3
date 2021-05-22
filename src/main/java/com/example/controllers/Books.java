package com.example.controllers;


import com.example.data.Author;
import com.example.data.AuthorRepository;
import com.example.data.BookService;
import com.example.data.BooksPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/books")
public class Books {

    private BookService bookService;
    private AuthorRepository authorRepository;
    private AuthorsController authorsController;

     @Autowired
    public Books(BookService bookService) {
        this.bookService = bookService;
    }
//--------------------------------Recent Page-----------------
   @ModelAttribute("recentBooks")
   public List recentBooks(LocalDate pubDate, LocalDate since){
    return bookService.getRecentPage(0, 6).getContent();
}



    @GetMapping("/recent")
    public String getRecentBooks( Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("from", LocalDate.now().minus(Period.ofMonths(6)));
        model.addAttribute("upTo", LocalDate.now());
        return "/books/recent";
    }

    @GetMapping("/books/recentBooks")
    @ResponseBody
    public BooksPageDto getRecentPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit,LocalDate pubDate, LocalDate since) {
        return new BooksPageDto(bookService.getRecentPage(offset, limit).getContent());
    }

//--------------------------Popular Page--------------------------

    @ModelAttribute("bestsellers")
    public List bestsellerBooks(){
        return bookService.getPageOfBestsellers(0,6).getContent();
    }


    @GetMapping("/popular")
    public String popularPage( Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "/books/popular";
    }


    @GetMapping("/books/bestsellers")
    @ResponseBody
    public BooksPageDto getBestSellersPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfBestsellers(offset, limit).getContent());
    }

    //------------------------------Slug Page----------------------------
    @GetMapping("/slug")
    public String slugPage(Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "/books/slug";
    }



    @GetMapping("/{authorSlug.id}")
    public String authorListPage(@PathVariable("authorSlug.id") Integer id, Model model){
       Author  author = authorRepository.findAuthorById(id);
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("author", author);
        model.addAttribute("thisauthorPage", bookService.findBooksByAuthorId(0,6,id));
        return "/books/author";
    }

}
