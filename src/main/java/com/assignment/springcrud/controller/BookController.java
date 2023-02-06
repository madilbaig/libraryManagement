package com.assignment.springcrud.controller;

import com.assignment.springcrud.entity.Book;
import com.assignment.springcrud.entity.Member;
import com.assignment.springcrud.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class BookController {
    @Autowired
    private BookService service;

    @PostMapping("/admin/addabook")
    public Book addABook(@RequestBody Book book){ return service.saveBook(book); }

    @PostMapping("/admin/addbooks")
    public List<Book> addBooks(@RequestBody List<Book> books){ return service.saveBooks(books); }

    @PostMapping("/admin/addbooksfromcsv")
    public String addBooksFromCsv() throws IOException { return service.saveBooksFromCsv(); }

    @PostMapping("/admin/addbooksfromcsvmultithread")
    public String addBooksFromCsvMultiThread() throws IOException, ExecutionException, InterruptedException { return service.saveBooksFromCsvMultiThread(); }

    @GetMapping("/all/getabookbyid/{bookId}")
    public Object getBookById(@PathVariable Integer bookId){
        if(service.findBookById(bookId)==null)
            return "Error: Book not found with bookId: " + bookId;
        return service.findBookById(bookId);
    }

    @GetMapping("/all/getabookbytitle/{title}")
    public Object getBookByTitle(@PathVariable String title){
        if(service.findBookByTitle(title)==null)
            return "Error: Book not found with title: " + title;
        return service.findBookByTitle(title);
    }

    @GetMapping("/all/getallbooks")
    public Object getAllBooks(){
        List<Book> books =service.findAllBooks();
        if(books == null)
            return "Error: No books registered.";
        else
            return service.findAllBooks();
    }

    @DeleteMapping("/admin/deleteallbooks")
    public String removeAllBooks(){
        return service.deleteAllBooks();
    }

    @DeleteMapping("/admin/deletebookbyid/{bookId}")
    public String removeBookById(@PathVariable Integer bookId){
        Book book =service.findBookById(bookId);
        if(book == null)
            return "Error: Book with bookId: " + bookId + " does not exist.";
        else
            return service.deleteBookById(bookId);
    }

    @Transactional
    @DeleteMapping("/admin/deletebookbytitle/{title}")
    public String removeBookByTitle(@PathVariable String title){
        Book book =service.findBookByTitle(title);
        if(book == null)
            return "Error: Book with title: " + title + " does not exist.";
        else
            return service.deleteBookByTitle(title);
    }

    @PutMapping("/admin/updatebookbyid")
    public Book updateBookById(@RequestBody Book book){
        return service.update(book);
    }
}
