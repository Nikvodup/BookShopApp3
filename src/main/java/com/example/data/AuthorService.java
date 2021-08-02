package com.example.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;




    @Autowired
    public AuthorService(AuthorRepository authorRepository, JdbcTemplate jdbcTemplate) {
        this.authorRepository = authorRepository;

    }

    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream().collect(Collectors.groupingBy((Author a)
                -> a.getLastName().substring(0,1)));
    }

    public Map<String, List<Author>> getAuthorsInAlphabetOrder() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().collect(Collectors.groupingBy((Author a) -> a.getLastName().substring(0,1)));
    }


   public Author getAuthorsById(Integer authorId){
        return authorRepository.findAuthorById(authorId);
    }

}
