package com.example.data;

import com.example.data.BookReview;
import com.example.data.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookReviewService {

  private final BookReviewRepository bookReviewRepository;

  @Autowired
  public BookReviewService(BookReviewRepository bookReviewRepository) {
    this.bookReviewRepository = bookReviewRepository;
  }

  public void saveReview(BookReview review) {
    bookReviewRepository.save(review);
  }

}

