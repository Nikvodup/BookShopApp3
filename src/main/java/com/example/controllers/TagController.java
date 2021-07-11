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




/**

    @GetMapping("/modern_literature_page")
    public String getModernLiteraturePage(Model model){
        model.addAttribute("modernLiterature", bookService.findbooksByTag(0,6,"modern_literature").getContent());
        return "/tags/modern_literature_page";

    }


    @GetMapping("/classic_literature_page")
    public String getClassicLiteraturePage(Model model){
        model.addAttribute("classicLiterature", bookService.findbooksByTag(0,6,"classic_literature").getContent());
        return "/tags/classic_literature_page";

    }

    @GetMapping("/foreign_literature_page")
    public String getForeignLiteraturePage(Model model){
        model.addAttribute("foreignLiterature", bookService.findbooksByTag(0,6,"foreign_literature").getContent());
        model.addAttribute("countForeignLiterature", bookService.getTagCount("foreign_literature"));
        return "/tags/foreign_literature_page";

    }

    @GetMapping("/fantasy_page")
    public String getFantasyPage(Model model){
        model.addAttribute("fantasy", bookService.findbooksByTag(0,6,"fantasy").getContent());
        model.addAttribute("countFantasy", bookService.getTagCount("fantasy"));
        return "/tags/fantasy_page";

    }

    @GetMapping("/english_literature_page")
    public String getEnglishPage(Model model){
        model.addAttribute("english_literature", bookService.findbooksByTag(0,6,"english_literature").getContent());
        model.addAttribute("countEnglishLiterature", bookService.getTagCount("english_literature"));
        return "/tags/english_literature_page";

    }

    @GetMapping("/russian_literature_page")
    public String getRussianPage(Model model){
        model.addAttribute("russianLiterature", bookService.findbooksByTag(0,6,"russian_litreture").getContent());
        model.addAttribute("countRussianLiterature", bookService.getTagCount("russian_litreture"));
        return "/tags/russian_literature_page";

    }

    @GetMapping("/science_fiction_page")
    public String getSFPage(Model model){
        model.addAttribute("sciencefiction", bookService.findbooksByTag(0,6,"science_fiction").getContent());
        model.addAttribute("countScienceFiction", bookService.getTagCount("science_fiction"));
        return "/tags/science_fiction_page";

    }

    @GetMapping("/books_for_children_page")
    public String getForChildrenPage(Model model){
        model.addAttribute("booksforchildren", bookService.findbooksByTag(0,6,"books_for_children").getContent());
        model.addAttribute("countForChildren", bookService.getTagCount("books_for_children"));
        return "/tags/books_for_children_page";

    }

    @GetMapping("/test_page")
    public String getTest(){
        return "/tags/test_page";
    }

    **/

    //-------------------

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
