package com.example.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


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




     //===========================================================================

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);


     Integer countBooksByGenre(String genre);


     @Query(value = "SELECT COUNT(*) FROM books WHERE author_id =:id", nativeQuery = true)
     Integer countBooksByAuthorId(@Param(value = "id") Integer id);


     @Query(value = "SELECT * FROM books WHERE author_id =:id", nativeQuery = true)
     Page<Book> findBooksByAuthorId(Pageable pageable, @Param(value ="id") Integer id);

     Book findBookBySlug(String slug);

     Page<Book> findBooksByTag(String tag, Pageable pageable);

     Integer countBooksByTag(String tag);

     Page<Book> findPageOfBooksByPubDateBetweenOrderByPubDate(Date dateFrom, Date dateTo, Pageable pageable);

    List<Book> findBooksBySlugIn(String[] slugs);

    //============================================Popularity and Rating=============================

    @Query("update Book b set b.popRating =:p where b.slug=:slug")
    void updatePopRating(@Param("p") Double popRating, @Param("slug") String slug);

    @Transactional
    @Modifying
    @Query(value = "UPDATE books SET cart_number = cart_number+1 WHERE slug=:slug", nativeQuery = true)
    void updateCartNumber(@Param("slug") String slug);

    @Modifying
    @Query( "UPDATE Book b SET b.postponedNumber =?1 where b.slug = ?2")
    void updatePostponedNumber(@Param("postponedNumber") Integer postponedNumber, @Param("slug") String slug);


    @Modifying
    @Query(value = "UPDATE Book b SET b.postponedNumber =?1 where b.slug = ?2")
    void updateBuyNumber(@Param("buyNumber") Integer buyNumber, @Param("slug") String slug);


    @Query(value = "SELECT b.buyNumber FROM Book b WHERE b.slug =:slug")
    Integer findBuyNumberBySlug(@Param("slug") String slug);



    @Query(value = "SELECT cart_number FROM books WHERE slug =:slug", nativeQuery = true)
    Integer findCartNumberBySlug(@Param("slug") String slug);

    @Query(value = "SELECT b.postponedNumber FROM Book b WHERE b.slug =:slug")
    Integer findPostponedNumberBySlug(@Param("slug") String slug);

    @Query(value = "SELECT * FROM books ORDER BY pop_rating", nativeQuery = true)
    Page<Book> findBooksByPopRating (Double popRating, Pageable pageable);

}




