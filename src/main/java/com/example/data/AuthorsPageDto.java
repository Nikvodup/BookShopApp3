package com.example.data;

import java.util.List;

public class AuthorsPageDto {

    private Integer count;
    private List<Author> authors;

    public AuthorsPageDto(List<Author> authors) {
        this.authors = authors;
        this.count = authors.size();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
