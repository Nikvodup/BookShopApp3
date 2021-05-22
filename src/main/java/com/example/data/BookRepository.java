package com.example.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBooksByAuthor_FirstName(String name);

    @Query("from Book")
    List<Book> customFindAllBooks();

    //NEW BOOK REST REPOSITORY COMMANDS

    List<Book> findBooksByAuthorFirstNameContaining(String authorFirstName);

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("select b from Book b where b.isBestseller=1")
    Page<Book> getBestsellers(Pageable pageable);

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)", nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

   // @Query("select b from Book b where b.pubDate > b.since ")
    @Query(value = "SELECT * FROM books WHERE pub_Date BETWEEN '2020-08-01' AND '2021-05-20'", nativeQuery = true)
    Page<Book> getRecent(Pageable pageable);

   // Page<Book> findBooksByPubDateGreaterThanOrSince(LocalDate pubDate, LocalDate since,Pageable pageable);


    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);


     Integer countBooksByGenre(String genre);


     @Query(value = "SELECT * FROM books WHERE author_id =:id", nativeQuery = true)
     Page<Book> findBooksByAuthorId(Pageable pageable, @Param(value ="id") Integer id);

}

