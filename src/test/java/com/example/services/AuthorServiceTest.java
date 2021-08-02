package com.example.services;

import com.example.data.Author;
import com.example.data.AuthorRepository;
import com.example.data.AuthorService;
import com.example.data.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AuthorService.class})
@ExtendWith(SpringExtension.class)
public class AuthorServiceTest {
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    public void testGetAuthorsInAlphabetOrder() {
        when(this.authorRepository.findAll()).thenReturn(new ArrayList<Author>());
        assertTrue(this.authorService.getAuthorsInAlphabetOrder().isEmpty());
        verify(this.authorRepository).findAll();
    }

    @Test
    public void testGetAuthorsInAlphabetOrder2() {
        Author author = new Author();
        author.setLastName("Doe");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("The characteristics of someone or something");
        author.add(Link.of("Href"));
        author.setFirstName("Jane");

        ArrayList<Author> authorList = new ArrayList<Author>();
        authorList.add(author);
        when(this.authorRepository.findAll()).thenReturn(authorList);
        assertEquals(1, this.authorService.getAuthorsInAlphabetOrder().size());
        verify(this.authorRepository).findAll();
    }

    @Test
    public void testGetAuthorsById() {
        Author author = new Author();
        author.setLastName("JOe");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("Самый популярный в мире человек");
        author.add(Link.of("Href"));
        author.setFirstName("Smak");
        Optional<Author> ofResult = Optional.<Author>of(author);
        when(this.authorRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(author, this.authorService.getAuthorsById(1));
        verify(this.authorRepository).findById((Integer) any());
    }
}

