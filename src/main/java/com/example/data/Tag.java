package com.example.data;

import com.example.data.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity

@Table(name = "Tag")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "book2tag",
            joinColumns = @JoinColumn(name = "tag_id"), foreignKey = @ForeignKey(name = "fk_tag_book"),
            inverseJoinColumns = @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "fk_book_tag"))
    )
    private  List<Book> bookList;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
