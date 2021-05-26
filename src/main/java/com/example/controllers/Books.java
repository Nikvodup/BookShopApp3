package com.example.controllers;


import com.example.data.*;
import org.springframework.beans.factory.annotation.Autowired;
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
   public List recentBooks(LocalDate since){

    return bookService.getRecentPage(since,0,6).getContent();
}



    @GetMapping("/recent_page")
    public String getRecentBooks( Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("from", LocalDate.now().minus(Period.ofMonths(6)));
        model.addAttribute("upTo", LocalDate.now());
        model.addAttribute("bookService", bookService);
        model.addAttribute("period", bookService.getPeriod());

        return "/books/recent_page";
    }

    @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto getRecentPage(LocalDate since,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getRecentPage(since,offset, limit).getContent());
    }

//--------------------------Popular Page--------------------------

 /**   @ModelAttribute("bestsellers")
    public List bestsellerBooks(){
        return bookService.getPageOfBestsellers(0,6).getContent();
    }  **/


    @GetMapping("/popular_page")
    public String popularPage( Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("bestsellers", bookService.getPageOfBestsellers(0,6).getContent());
        return "/books/popular_page";
    }


    @GetMapping("/books/popular")
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

   //----------------------------Page of a specific author----------------------

 /**   @GetMapping("/{authorSlug.id}")
    public String authorListPage(@PathVariable("authorSlug.id") Integer id, Model model){
       Author  author = authorRepository.findAuthorById(id);
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("author", author);
        model.addAttribute("thisauthorPage", bookService.findBooksByAuthorId(0,6,id));
        return "/books/author";
    }  **/

}
