package com.example.controllers;


import com.example.data.BookService;
import com.example.data.BooksPageDto;
import com.example.data.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final BookService bookService;

    public TagController(TagService tagService, BookService bookService) {
        this.tagService = tagService;
        this.bookService = bookService;
    }





    @GetMapping("/tags/{tagId}")
    public String getTag(@PathVariable Integer tagId, Model model) {
        model.addAttribute("tagBooks", bookService.getBooksByTag(tagId, 0, 5));
        model.addAttribute("tag", tagService.getTag(tagId));
        return "/tags/index";
    }

    @GetMapping("/books/tag/{tagId}")
    @ResponseBody
    public BooksPageDto getNextPage(
            @PathVariable Integer tagId,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ) {
        return new BooksPageDto(bookService.getBooksByTag(tagId, offset, limit).getContent());
    }


}
