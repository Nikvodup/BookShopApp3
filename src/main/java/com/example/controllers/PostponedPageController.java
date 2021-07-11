


package com.example.controllers;


import com.example.data.Book;
import com.example.data.BookRepository;
import com.example.data.PopularityAndRatingService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public PostponedPageController(BookRepository bookRepository, PopularityAndRatingService popularityAndRatingService) {
        this.bookRepository = bookRepository;
        this.popularityAndRatingService = popularityAndRatingService;
    }


    @ModelAttribute("serverTime")
    public String getTime(){
        return new SimpleDateFormat("hh:mm:ss").format(new Date());
    }

    @ModelAttribute(name = "bookPostponed")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    List<Book> booksFromCookieSlugs = new ArrayList<>();

    public List<Book> getBooksFromCookieSlugs() {
        return booksFromCookieSlugs;
    }

    public void setBooksFromCookieSlugs(List<Book> booksFromCookieSlugs) {
        this.booksFromCookieSlugs = booksFromCookieSlugs;
    }

    @GetMapping("/postponed")
    public String handlePostponedRequest(@CookieValue(value = "postponedContents", required = false) String postponedContents,
                                    Model model) {
        if (postponedContents == null || postponedContents.equals("")) {
            model.addAttribute("isPostponedEmpty", true);
        } else {
            model.addAttribute("isPostponedEmpty", false);
            postponedContents = postponedContents.startsWith("/") ? postponedContents.substring(1) : postponedContents;
            postponedContents = postponedContents.endsWith("/") ? postponedContents.substring(0, postponedContents.length() - 1) : postponedContents;
            String[] cookieSlugs = postponedContents.split("/");
             booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookPostponed", booksFromCookieSlugs);
           // model.addAttribute("total", booksFromCookieSlugs.stream().reduce(0,(sum,p)->sum+=p.discountPrice(),(sum1,sum2)->sum1 + sum2));
        }

        return "postponed";
    }


    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    public String handleRemoveBookFromPostponedRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "postponedContents", required = false) String postponedContents, HttpServletResponse response, Model model){

        if (postponedContents != null && !postponedContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("postponedContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
        }else {
            model.addAttribute("isPostponedEmpty", true);
        }

        return "redirect:/books/postponed";
    }


    @PostMapping("/changeBookStatus/postponed/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug, @CookieValue(name = "postponedContents",
            required = false) String postponedContents, HttpServletResponse response, Model model) {

        if (postponedContents == null || postponedContents.equals("")) {
            Cookie cookie = new Cookie("postponedContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
        } else if (!postponedContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(postponedContents).add(slug);
            Cookie cookie = new Cookie("postponedContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
        }

        popularityAndRatingService.updatePostponedNumber(slug);

        return "redirect:/books/" + slug;
    }

}





