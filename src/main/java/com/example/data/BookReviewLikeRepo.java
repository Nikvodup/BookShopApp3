package com.example.data;

import com.example.security.BookstoreUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewLikeRepo extends JpaRepository<BookReviewLike, Integer> {

    BookReviewLike findByUserAndAndBookReview(BookstoreUser user, BookReview bookReview);

}