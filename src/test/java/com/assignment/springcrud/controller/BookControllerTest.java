package com.assignment.springcrud.controller;

import com.assignment.springcrud.CustomUserDetails;
import com.assignment.springcrud.entity.Book;
import com.assignment.springcrud.entity.User;
import com.assignment.springcrud.repository.UserRepository;
import com.assignment.springcrud.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class BookControllerTest {
    @MockBean private BookService service;
    @Autowired private UserRepository repository;
    @Autowired private MockMvc mockMvc;

    private UserDetails adminUserDetails = new CustomUserDetails(new User("baig", passwordEncoder().encode("1234"),"ADMIN"));
    private List<Book> mockBooks = Arrays.asList(new Book(1,"abc","a",1),new Book(2,"abc2","a2",2));
    private Book mockBook = new Book(1,"abc","a",1);
    private Book postBook = new Book("abc","a",1);


    @Test
    void testCanAddABook() throws Exception {
        doReturn(mockBook).when(service).saveBook(any());

        mockMvc.perform(post("/admin/addabook")
                        .with(user(adminUserDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postBook)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("abc"))
                .andExpect(jsonPath("$.category").value("a"))
                .andExpect(jsonPath("$.copies").value(1));
    }

    @Test
    void testCanAddBooks() throws Exception {
        List<Book> postBooks = Arrays.asList(new Book("abc","a",1),new Book("abc2","a2",2));
        doReturn(mockBooks).when(service).saveBooks(any());

        mockMvc.perform(post("/admin/addbooks")
                        .with(user(adminUserDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postBooks)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].bookId").value(1))
                .andExpect(jsonPath("$[0].title").value("abc"))
                .andExpect(jsonPath("$[0].category").value("a"))
                .andExpect(jsonPath("$[0].copies").value(1))
                .andExpect(jsonPath("$[1].bookId").value(2))
                .andExpect(jsonPath("$[1]title").value("abc2"))
                .andExpect(jsonPath("$[1].category").value("a2"))
                .andExpect(jsonPath("$[1].copies").value(2));
    }

    @Test
    void testCanGetBookById() throws Exception {
        doReturn(mockBook).when(service).findBookById(any());

        mockMvc.perform(get("/all/getabookbyid/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("abc"))
                .andExpect(jsonPath("$.category").value("a"))
                .andExpect(jsonPath("$.copies").value(1));
    }

    @Test
    void testCanGetBookByTitle() throws Exception {
        doReturn(mockBook).when(service).findBookByTitle(any());

        mockMvc.perform(get("/all/getabookbytitle/abc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("abc"))
                .andExpect(jsonPath("$.category").value("a"))
                .andExpect(jsonPath("$.copies").value(1));
    }

    @Test
    void testCanGetAllBooks() throws Exception {
        doReturn(mockBooks).when(service).findAllBooks();

        mockMvc.perform(get("/all/getallbooks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].bookId").value(1))
                .andExpect(jsonPath("$[0].title").value("abc"))
                .andExpect(jsonPath("$[0].category").value("a"))
                .andExpect(jsonPath("$[0].copies").value(1))
                .andExpect(jsonPath("$[1].bookId").value(2))
                .andExpect(jsonPath("$[1]title").value("abc2"))
                .andExpect(jsonPath("$[1].category").value("a2"))
                .andExpect(jsonPath("$[1].copies").value(2));
    }

    @Test
    void testCanRemoveAllBooks() throws Exception {
        doReturn("Deleted All books").when(service).deleteAllBooks();

        mockMvc.perform(delete("/admin/deleteallbooks")
                        .with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Deleted All books"));
    }

    @Test
    void testCanRemoveBookById() throws Exception {
        doReturn(mockBook).when(service).findBookById(any());
        doReturn("Deleted book with id: 1").when(service).deleteBookById(any());

        mockMvc.perform(delete("/admin/deletebookbyid/1")
                        .with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Deleted book with id: 1"));
    }

    @Test
    void testCanRemoveBookByTitle() throws Exception {
        doReturn(mockBook).when(service).findBookByTitle(any());
        doReturn("Deleted book with title: abc").when(service).deleteBookByTitle(any());

        mockMvc.perform(delete("/admin/deletebookbytitle/abc")
                        .with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Deleted book with title: abc"));
    }

    @Test
    void testCanUpdateBookById() throws Exception {
        doReturn(mockBook).when(service).update(any());

        mockMvc.perform(put("/admin/updatebookbyid")
                        .with(user(adminUserDetails))
                        .content(asJsonString(postBook))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("abc"))
                .andExpect(jsonPath("$.category").value("a"))
                .andExpect(jsonPath("$.copies").value(1));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}