package com.example.data;


import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GenresService {

    private GenresRepository genresRepository;
    @Autowired
    public GenresService(GenresRepository genresRepository){
        this.genresRepository=genresRepository;
    }

    public Page<Book> getScienceFictionBooksPage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getScienceFictionBooks(nextPage);
    }

    public Page<Book> getActionStoryBooksPage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getActionStoryBooks(nextPage);
    }

    public Page<Book> getFantasyBooksPage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getFantasyBooks(nextPage);
    }


    public Page<Book> getHorrorBooksPage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getHorrorBooks(nextPage);
    }

    public Page<Book> getAdventuresBooksPage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getAdventuresBooks(nextPage);
    }

    public Page<Book> getLifeStoryPage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getLifeStory(nextPage);
    }

    public Page<Book> getThrillerPage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getThriller(nextPage);
    }

    public Page<Book> getSpyStoryPage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getSpyStory(nextPage);
    }

    public Page<Book> getPoliticalCrimePage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getPoliticalCrime(nextPage);
    }

    public Page<Book> getClassicalCrimePage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getClassicalCrime(nextPage);
    }

    public Page<Book> getIronicCrimePage(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.getIronicCrime(nextPage);
    }


    public Page<Book> getGenreBooks(String genre,Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return genresRepository.findBooksByGenre(genre, nextPage);
    }

}
