package com.example.data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity


@Table(name = "book_review_like")
public class BookReviewLike implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private BookReview bookReview;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private BookstoreUser user;
    private Date time;
    private Short value;

    public BookReviewLike(){

    }

    public BookReviewLike(Integer id, BookReview bookReview, BookstoreUser user, Date time, Short value) {
        this.id = id;
        this.bookReview = bookReview;
        this.user = user;
        this.time = time;
        this.value = value;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookReview getBookReview() {
        return bookReview;
    }

    public void setBookReview(BookReview bookReview) {
        this.bookReview = bookReview;
    }

    public BookstoreUser getUser() {
        return user;
    }

    public void setUser(BookstoreUser user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }
}
