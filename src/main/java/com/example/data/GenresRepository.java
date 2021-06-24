package com.example.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenresRepository extends JpaRepository<Book,Integer> {

    @Query("select b from Book b where b.genre='science_fiction'")
    Page<Book> getScienceFictionBooks(Pageable pageable);

    @Query("select b from Book b where b.genre='action_story'")
    Page<Book> getActionStoryBooks(Pageable pageable);


    @Query("select b from Book b where b.genre='fantasy'")
    Page<Book> getFantasyBooks(Pageable pageable);

    @Query("select b from Book b where b.genre='horror_story'")
    Page<Book> getHorrorBooks(Pageable pageable);

    @Query("select b from Book b where b.genre='adventures'")
    Page<Book> getAdventuresBooks(Pageable pageable);

    @Query("select b from Book b where b.genre='life_story'")
    Page<Book> getLifeStory(Pageable pageable);

    @Query("select b from Book b where b.genre='thriller'")
    Page<Book> getThriller(Pageable pageable);

    @Query("select b from Book b where b.genre='spy_story'")
    Page<Book> getSpyStory(Pageable pageable);

    @Query("select b from Book b where b.genre='political_detective'")
    Page<Book> getPoliticalCrime(Pageable pageable);

    @Query("select b from Book b where b.genre='classical_detective'")
    Page<Book> getClassicalCrime(Pageable pageable);

    @Query("select b from Book b where b.genre='ironic_detective'")
    Page<Book> getIronicCrime(Pageable pageable);


    //one method to find all books by a certain genre
    Page<Book> findBooksByGenre(String genre,Pageable pageable);

}
