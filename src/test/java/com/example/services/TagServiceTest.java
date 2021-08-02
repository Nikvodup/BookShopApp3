package com.example.services;

import com.example.data.Book;
import com.example.data.Tag;
import com.example.data.TagRepository;
import com.example.data.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TagService.class})
@ExtendWith(SpringExtension.class)
public class TagServiceTest {
    @MockBean
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @Test
    public void testGetAll() {
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        when(this.tagRepository.findAll()).thenReturn(tagList);
        List<Tag> actualAll = this.tagService.getAll();
        assertSame(tagList, actualAll);
        assertTrue(actualAll.isEmpty());
        verify(this.tagRepository).findAll();
    }

    @Test
    public void testGetTag() {
        Tag tag = new Tag();
        tag.setBookList(new ArrayList<Book>());
        tag.setId(1);
        tag.setName("Популярное");
        when(this.tagRepository.getOne((Integer) any())).thenReturn(tag);
        assertSame(tag, this.tagService.getTag(123));
        verify(this.tagRepository).getOne((Integer) any());
    }
}

