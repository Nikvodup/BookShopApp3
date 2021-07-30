package com.example.controllers;


import com.example.annotations.UserActionToCartLoggable;
import com.example.data.*;
import com.example.security.BookstoreUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@ControllerAdvice
@RequestMapping("/books")
 class BookshpCartController {


    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

   // List<Book> booksFromCookieSlugs = new ArrayList<>();

  //  public List<Book> getBooksFromCookieSlugs() {
 //       return booksFromCookieSlugs;
 //   }

    private final BookRepository bookRepository;
    private final PopularityAndRatingService popularityAndRatingService;
    private final BookService bookService;
    private final PaymentService paymentService;
    private final BalanceTransactionRepository balanceTransactionRepository;
    private final BalanceTransactionService balanceTransactionService;
    private final Book2Type book2Type = new Book2Type();


    @Autowired
    public BookshpCartController(BookRepository bookRepository, PopularityAndRatingService popularityAndRatingService,
                                 PaymentService paymentService, BookService bookService,
                                 BalanceTransactionRepository balanceTransactionRepository,
                                 BalanceTransactionService balanceTransactionService) {
        this.bookRepository = bookRepository;
        this.popularityAndRatingService = popularityAndRatingService;
        this.bookService = bookService;
        this.paymentService = paymentService;
        this.balanceTransactionRepository = balanceTransactionRepository;
        this.balanceTransactionService = balanceTransactionService;

    }


    @GetMapping("/cart")
    public String handleCartRequest(
            Model model,
            @AuthenticationPrincipal BookstoreUserDetails user,
            @RequestParam(value = "noMoney", required = false) Boolean noMoney) {

        List<Book> books = bookService.getCartBooks(user.getBookstoreUser().getId());
        if (books.isEmpty()) {
            model.addAttribute("isCartEmpty", true);

        } else {


            model.addAttribute("isCartEmpty", false);
            model.addAttribute("bookCart", books);
            model.addAttribute("total", books.stream().reduce(0,(sum,p)->sum+=p.discountPrice(),(sum1,sum2)->sum1 + sum2));
        }


        return "cart";
    }





    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    @UserActionToCartLoggable
    public String handleRemoveBookFromCartRequest(
            @PathVariable("slug") String slug,
            @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
        bookService.removeFromBook2User(book, user.getBookstoreUser());
        return "redirect:/books/cart";
    }




    @PostMapping("/changeBookStatus/{slug}")
    @UserActionToCartLoggable
    public String handleChangeBookStatus(
            @PathVariable("slug") String slug,
            @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
   //     if (bookService.isPaid(book, user.getBookstoreUser().getId(), false)) {
    //        return "redirect:/books/" + slug + "?isPaid=true";
    //    }
        if (bookService.getCartBooks(user.getBookstoreUser().getId()).contains(book)) {
            return "redirect:/books/" + slug;
        } else {
            bookService.saveBook2User(book, user.getBookstoreUser(), book2Type.getTypeStatus().CART);
            popularityAndRatingService.updateCartNumber(slug);
        }

        return "redirect:/books/" + slug;
    }




    @GetMapping("/pay")
    public String handlePay(@AuthenticationPrincipal BookstoreUserDetails user, Model model) {
        List<Book> books = bookService.getCartBooks(user.getBookstoreUser().getId());
        Double allSum = books.stream().mapToDouble(Book::discountPrice).sum();
        Integer accountMoney = (Integer) ((BindingAwareModelMap) model).get("accountMoney");
        if (accountMoney < allSum) {
            return  "redirect:/books/cart" + "?noMoney=true";
        }
        books.forEach(book -> bookService.saveBook2User(book, user.getBookstoreUser(),Book2Type.TypeStatus.PAID));
        String booksName = books.stream().map(book -> book.getTitle() + ", ").collect(Collectors.joining());
        String bookSizeText = books.size() == 1 ? books.size() + " книги: " : books.size() + " книг: ";
        BalanceTransaction balanceTransaction = new BalanceTransaction();
        balanceTransaction.setUserId(user.getBookstoreUser().getId());
        balanceTransaction.setValue((int) Math.round(allSum) * -1);
        balanceTransaction.setDescription("Покупка " + bookSizeText + booksName);
        balanceTransaction.setTime(new Date());
        balanceTransaction.setTypeStatus(BalanceTransaction.TypeStatus.OK);
        balanceTransactionRepository.save(balanceTransaction);
        return  "redirect:/books/cart";
    }

}

