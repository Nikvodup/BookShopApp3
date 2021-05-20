package com.example.controllers;


import com.example.data.Book;
import com.example.data.BookService;
import com.example.data.BooksPageDto;
import com.example.data.GenresService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/genres")
public class GenresController {

    private BookService bookService;
    private GenresService genresService;

    public GenresController(BookService bookService,GenresService genresService) {
        this.bookService = bookService;
        this.genresService = genresService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();
    }

    @GetMapping("")
    public String genresPage(BookService bookService,Book book,Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));

        return "/genres/index";
    }

    @GetMapping("/slug")
    public String slugPage(Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "/genres/slug";
    }

  /**  @GetMapping("/light_reading")
    public String lightReadingPage(){
        return "/genres/light_reading";
    }

    @GetMapping("/serious_reading")
    public String seriousReadingPage(){
        return "/genres/serious_reading";
    } **/

    @GetMapping("/business_books")
    public String businessReadingPage(){
        return "/genres/business_books";
    }

    @GetMapping("/drama")
    public String dramaReadingPage(){
        return "/genres/drama";
    }

  /**  @GetMapping("/crime_story")
    public String crimeStoryPage(){
        return "/genres/crime_story";
    } **/

    //=================================Specific Genres Pages===================



  // ==============================Light Reading Begin=======================

    //----------------------------------Science Fiction------------------

    @GetMapping("/science_fiction")
    public String scienceFictionPage(Model model){
        model.addAttribute("sciencefiction", genresService.getScienceFictionBooksPage(0,6).getContent());
        return  "/genres/science_fiction";
    }

    @GetMapping("/books/sciencefiction")
    @ResponseBody
    public BooksPageDto getSFPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getScienceFictionBooksPage(offset,limit).getContent());
    }

    //---------------------------------Action Story-------------------------


    @GetMapping("/action_story")
    public String actionStoryPage(Model model){
        model.addAttribute("actionstory", genresService.getActionStoryBooksPage(0,6).getContent());
        return  "/genres/action_story";
    }

    @GetMapping("/books/action_story")
    @ResponseBody
    public BooksPageDto getActionPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getActionStoryBooksPage(offset,limit).getContent());
    }

    //---------------------------Fantasy------------------------

    @GetMapping("/fantasy")
    public String fantasyStoryPage(Model model){
        model.addAttribute("fantasy", genresService.getFantasyBooksPage(0,6).getContent());
        return  "/genres/fantasy";
    }

    @GetMapping("/books/fantasystory")
    @ResponseBody
    public BooksPageDto getFantasyPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getFantasyBooksPage(offset,limit).getContent());
    }

    //----------------------------------Horror Stories-------------------

    @GetMapping("/horror")
    public String horrorStoryPage(Model model){
        model.addAttribute("horror", genresService.getHorrorBooksPage(0,6).getContent());
        return  "/genres/horror";
    }

    @GetMapping("/books/horrorstory")
    @ResponseBody
    public BooksPageDto getHorrorPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getHorrorBooksPage(offset,limit).getContent());
    }

    //-----------------------------Adventures-------------------

    @GetMapping("/adventures")
    public String adventuresStoryPage(Model model){
        model.addAttribute("adventures", genresService.getAdventuresBooksPage(0,6).getContent());
        return  "/genres/adventures";
    }

    @GetMapping("/books/adventures")
    @ResponseBody
    public BooksPageDto getAdventuresPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getAdventuresBooksPage(offset,limit).getContent());
    }

    //===============================LIGHT READING END======================



    //==============================CRIME STORIES BEGIN========================

    //------------------------Thriller--------------------

    @GetMapping("/thriller")
    public String thrillerPage(Model model){
        model.addAttribute("thriller", genresService.getThrillerPage(0,6).getContent());
        return  "/genres/thriller";
    }

   @GetMapping("/books/thriller")
    @ResponseBody
    public BooksPageDto getThrillerPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getThrillerPage(offset,limit).getContent());
    }

    //--------------------------- Ironic Crime Story ----------------------

    @GetMapping("/ironic_crime")
    public String ironicCrimePage(Model model){
        model.addAttribute("ironiccrime", genresService.getIronicCrimePage(0,6).getContent());
        return  "/genres/ironic_crime";
    }

    @GetMapping("/books/ironiccrime")
    @ResponseBody
    public BooksPageDto getIronicCrimePage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getIronicCrimePage(offset,limit).getContent());
    }

    //---------------------------Spy Crime Story----------------------

    @GetMapping("/spy_crime")
    public String spyCrimePage(Model model){
        model.addAttribute("spycrime", genresService.getSpyStoryPage(0,6).getContent());
        return  "/genres/spy_crime";
    }

    @GetMapping("/books/spycrime")
    @ResponseBody
    public BooksPageDto getSpyCrimePage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getSpyStoryPage(offset,limit).getContent());
    }

    //-----------------------------Classic Crime Story _________________________

    @GetMapping("/classic_crime")
    public String classicCrimePage(Model model){
        model.addAttribute("classiccrime", genresService.getClassicalCrimePage(0,6).getContent());
        return  "/genres/classic_crime";
    }

    @GetMapping("/books/classiccrime")
    @ResponseBody
    public BooksPageDto getClassicCrimePage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getClassicalCrimePage(offset,limit).getContent());
    }

    //-------------------------Political Crime Story -----------------------------

    @GetMapping("/political_crime")
    public String politicalCrimePage(Model model){
        model.addAttribute("politicalcrime", genresService.getPoliticalCrimePage(0,6).getContent());
        return  "/genres/political_crime";
    }

    @GetMapping("/books/politicalcrime")
    @ResponseBody
    public BooksPageDto getPoliticalCrimePage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getPoliticalCrimePage(offset,limit).getContent());
    }

    //============================CRIME STORIES END===================================

    //==============================Serious Reading=================

    @GetMapping("/life_story")
    public String lifeStoryPage(Model model){
        model.addAttribute("lifestory", genresService.getLifeStoryPage(0,6).getContent());
        return  "/genres/life_story";
    }

    @GetMapping("/books/lifestory")
    @ResponseBody
    public BooksPageDto getLifeStoryPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getLifeStoryPage(offset,limit).getContent());
    }

}