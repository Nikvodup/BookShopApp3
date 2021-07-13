package com.example.data.rating;

import com.example.data.Book;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "rating_book")
public class RatingBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_rating_book_book"))
    private Book book;
    @Column(name = "one_star")
    private Integer oneStar;
    @Column(name = "two_star")
    private Integer twoStart;
    @Column(name = "three_star")
    private Integer threeStar;
    @Column(name = "four_star")
    private Integer fourStart;
    @Column(name = "five_star")
    private Integer fiveStar;

    public RatingBook() {
    }

    public RatingBook(Integer id, Book book,
                      Integer oneStar, Integer twoStart, Integer threeStar,
                      Integer fourStart, Integer fiveStar) {
        this.id = id;
        this.book = book;
        this.oneStar = oneStar;
        this.twoStart = twoStart;
        this.threeStar = threeStar;
        this.fourStart = fourStart;
        this.fiveStar = fiveStar;
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

    public Integer getOneStar() {
        return oneStar;
    }

    public void setOneStar(Integer oneStar) {
        this.oneStar = oneStar;
    }

    public Integer getTwoStart() {
        return twoStart;
    }

    public void setTwoStart(Integer twoStart) {
        this.twoStart = twoStart;
    }

    public Integer getThreeStar() {
        return threeStar;
    }

    public void setThreeStar(Integer threeStar) {
        this.threeStar = threeStar;
    }

    public Integer getFourStart() {
        return fourStart;
    }

    public void setFourStart(Integer fourStart) {
        this.fourStart = fourStart;
    }

    public Integer getFiveStar() {
        return fiveStar;
    }

    public void setFiveStar(Integer fiveStar) {
        this.fiveStar = fiveStar;
    }
}