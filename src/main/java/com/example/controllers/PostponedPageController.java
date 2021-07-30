


package com.example.controllers;


import com.example.annotations.UserActionToPostponedLoggable;
import com.example.data.*;
import com.example.security.BookstoreUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/books")
public class PostponedPageController {

    private final BookRepository bookRepository;
    private final PopularityAndRatingService popularityAndRatingService;
    private final BookService bookService;
    private final Book2Type book2Type = new Book2Type();

    @Autowired
    public PostponedPageController(BookRepository bookRepository, PopularityAndRatingService popularityAndRatingService, BookService bookService) {
        this.bookRepository = bookRepository;
        this.popularityAndRatingService = popularityAndRatingService;
        this.bookService = bookService;
    }


    @ModelAttribute("serverTime")
    public String getTime(){
        return new SimpleDateFormat("hh:mm:ss").format(new Date());
    }

    @ModelAttribute(name = "bookPostponed")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

  //  List<Book> booksFromCookieSlugs = new ArrayList<>();

  //  public List<Book> getBooksFromCookieSlugs() {
   //     return booksFromCookieSlugs;
  //  }

 //   public void setBooksFromCookieSlugs(List<Book> booksFromCookieSlugs) {
   //     this.booksFromCookieSlugs = booksFromCookieSlugs;
  //  }

    @GetMapping("/postponed")
    public String getPostponed(
            @AuthenticationPrincipal BookstoreUserDetails user,
            Model model) {
        List<Book> books = bookRepository.getPostponedBooks(user.getBookstoreUser().getId());
        if (books.isEmpty()) {
            model.addAttribute("isPostponedEmpty", true);
            model.addAttribute("bookPostponed", new ArrayList<Book>());
        } else {
            model.addAttribute("isPostponedEmpty", false);
            model.addAttribute("bookPostponed", books);
        }
        return "postponed";
    }


    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    @UserActionToPostponedLoggable
    public String handleRemoveBookFromCart(@PathVariable("slug") String slug,
                                           @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
        bookService.removeFromBook2User(book, user.getBookstoreUser());
        return "redirect:/books/postponed";
    }


    @PostMapping("/changeBookStatus/postponed/{slug}")
    @UserActionToPostponedLoggable
    public String handleChangeBookStatus(
            @PathVariable("slug") String slug,
            @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
        if (bookService.getPostponedBooks(user.getBookstoreUser().getId()).contains(book)) {
            return "redirect:/books/" + slug;
        } else {
            bookService.saveBook2User(book, user.getBookstoreUser(), book2Type.getTypeStatus().KEPT);
            popularityAndRatingService.updatePostponedNumber(slug);

        }
        return "redirect:/books/" + slug;

    }

}





