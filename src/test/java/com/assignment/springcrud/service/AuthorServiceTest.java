package com.assignment.springcrud.service;

import com.assignment.springcrud.entity.Author;
import com.assignment.springcrud.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith({SpringExtension.class,})

@SpringBootTest
class AuthorServiceTest {

    @Autowired
    AuthorService service;
    @MockBean
    AuthorRepository repository;

    private Author mockAuthor = new Author(1,"Author");
    private List<Author> mockAuthors = Arrays.asList(mockAuthor,new Author(2,"Author2"));
    @Test
    void saveAuthor() {
        doReturn(mockAuthor).when(repository).save(any());

        Author returnedProduct = service.saveAuthor(mockAuthor);

        Assertions.assertNotNull(returnedProduct,"The saved product should not be null");
        Assertions.assertEquals(1,returnedProduct.getAuthorId().intValue(),"The id for the new product should be 1");
        Assertions.assertEquals("Author",returnedProduct.getAuthorName(),"The name for the new product should be Author");

    }

    @Test
    void saveAuthors() {
        doReturn(mockAuthors).when(repository).saveAll(any());
        List<Author> returnedProducts = service.saveAuthors(mockAuthors);
        Assertions.assertNotNull(returnedProducts,"The saved product should not be null");
        Assertions.assertEquals(2,mockAuthors.size(),"Error");

    }

    @Test
    @Transactional
    void deleteAuthorById() {
        doNothing().when(repository).deleteById(any());
        String returnedString = service.deleteAuthorById(1);
        Assertions.assertEquals("Deleted member by id: 1",returnedString,"not deleted");
    }

    @Test
    @Transactional
    void deleteAllAuthorsByName() {
        doReturn(1l).when(repository).deleteAllByAuthorName(any());
        String returnedString = service.deleteAllAuthorsByName("Author");
        Assertions.assertTrue(returnedString.startsWith("Delete Author by noting the author id of"),"not deleted");
    }

    @Test
    void deleteAllAuthors() {
        doNothing().when(repository).deleteAll();
        String returnedString = service.deleteAllAuthors();
        Assertions.assertEquals("Deleted all authors",returnedString,"Not deleted");
    }

    @Test
    void update() {
        doReturn(Optional.of(mockAuthor)).when(repository).findById(1);
        Author returnedProduct = service.update(mockAuthor);
        Assertions.assertNotNull(returnedProduct,"The saved product should not be null");
        Assertions.assertEquals(1,returnedProduct.getAuthorId().intValue(),"The id for the new product should be 1");
        Assertions.assertEquals("Author",returnedProduct.getAuthorName(),"The id for the new product should be 1");
    }

    @Test
    void findAuthorById() {
        doReturn(Optional.of(mockAuthor)).when(repository).findById(1);
        Author returnedProduct = service.findAuthorById(1);
        Assertions.assertNotNull(returnedProduct,"The saved product should not be null");
        Assertions.assertEquals(1,returnedProduct.getAuthorId().intValue(),"The id for the new product should be 1");
        Assertions.assertEquals("Author",returnedProduct.getAuthorName(),"The name for the new product should be Author");
    }

    @Test
    void findAuthorByName() {
        List<Author> mockAuthors1 = Arrays.asList(mockAuthor,new Author(2,"Author"));
        doReturn(mockAuthors1).when(repository).findAllByAuthorName("Author");
        List<Author> returnedProducts = service.findAuthorByName("Author");
        Assertions.assertNotNull(returnedProducts,"The saved product should not be null");
        Assertions.assertEquals(2,returnedProducts.size(),"The no of authors found is not correct");
    }

    @Test
    void findAllAuthor() {
        doReturn(mockAuthors).when(repository).findAll();
        List<Author> returnedProducts = service.findAllAuthor();
        Assertions.assertNotNull(returnedProducts,"The saved product should not be null");
        Assertions.assertEquals(2,returnedProducts.size(),"The no of authors found is not correct");
    }
}