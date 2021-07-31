package com.example.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class AuthorRepositoryTests {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryTests(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    void findBooksByAuthorFirstNameContaining() {
        Author author = authorRepository.findByFirstName("Zak");
        String authorName =  author.getFirstName();
        assertNotNull(author);
        assertEquals(authorName, "Zak");
    }

}