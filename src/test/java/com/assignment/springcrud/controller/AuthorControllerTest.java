package com.assignment.springcrud.controller;

import com.assignment.springcrud.CustomUserDetails;
import com.assignment.springcrud.entity.Author;
import com.assignment.springcrud.entity.User;
import com.assignment.springcrud.repository.UserRepository;
import com.assignment.springcrud.service.AuthorService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AuthorControllerTest {

    @MockBean private AuthorService service;
    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository repository;

    private String adminUsername = "baig";
    private String adminPassword = passwordEncoder().encode("1234");
    private User adminUser = new User(adminUsername, adminPassword,"ADMIN");
    private UserDetails adminUserDetails = new CustomUserDetails(adminUser);

    @Test
    void testCanAddAuthor() throws Exception {
        Author mockAuthor = new Author(1,"Author_2");
        Author postAuthor = new Author("Author_2");

        doReturn(mockAuthor).when(service).saveAuthor(any());

        mockMvc.perform(post("/admin/addauthor")
                        .with(user(adminUserDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postAuthor)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.authorName").value("Author_2"))
                .andExpect(jsonPath("$.authorId").value(1));
    }

    @Test
    void testCanAddAuthors() throws Exception {
        List<Author> mockAuthors = Arrays.asList(new Author(2,"Author_2"),new Author(3,"Author_3"));
        List<Author> postAuthors = Arrays.asList(new Author("Author_2"),new Author("Author_3"));

        doReturn(mockAuthors).when(service).saveAuthors(anyList());

        mockMvc.perform(post("/admin/addauthors")
                .with(user(adminUserDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postAuthors)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].authorId").value(2))
                .andExpect(jsonPath("$[0].authorName").value("Author_2"))
                .andExpect(jsonPath("$[1].authorId").value(3))
                .andExpect(jsonPath("$[1].authorName").value("Author_3"));
    }

    @Test
    void testCanGetAuthorById() throws Exception {

        Author mockAuthor = new Author(1,"Author_2");
        Author postAuthor = new Author("Author_2");

        doReturn(mockAuthor).when(service).findAuthorById(any());

        mockMvc.perform(get("/all/getauthorbyid/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.authorName").value("Author_2"))
                .andExpect(jsonPath("$.authorId").value(1));
    }

    @Test
    void testCanGetAuthorsByAuthorName() throws Exception {
        List<Author> mockAuthors = Arrays.asList(new Author(2,"Author_2"),new Author(3,"Author_2"));
        doReturn(mockAuthors).when(service).findAuthorByName(any());

        mockMvc.perform(get("/all/getauthorsbyauthorname/Author_2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].authorId").value(2))
                .andExpect(jsonPath("$[0].authorName").value("Author_2"))
                .andExpect(jsonPath("$[1].authorId").value(3))
                .andExpect(jsonPath("$[1].authorName").value("Author_2"));
    }

    @Test
    void testCanGetAllAuthors() throws Exception {
        List<Author> mockAuthors = Arrays.asList(new Author(2,"Author_2"),new Author(3,"Author_3"));
        doReturn(mockAuthors).when(service).findAllAuthor();

        mockMvc.perform(get("/all/getallauthors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].authorId").value(2))
                .andExpect(jsonPath("$[0].authorName").value("Author_2"))
                .andExpect(jsonPath("$[1].authorId").value(3))
                .andExpect(jsonPath("$[1].authorName").value("Author_3"));
    }

    @Test
    void testCanRemoveAllAuthors() throws Exception {
        List<Author> mockAuthors = Arrays.asList(new Author(2,"Author_2"),new Author(3,"Author_3"));
        doReturn(mockAuthors).when(service).findAllAuthor();
        doReturn("Deleted all authors").when(service).deleteAllAuthors();

        mockMvc.perform(delete("/admin/deleteallauthors")
                        .with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(result -> {
                    String s = "Deleted all authors";
                });
    }
    @Test
    void testShowErrorWhenNoAuthorPresentAndRemoveAllAuthorsUsed() throws Exception {
        doReturn("Deleted all authors").when(service).deleteAllAuthors();

        mockMvc.perform(delete("/admin/deleteallauthors")
                        .with(user(adminUserDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(result -> {
                    String s = "Error: Author list is empty.";
                });
    }

    @Test
    void testCanRemoveAuthorById() throws Exception {
        Author mockAuthor = new Author(1,"Author_2");
        doReturn(mockAuthor).when(service).findAuthorById(any());
        doReturn("Deleted member by id: 1").when(service).deleteAuthorById(any());

        mockMvc.perform(delete("/admin/deleteauthorbyid/1")
                        .with(user(adminUserDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(result -> {
                    String s = "Deleted member by id: 1";
                });
    }
    @Test
    void testShowErrorWhenNoAuthorPresentAndRemoveAuthorByIdPerformed() throws Exception {
        doReturn("Deleted member by id: 1").when(service).deleteAuthorById(any());

        mockMvc.perform(delete("/admin/deleteauthorbyid/1")
                        .with(user(adminUserDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(result -> {
                    String s = "Error: Author with authorId: 1 does not exist.";
                });
    }

    @Test
    void updateAuthorById() throws Exception {
        Author mockAuthor = new Author(1,"Author_2");
        Author postAuthor = new Author("Author_2");
        doReturn(mockAuthor).when(service).update(any());

        mockMvc.perform(put("/admin/updateauthorbyid")
                        .with(user(adminUserDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postAuthor)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.authorName").value("Author_2"))
                .andExpect(jsonPath("$.authorId").value(1));
    }

    @Test
    void testShowErrorForWrongAuthentication() throws Exception {
        List<Author> mockAuthors = Arrays.asList(new Author(2,"Author_2"),new Author(3,"Author_3"));
        doReturn(mockAuthors).when(service).findAllAuthor();
        doReturn("Deleted all authors").when(service).deleteAllAuthors();

        mockMvc.perform(delete("/admin/deleteallauthors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
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