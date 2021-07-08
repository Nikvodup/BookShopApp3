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

    @Query(value = "SELECT COUNT (*) FROM books",nativeQuery = true)
   Integer countAll();

    @Query("select b.cartNumber from Book b where b.id=:id")
    Integer findCartNumberById(@Param("id") Integer id);

    @Query("select b.postponedNumber from Book b where b.id=:id")
    Integer findPostponedNumberById(@Param("id") Integer id);

    @Query("select b.buyNumber from Book b where b.id=:id")
    Integer findBuyNumberById(@Param("id") Integer id);


    @Query(value = "UPDATE books SET pop_rating=:popRating WHERE id=:id",nativeQuery = true)
    void updatePopRatingById(@Param("popRating") Integer popRating, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE books SET cart_number=cart_number+1 WHERE slug=:slug",nativeQuery = true)
    void updateCartNumber(@Param("slug") String slug);

    @Modifying
    @Query( value = "UPDATE books SET postponed_number =postponed_number+1 where slug =:slug",nativeQuery = true)
    void updatePostponedNumber(@Param("slug") String slug);


    @Modifying
    @Query(value = "UPDATE Book b SET b.postponedNumber =?1 where b.slug = ?2")
    void updateBuyNumber(@Param("buyNumber") Integer buyNumber, @Param("slug") String slug);


    @Query(value = "SELECT b.buyNumber FROM Book b WHERE b.slug =:slug")
    Integer findBuyNumberBySlug(@Param("slug") String slug);



    @Query( "select b.cartNumber from Book b WHERE b.slug =:slug")
    Integer findCartNumberBySlug(@Param("slug") String slug);



    @Query(value = "SELECT b.postponedNumber FROM Book b WHERE b.slug =:slug")
    Integer findPostponedNumberBySlug(@Param("slug") String slug);


    @Query("select b.popRating from Book b where b.slug=:slug")
    Double findPopRatingBySlug(@Param("slug") String slug);

     @Query(value = "select * from books order by pop_rating desc",nativeQuery = true)
    Page<Book> findBooksByPopRating (Pageable pageable);

}




