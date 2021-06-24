


package com.example.controllers;


import com.example.data.Book;
import com.example.data.BookRepository;
import com.example.data.BooksPageDto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/books")
public class PostponedPageController {
    @GetMapping("/postponed")
    public String postponedPage(){
        return "postponed";
    }

}






/**    private final BookRepository bookRepository;

    public PostponedPageController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{slugBook}")
    public String getPostponedPage(@Param(value = "slugBook") String slugBook, Model model){

        Book book = bookRepository.findBookBySlug(slug);

        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("book",book);
        return "/postponed/postponed";
    }

    @GetMapping("/postponed/{slug}")
    public Book postponedPage(@Param(value = "slug") String slug){
        return  bookRepository.findBookBySlug(slug);
    }
}   **/
