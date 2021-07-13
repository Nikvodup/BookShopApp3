package com.example.data;

import com.example.data.BookstoreUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookstoreUserRepository extends JpaRepository<BookstoreUser,Integer> {
   BookstoreUser findBookstoreUserByEmail(String email);
   BookstoreUser findBookstoreUserByPhone(String phone);

}
