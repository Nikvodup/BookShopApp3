package com.example.data;


import com.example.data.rating.RatingBook;
import com.example.data.rating.RatingCountI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<RatingBook, Integer> {


     @Query(value = "SELECT * FROM rating_book WHERE book_id=?1",nativeQuery = true)
    RatingBook findBookById( Integer id);

    @Query(value = "SELECT total, ((five_star*5  + four_star*4 + three_star*3  + two_star*2 + one_star) / total) AS average " +
            "FROM rating_book  " +
            ", LATERAL(SELECT five_star + four_star + one_star + three_star + two_star) AS s1(total) WHERE book_id = :bookId limit 1 ;", nativeQuery = true)
    RatingCountI getTotalAndAvgStars(@Param("bookId") Integer bookId);

}
