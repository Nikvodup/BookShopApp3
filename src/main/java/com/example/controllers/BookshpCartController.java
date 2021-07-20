package com.example.controllers;




import com.example.data.*;
import com.example.security.BookstoreUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

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
    private final BookService bookService;
    private final PaymentService paymentService;
    private final BalanceTransactionRepository balanceTransactionRepository;
    private final BalanceTransactionService balanceTransactionService;

    @Autowired
    public BookshpCartController(BookRepository bookRepository, PopularityAndRatingService popularityAndRatingService, PaymentService paymentService, BookService bookService, BalanceTransactionRepository balanceTransactionRepository, BalanceTransactionService balanceTransactionService) {
        this.bookRepository = bookRepository;
        this.popularityAndRatingService = popularityAndRatingService;
        this.bookService = bookService;
        this.paymentService = paymentService;
        this.balanceTransactionRepository = balanceTransactionRepository;
        this.balanceTransactionService = balanceTransactionService;
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

