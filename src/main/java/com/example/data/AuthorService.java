package com.example.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
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
                -> {return a.getLastName().substring(0,1);}));
    }


   public List<Author> getAuthorsById(Integer authorId){
        return authorRepository.getAuthorsById(authorId);
    }

}
