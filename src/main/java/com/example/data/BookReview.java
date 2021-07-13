package com.example.data;



import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity


@Table(name = "book_review")
public class BookReview implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_review_book"))
    private Book book;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    private Date time;
    @Column(name = "text", length = 1000)
    private String text;
    @Column(name = "rating", columnDefinition = "int default 4")
    private Integer rating;
    @OneToMany(mappedBy = "bookReview", cascade = CascadeType.ALL)
    private List<BookReviewLike> bookReviewLikes = new ArrayList<>();

    public long getLikeCount() {
        return bookReviewLikes.stream().filter(like -> like.getValue()==1).count();
    }

    public long getDisLikeCount() {
        return bookReviewLikes.stream().filter(like -> like.getValue()==-1).count();
    }


    public BookReview() {
    }

    public BookReview(Integer id, Book book, Integer userId, String userName, Date time, String text, Integer rating, List<BookReviewLike> bookReviewLikes) {
        this.id = id;
        this.book = book;
        this.userId = userId;
        this.userName = userName;
        this.time = time;
        this.text = text;
        this.rating = rating;
        this.bookReviewLikes = bookReviewLikes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<BookReviewLike> getBookReviewLikes() {
        return bookReviewLikes;
    }

    public void setBookReviewLikes(List<BookReviewLike> bookReviewLikes) {
        this.bookReviewLikes = bookReviewLikes;
    }
}