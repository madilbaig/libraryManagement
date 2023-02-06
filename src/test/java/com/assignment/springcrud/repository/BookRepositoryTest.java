package com.assignment.springcrud.repository;

import com.assignment.springcrud.entity.Author;
import com.assignment.springcrud.entity.Book;
import com.assignment.springcrud.entity.Member;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    void findByTitle() {
        System.out.println(repository.findByTitle("Book_1"));
    }

    @Test
    void deleteByTitle() {
        System.out.println(repository.deleteByTitle("Book_1"));
    }
//    @Test
//    void canSave(){
//        Member member =Member.builder()
//                .name("adil")
//                .phone(9090909090l)
//                .email("adil@gmail.com")
//                .build();
//        Member member1 =Member.builder()
//                .name("baig")
//                .phone(9090909091l)
//                .email("baig@gmail.com")
//                .build();
//        Author author = Author.builder()
//                .authorName("Author_100")
//                .build();
//        Author author1 = Author.builder()
//                .authorName("Author_101")
//                .build();
//        Book book = Book.builder()
//                .title("Book_1")
//                .category("Category_1")
//                .copies(2)
//                .authors(Arrays.asList(author,author1))
//                .member(Arrays.asList(member,member1))
//                .build();
//        System.out.println(repository.save(book));
//    }
}