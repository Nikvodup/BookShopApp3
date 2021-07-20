package com.example.data;

import com.example.security.BookstoreUser;
import com.example.data.RecentlyViewedBook;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecentlyViewedBooksRepository extends JpaRepository<RecentlyViewedBook, Integer> {

    RecentlyViewedBook findByUserAndBook(BookstoreUser bookstoreUser, Book book);

}