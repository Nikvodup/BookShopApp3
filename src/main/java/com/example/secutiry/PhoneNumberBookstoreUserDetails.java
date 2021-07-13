package com.example.secutiry;

import com.example.data.BookstoreUser;

public class PhoneNumberBookstoreUserDetails extends BookstoreUserDetails {

    public PhoneNumberBookstoreUserDetails(BookstoreUser bookstoreUser) {
        super(bookstoreUser);
    }

    @Override
    public String getEmail() {
        return getBookstoreUser().getPhone();
    }
}
