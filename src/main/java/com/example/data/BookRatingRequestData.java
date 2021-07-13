package com.example.data;

//import lombok.Data;

//@Data
public class BookRatingRequestData {

    private String bookId;
    private Integer value;

    public BookRatingRequestData(String bookId, Integer value) {
        this.bookId = bookId;
        this.value = value;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}