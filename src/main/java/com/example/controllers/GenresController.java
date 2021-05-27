package com.example.controllers;


import com.example.data.*;
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



    @GetMapping("")
    public String genresPage( Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        model.addAttribute("countSF", bookService.getCount("science_fiction"));
        model.addAttribute("countAD", bookService.getCount("ancient_drama"));
        model.addAttribute("countActionStory", bookService.getCount("action_story"));
        model.addAttribute("countScript", bookService.getCount("movie_script"));
        model.addAttribute("countDrama", bookService.getCount("drama"));
        model.addAttribute("countComedy", bookService.getCount("ironic_detective"));
        model.addAttribute("countLifeStory", bookService.getCount("life_story"));
        model.addAttribute("countFantasy", bookService.getCount("fantasy"));
        model.addAttribute("countHorror", bookService.getCount("horror_story"));
        model.addAttribute("countAdventures", bookService.getCount("adventures"));
        model.addAttribute("countThriller", bookService.getCount("thriller"));
        model.addAttribute("countIronicDetective", bookService.getCount("ironic_detective"));
        model.addAttribute("countSpyDetective", bookService.getCount("spy_story"));
        model.addAttribute("countClassicDetective", bookService.getCount("classical_detective"));
        model.addAttribute("countPoliticalDetective", bookService.getCount("political_detective"));


        return "/genres/index";
    }

    @GetMapping("/slug")
    public String slugPage(Model model){
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        return "/genres/slug";
    }


    //=================================Specific Genres Pages===================



  // ==============================Light Reading Begin=======================

    //----------------------------------Science Fiction------------------

    @GetMapping("/science_fiction")
    public String scienceFictionPage(Model model){
        model.addAttribute("sciencefiction", genresService.getScienceFictionBooksPage(0,6).getContent());
        model.addAttribute("countSF", bookService.getCount("science_fiction"));
        return  "/genres/science_fiction";
    }

    @GetMapping("/books/genre/'sciencefiction'")
    @ResponseBody
    public BooksPageDto getSFPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getScienceFictionBooksPage(offset,limit).getContent());
    }

    //---------------------------------Action Story-------------------------


    @GetMapping("/action_story")
    public String actionStoryPage(Model model){
        model.addAttribute("actionstory", genresService.getActionStoryBooksPage(0,6).getContent());
        model.addAttribute("countAS",  bookService.getCount("action_story"));
        return  "/genres/action_story";
    }

    @GetMapping("/books/action_story")
    @ResponseBody
    public BooksPageDto getActionPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getActionStoryBooksPage(offset,limit).getContent());
    }

    //---------------------------Fantasy------------------------

    @GetMapping("/fantasy_page")
    public String fantasyStoryPage(Model model){
        model.addAttribute("fantasy", genresService.getFantasyBooksPage(0,6).getContent());
        model.addAttribute("countFan", bookService.getCount("fantasy"));
        return "/genres/fantasy_page";
    }

    @GetMapping("/books/genre/fantasy")
    @ResponseBody
    public BooksPageDto getFantasyPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getFantasyBooksPage(offset,limit).getContent());
    }

    //----------------------------------Horror Stories-------------------

    @GetMapping("/horror")
    public String horrorStoryPage(Model model){
        model.addAttribute("horror", genresService.getHorrorBooksPage(0,6).getContent());
        model.addAttribute("countHorror", bookService.getCount("horror_story"));
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
        model.addAttribute("countAdventures", bookService.getCount("adventures"));
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
        model.addAttribute("countThriller", bookService.getCount("thriller"));
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
        model.addAttribute("countIronic", bookService.getCount("ironic_detective"));
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
        model.addAttribute("countSpy", bookService.getCount("spy_story"));
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
        model.addAttribute("countClassic", bookService.getCount("classical_detective"));
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
        model.addAttribute("countPolitical", bookService.getCount("political_detective"));
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
        model.addAttribute("countLifestory", bookService.getCount("life_story"));
        return  "/genres/life_story";
    }

    @GetMapping("/books/lifestory")
    @ResponseBody
    public BooksPageDto getLifeStoryPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getLifeStoryPage(offset,limit).getContent());
    }

    //======================Dramaturgy===============

    //------------------------Ancient Drama------------

    @GetMapping("/ancientdrama")
    public String ancientDramaPage(String genre, Model model){
        model.addAttribute("ancientdrama", genresService.getGenreBooks("ancient_drama",0,6).getContent());
                model.addAttribute("countAncientDrama", bookService.getCount("ancient_drama"));
                return "/genres/ancientdrama";
    }

    @GetMapping("/books/ancientdrama")
    @ResponseBody
    public BooksPageDto getAncientDramaPage(String genre,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getGenreBooks(genre,offset,limit).getContent());
    }

    //--------------------------Movie Script-----------------------

    @GetMapping("/movie_script")
    public String moviescriptPage( Model model){
        model.addAttribute("moviescipt", genresService.getGenreBooks("movie_script",0,6).getContent());
        model.addAttribute("countMovieScript", bookService.getCount("movie_script"));
        return "/genres/movie_script";
    }

    @GetMapping("/books/movie_script")
    @ResponseBody
    public BooksPageDto getMovieScriptPage(String genre,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getGenreBooks(genre,offset,limit).getContent());
    }

    //-------------------------Comedy-----------------
    @GetMapping("/comedy")
    public String comedyPage( Model model){
        model.addAttribute("comedy", genresService.getGenreBooks("ironic_detective",0,6).getContent());
        model.addAttribute("countComedy", bookService.getCount("ironic_detective"));
        return "/genres/comedy";
    }

    @GetMapping("/books/comedy")
    @ResponseBody
    public BooksPageDto getComedyPage(String genre,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getGenreBooks(genre,offset,limit).getContent());
    }

    //-----------------------Drama----------------------
    @GetMapping("/drama")
    public String dramaPage( Model model){
        model.addAttribute("drama", genresService.getGenreBooks("drama",0,6).getContent());
        model.addAttribute("countDrama", bookService.getCount("drama"));
        return "/genres/drama";
    }

    @GetMapping("/books/drama")
    @ResponseBody
    public BooksPageDto getDramaPage(String genre,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit ){
        return new BooksPageDto(genresService.getGenreBooks(genre,offset,limit).getContent());
    }

    //===================================Business Books==================




}