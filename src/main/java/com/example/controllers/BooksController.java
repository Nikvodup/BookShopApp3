package com.example.controllers;

import com.example.data.Book;
import com.example.data.BookRepository;
import com.example.data.BooksPageDto;
import com.example.data.ResourceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
//@RequestMapping("/books")
public class BooksController {

    private final BookRepository bookRepository;
    private final ResourceStorage storage;

    @Autowired
    public BooksController(BookRepository bookRepository, ResourceStorage storage) {
        this.bookRepository = bookRepository;
        this.storage = storage;
    }

    @GetMapping("/books/{slug}")
    public String bookPage(@PathVariable(value = "slug") String slug, Model model){
         Book book = bookRepository.findBookBySlug(slug);

       model.addAttribute("slugBook", book);

        return "/books/slug";
    }



    @PostMapping("/books/{slug}/img/save")
    public String saveNewBoookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException, IOException {

        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookRepository.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate); //save new path in db here

        return ("redirect:/books/" + slug);
    }


    @GetMapping("/books/download/{hash}")
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
        return "/postponed/postponed";
    }

/**    @GetMapping("/{slug}")
    @ResponseBody
    public Book postponedBook(@Param(value = "slug") String slug){
        return bookRepository.findBookBySlug(slug);
    }  **/


}