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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@ControllerAdvice
@RequestMapping("/books")
 class BookshpCartController {


    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    List<Book> booksFromCookieSlugs = new ArrayList<>();

    public List<Book> getBooksFromCookieSlugs() {
        return booksFromCookieSlugs;
    }

    private final BookRepository bookRepository;
    private final PopularityAndRatingService popularityAndRatingService;

    @Autowired
    public BookshpCartController(BookRepository bookRepository, PopularityAndRatingService popularityAndRatingService) {
        this.bookRepository = bookRepository;
        this.popularityAndRatingService = popularityAndRatingService;
    }


    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookCart", booksFromCookieSlugs);
            model.addAttribute("total", booksFromCookieSlugs.stream().reduce(0,(sum,p)->sum+=p.discountPrice(),(sum1,sum2)->sum1 + sum2));

        }
        return "cart";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "cartContents", required = false) String cartContents, HttpServletResponse response, Model model){

        if (cartContents != null && !cartContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }else {
            model.addAttribute("isCartEmpty", true);
        }

        return "redirect:/books/cart";
    }




    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug, @CookieValue(name = "cartContents",
            required = false) String cartContents, HttpServletResponse response, Model model) {


        if (cartContents == null || cartContents.equals("")) {

            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);

        } else if (!cartContents.contains(slug)) {

            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);

        }

         popularityAndRatingService.updateCartNumber(slug);


        return "redirect:/books/" + slug;

    }

}

