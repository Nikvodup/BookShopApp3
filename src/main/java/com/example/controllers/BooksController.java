package com.example.controllers;

import com.example.data.*;
import com.example.security.BookstoreUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookRepository bookRepository;
    private final ResourceStorage storage;
    private final BookshpCartController bookshpCartController;
    private final BookReviewLikeService reviewLikeService;
    private final BookReviewService bookReviewService;
    private final RatingService ratingService;
    private final BookService bookService;

    private final static String BOOKS_REDIRECT = "redirect:/books/";

    @Autowired
    public BooksController(BookRepository bookRepository, ResourceStorage storage, BookshpCartController bookshpCartController, BookReviewLikeService reviewLikeService, BookReviewService bookReviewService, RatingService ratingService, BookService bookService) {
        this.bookRepository = bookRepository;
        this.storage = storage;
        this.bookshpCartController = bookshpCartController;
        this.reviewLikeService = reviewLikeService;
        this.bookReviewService = bookReviewService;
        this.ratingService = ratingService;
        this.bookService = bookService;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable(value = "slug") String slug, Model model){
         Book book = bookRepository.findBookBySlug(slug);
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
       model.addAttribute("slugBook", book);
        model.addAttribute("rating", ratingService.findBookById(book.getId()));
        model.addAttribute("ratingTotalAndAvg", ratingService.getTotalAndAvgStars(book.getId()));
       model.addAttribute("isInCart", bookshpCartController.getBooksFromCookieSlugs().contains(slug));
        return "/books/slug";
    }



    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws  IOException {

        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookRepository.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate); //save new path in db here

        return "redirect:/books/" + slug ;
    }


    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {

        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: " + data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }


    @GetMapping("/postponed/{slug}")
    public String getPostponedPage(@Param(value = "slug") String slug, Model model){

        Book book = bookRepository.findBookBySlug(slug);

        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("slugBook", book);
        return "postponed";
    }


    @PostMapping("/addReview/{slug}")
    public String addReview(
            @RequestParam("reviewText") String reviewText,
            @RequestParam(value = "ratingReview", required = false) Integer ratingReview,
            @PathVariable("slug") String slug,
            @AuthenticationPrincipal BookstoreUserDetails user
    ) {
        Book book = bookService.findBookBySlug(slug);
        BookReview review = new BookReview(null, book, user.getBookstoreUser().getId(), user.getBookstoreUser().getName(), new Date(),
                reviewText, ratingReview == null ? 1 : ratingReview, Collections.emptyList());
        bookReviewService.saveReview(review);

        return BOOKS_REDIRECT + slug;
    }

    @PostMapping("/changeBookStatus/review/{slug}")
    public String handleChangeBookStatus(
            @RequestBody BookRatingRequestData bookRatingRequestData, @PathVariable("slug") String slug) {
        ratingService.saveRating(ratingService.findBookById(bookService.findBookBySlug(slug).getId()), bookRatingRequestData.getValue());
        return BOOKS_REDIRECT + slug;
    }

    @PostMapping("/rateBookReview/{bookSlug}")
    public String handleBookReviewRateChanging(@RequestBody BookReviewLikeValue reviewLikeValue,
                                               @PathVariable("bookSlug") String bookSlug,
                                               @AuthenticationPrincipal BookstoreUserDetails user) {
        if (nonNull(user)) {
            reviewLikeService.saveReviewLike(user.getBookstoreUser(), reviewLikeValue.getReviewid(), reviewLikeValue.getValue());
        }
        return "redirect:/books/" + bookSlug;
    }

}
