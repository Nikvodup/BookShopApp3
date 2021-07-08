package com.example.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class PopularityAndRatingService {
    private BookRepository bookRepository;


    @Autowired
    public PopularityAndRatingService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    Lock lock = new ReentrantLock();

    public void updateCartNumber(String slug) {
        if (lock.tryLock()) {
            try {
                bookRepository.updateCartNumber(slug);
            } finally {
                lock.unlock();
            }
        }
    }


    public void updatePostponedNumber(String slug){
        if(lock.tryLock()){
            try {
                bookRepository.updatePostponedNumber(slug);
            } finally {
                lock.unlock();
            }
        }
    }


    public void updatePopRating() {

        if (lock.tryLock()) {

            try {

                Integer total = bookRepository.countAll();

                for (int i = 1; i <= total; i++) {
                    Integer cartNumber = bookRepository.findCartNumberById(i);
                    Integer postponedNumber = bookRepository.findPostponedNumberById(i);
                    Integer buyNumber = bookRepository.findBuyNumberById(i);
                    Integer popRating = Math.toIntExact(buyNumber + Math.round(0.7 * cartNumber) + Math.round(0.4 * postponedNumber));
                    bookRepository.updatePopRatingById(popRating,i);
                }
            } finally {
                lock.unlock();
            }
        }
    }

}

