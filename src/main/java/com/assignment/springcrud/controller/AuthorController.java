package com.assignment.springcrud.controller;

import com.assignment.springcrud.entity.Author;
import com.assignment.springcrud.entity.Book;
import com.assignment.springcrud.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping
public class AuthorController {
    @Autowired
    private AuthorService service;

    @PostMapping("/admin/addauthor")
    public Author addAuthor(@RequestBody Author author){ return service.saveAuthor(author); }

    @PostMapping("/admin/addauthors")
    public List<Author> addAuthors(@RequestBody List<Author> authors){
        return service.saveAuthors(authors);
    }

    @PostMapping("/admin/addauthorsfromcsvmultithread")
    public String addAuthorsFromCsvMultiThread() throws IOException, ExecutionException, InterruptedException { return service.saveAuthorsFromCsvMultiThread(); }


    @GetMapping("/all/getauthorbyid/{authorId}")
    public Object getAuthorById(@PathVariable Integer authorId){
        Author author =service.findAuthorById(authorId);
        if(author == null)
            return "Error: Author with authorId: " + authorId + " does not exist.";
        else
            return service.findAuthorById(authorId);
    }

    @GetMapping("/all/getauthorsbyauthorname/{authorName}")
    public Object getAuthorsByAuthorName(@PathVariable String authorName){
        List<Author> authors =service.findAuthorByName(authorName);
        if(authors == null)
            return "Error: Author with authorName: " + authorName + " does not exist.";
        else
            return service.findAuthorByName(authorName);
    }

    @GetMapping("/all/getallauthors")
    public Object getAllAuthors(){
        List<Author> authors =service.findAllAuthor();
        if(authors == null)
            return "Error: Author list is empty.";
        else
            return service.findAllAuthor();
    }

    @DeleteMapping("/admin/deleteallauthors")
    public String removeAllAuthors(){
        List<Author> authors =service.findAllAuthor();
        if(authors == null)
            return "Error: Author list is empty.";
        else
            return service.deleteAllAuthors();
    }

    @DeleteMapping("/admin/deleteauthorbyid/{authorId}")
    public String removeAuthorById(@PathVariable Integer authorId){
        Author author =service.findAuthorById(authorId);
        if(author == null)
            return "Error: Author with authorId: " + authorId + " does not exist.";
        else
            return service.deleteAuthorById(authorId);
    }

    @DeleteMapping("/admin/deleteauthorsbyname/{authorName}")
    public Object removeAuthorByAuthorName(@PathVariable String authorName){
        List<Author> authors =service.findAuthorByName(authorName);
        if(authors == null)
            return "Error: Author with authorName: " + authorName + " does not exist.";
        else
            return service.deleteAllAuthorsByName(authorName);
    }

    @PutMapping("/admin/updateauthorbyid")
    public Author updateAuthorById(@RequestBody Author author){
        return service.update(author);
    }
}
