package com.assignment.springcrud.service;

import com.assignment.springcrud.entity.Author;
import com.assignment.springcrud.entity.Book;
import com.assignment.springcrud.entity.Member;
import com.assignment.springcrud.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository repository;

//    public Author saveAuthor(){
//        Author author = Author.builder()
//                .authorName("Ammar").build();
//        return repository.save(author);
//    }

    public Author saveAuthor(Author author){
        return repository.save(author);
    }

    public List<Author> saveAuthors(List<Author> authors){
        return repository.saveAll(authors);
    }

    public String saveAuthorsFromCsvMultiThread() throws ExecutionException, InterruptedException, IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        FileRead fileRead;
        Future<String> future;
        for (int i=0;i<14999;i++){
            fileRead = new FileRead(i);
            future = executorService.submit(fileRead);
            repository.save(Author.builder().authorName(future.get()).build());
        }
        executorService.shutdown();
        return "Authors from csv using multi threading saved";
    }

    public String deleteAuthorById(Integer authorId){
        repository.deleteById(authorId);
        return "Deleted member by id: " + authorId;
    }

    public String deleteAllAuthorsByName(String authorName){
        System.out.println(repository.findAllByAuthorName(authorName));
        return  "Delete Author by noting the author id of " + authorName +"\n" + repository.findAllByAuthorName(authorName);
    }

    public String deleteAllAuthors(){
        repository.deleteAll();
        return "Deleted all authors";
    }

    public Author update(Author author){
        Author existingAuthor = repository.findById(author.getAuthorId()).orElse(author);
        existingAuthor.setAuthorName(author.getAuthorName());
        repository.save(existingAuthor);
        return existingAuthor;
    }

    public Author findAuthorById(Integer authorId){
        return repository.findById(authorId).orElse(null);
    }

    public List<Author> findAuthorByName(String authorName){
        return repository.findAllByAuthorName(authorName);
    }

    public List<Author> findAllAuthor(){
        return repository.findAll();
    }

}
