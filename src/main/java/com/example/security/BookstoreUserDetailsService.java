package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

    private final BookstoreUserRepository bookstoreUserRepository;

    @Autowired
    public BookstoreUserDetailsService(BookstoreUserRepository bookstoreUserRepository) {
        this.bookstoreUserRepository = bookstoreUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

    BookstoreUser phoneUser = bookstoreUserRepository.findBookstoreUserByPhone(s);

        if (phoneUser != null) {
            return new PhoneNumberBookstoreUserDetails(phoneUser);
        }


        BookstoreUser emailUser = bookstoreUserRepository.findBookstoreUserByEmail(s);
        if (emailUser != null){
            return new BookstoreUserDetails(emailUser);
        }

        else {
            throw new UsernameNotFoundException("user not found doh!");

        }
    }
}
