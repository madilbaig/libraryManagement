package com.assignment.springcrud.service;

import com.assignment.springcrud.entity.Author;
import com.assignment.springcrud.entity.Book;
import com.assignment.springcrud.repository.AuthorRepository;
import com.assignment.springcrud.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BookServiceTest {

    @Autowired
    BookService service;
    @MockBean
    BookRepository repository;

    private Book mockBook = new Book(1,"Book","cs",1);
    private List<Book> mockBooks = Arrays.asList(mockBook,new Book(2,"Book2","cs",2));

    @Test
    void saveBook() {
        doReturn(mockBook).when(repository).save(any());

        Book returnedProduct = service.saveBook(mockBook);

        Assertions.assertNotNull(returnedProduct,"The saved product should not be null");
        Assertions.assertEquals(1,returnedProduct.getBookId().intValue(),"The id for the new product should be 1");
        Assertions.assertEquals("Book",returnedProduct.getTitle(),"The name for the new product should be Book");
        Assertions.assertEquals("cs",returnedProduct.getCategory(),"The category for the new product should be Book");
        Assertions.assertEquals(1,returnedProduct.getCopies().intValue(),"The copies for the new product should be Book");

    }

    @Test
    void deleteBookById() {
        doNothing().when(repository).deleteById(any());
        String returnedString = service.deleteBookById(1);
        Assertions.assertEquals("Deleted book with id: 1",returnedString,"not deleted");
    }

    @Test
    void update() {
        doReturn(Optional.of(mockBook)).when(repository).findById(any());
        Book returnedProduct = service.update(mockBook);
        Assertions.assertNotNull(returnedProduct,"The saved product should not be null");
        Assertions.assertEquals(1,returnedProduct.getBookId().intValue(),"The id for the new product should be 1");
        Assertions.assertEquals("Book",returnedProduct.getTitle(),"The name for the new product should be Book");
        Assertions.assertEquals("cs",returnedProduct.getCategory(),"The category for the new product should be Book");
        Assertions.assertEquals(1,returnedProduct.getCopies().intValue(),"The copies for the new product should be Book");
    }

    @Test
    void findBookById() {
        doReturn(Optional.of(mockBook)).when(repository).findById(any());

        Book returnedProduct = service.findBookById(1);

        Assertions.assertNotNull(returnedProduct,"The saved product should not be null");
        Assertions.assertEquals(1,returnedProduct.getBookId().intValue(),"The id for the new product should be 1");
        Assertions.assertEquals("Book",returnedProduct.getTitle(),"The name for the new product should be Book");
        Assertions.assertEquals("cs",returnedProduct.getCategory(),"The category for the new product should be Book");
        Assertions.assertEquals(1,returnedProduct.getCopies().intValue(),"The copies for the new product should be Book");


    }
}