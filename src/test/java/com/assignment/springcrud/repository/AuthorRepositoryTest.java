package com.assignment.springcrud.repository;

import com.assignment.springcrud.entity.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    private Author author = new Author(1, "Author");
    private Author author1 = new Author(2, "Author");

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void findAllByAuthorName() {
        repository.saveAll(Arrays.asList(author, author1));
        List<Author> returnedAuthors = repository.findAllByAuthorName("Author");
        assertEquals(2, returnedAuthors.size());
    }
}