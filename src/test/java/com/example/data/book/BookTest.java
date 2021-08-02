package com.example.data.book;

import com.example.data.Author;
import com.example.data.Book;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class BookTest {
    @Test
    public void testAuthorsFullName() {
        Author author = new Author();
        author.setLastName("Doe");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("The characteristics of someone or something");
        author.add(Link.of("Href"));
        author.setFirstName("Jane");
        Book book = new Book();
        book.setAuthor(author);
        assertEquals("Jane Doe", book.authorsFullName());
    }
}

