package com.example.security;


import org.springframework.security.core.userdetails.UserDetails;

public class PhoneNumberBookstoreUserDetails extends BookstoreUserDetails {

    public PhoneNumberBookstoreUserDetails(BookstoreUser bookstoreUser) {
        super(bookstoreUser);
    }

    @Override
    public String getEmail() {
        return getBookstoreUser().getPhone();
    }
}
