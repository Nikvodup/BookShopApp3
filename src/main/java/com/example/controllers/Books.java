package com.example.controllers;


import com.example.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.sql.Date;
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
 /** @ModelAttribute("recentBooks")
   public List recentBooks(LocalDate since){

    return bookService.getRecentPage(since,0,6).getContent();
} **/



  //  @GetMapping("/recent_page")
  //  public String getRecent(java.sql.Date date,Model model) {
       // Calendar calendar = Calendar.getInstance();
      //  calendar.add(Calendar.MONTH, -3);

      //  Date dateFrom  = new java.sql.Date((calendar.getTime()).getTime());

      //  model.addAttribute("recentBooks", bookService.getPageOfRecentBooksData(dateFrom, date, 0, 5));
      //  model.addAttribute("dateFrom", dateFrom);
      //  model.addAttribute("dateTo", date);
      //  return "/books/recent_page";
   // }


 /**   @GetMapping("/books/recent/page")
    @ResponseBody
    public BooksPageDto getNextSearchPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @RequestParam(value = "from", defaultValue = "") String  fromUtil,
            @RequestParam(value = "to", defaultValue = "") String toUtil) throws ParseException {

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date date1 = formater.parse(fromUtil);
        Date from = new java.sql.Date(date1.getTime());

        java.util.Date date2 = formater.parse(toUtil);
        Date to = new java.sql.Date(date2.getTime());


        return new BooksPageDto(bookService.getPageOfRecentBooksData(from,
                to, offset, limit).getContent());
    }  **/



    @GetMapping("/recent_page")
    public String getRecentBooks( Model model){
      //  model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
       // model.addAttribute("from", LocalDate.now().minus(Period.ofMonths(6)));
       // model.addAttribute("upTo", LocalDate.now());
       // model.addAttribute("bookService", bookService);
       // model.addAttribute("period", bookService.getPeriod());
        model.addAttribute("recentBooks", bookService.getRecentBooks(0,6).getContent());

        return "/books/recent_page";
    }

 /**   @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto getRecentPage(LocalDate since,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getRecentPage(since,offset, limit).getContent());
    } **/

//--------------------------Popular Page--------------------------

 /**   @ModelAttribute("bestsellers")
    public List bestsellerBooks(){
        return bookService.getPageOfBestsellers(0,6).getContent();
    }  **/


    @GetMapping("/popular_page")
    public String popularPage(Date date, Model model){
      //  model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
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
    public String slugPage(Date date,Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(date));
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
