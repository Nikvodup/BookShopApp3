package com.example.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewLikeRepo extends JpaRepository<BookReviewLike, Integer> {

    BookReviewLike findByUserAndAndBookReview(BookstoreUser user, BookReview bookReview);

}