package com.example.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PopularityAndRatingService {
     private BookRepository bookRepository;
     private String slug;

     @Autowired
     public PopularityAndRatingService(BookRepository bookRepository){
         this.bookRepository=bookRepository;
     }


     public void updateCartNumberAndPopRating(String slug){
         bookRepository.updateCartNumber(slug);
         Integer cartNumber = bookRepository.findCartNumberBySlug(slug);
       double popFactor = 0.7 * cartNumber;
         Double popRating = bookRepository.findPopRatingBySlug(slug);
       popRating += popFactor;
       bookRepository.updatePopRating(popRating,slug);
     }

}
