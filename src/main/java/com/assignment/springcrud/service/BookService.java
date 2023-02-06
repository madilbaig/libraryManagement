package com.assignment.springcrud.service;

import com.assignment.springcrud.entity.Author;
import com.assignment.springcrud.entity.Book;
import com.assignment.springcrud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

//    public Book saveBook(){
//        Book book = Book.builder()
//                .title("first_book")
//                .category("Science")
//                .build();
//        return  repository.save(book);
//    }

    public Book saveBook(Book book){
        return  repository.save(book);
    }

    public String saveBooksFromCsv() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get("test.csv"));
        String line = "";
        while ((line =reader.readLine())!=null) {
            String[] details = line.split(",");
            ArrayList<Author> authors = new ArrayList<>();
            for(String x:Arrays.copyOfRange(details,3,details.length))
                authors.add(Author.builder().authorName(x).build());
            repository.save(Book.builder().category(details[1]).title(details[0]).copies(Integer.parseInt(details[2])).authors(authors).build());
            //repository.save(Book.builder().category(future.get()[1]).title(future.get()[0]).copies(Integer.parseInt(future.get()[2])).build());
        }
        return "Books saved";
    }

    public String saveBooksFromCsvMultiThread() throws ExecutionException, InterruptedException, IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        FileRead fileRead;
        Future<String> future;
        for (int i=0;i<14999;i++){
            fileRead = new FileRead(i);
            future = executorService.submit(fileRead);
            String[] details = future.get().split(",");
            repository.save(Book.builder().category(details[1]).title(details[0]).copies(Integer.parseInt(details[2])).build());
        }
        executorService.shutdown();
        return "Books from csv using multi threading saved";
    }

    public String deleteBookById(Integer bookId){
        repository.deleteById(bookId);
        return "Deleted book with id: " + bookId;
    }

    public String deleteBookByTitle(String title){
        repository.deleteByTitle(title);
        return "Deleted book with title: " + title;
    }
    public String deleteAllBooks(){
        repository.deleteAll();
        return "Deleted All books";
    }
    public Book update(Book book){
        Book existingBook = repository.findById(book.getBookId()).orElse(book);
        existingBook.setCategory(book.getCategory());
        existingBook.setTitle(book.getTitle());
        repository.save(existingBook);
        return existingBook;
    }

    public List<Book> saveBooks(List<Book> books){
        return repository.saveAll(books);
    }

    public Book findBookById(Integer bookId){
        return repository.findById(bookId).orElse(null);
    }

    public Book findBookByTitle(String title) { return repository.findByTitle(title); }

    public List<Book> findAllBooks(){
        return repository.findAll();
    }

}


