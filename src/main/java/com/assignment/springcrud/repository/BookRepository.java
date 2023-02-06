package com.assignment.springcrud.repository;

import com.assignment.springcrud.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findByTitle(String title);

//    @Query(name = "delete from book b where b.title = :title",nativeQuery = true)
    @Transactional Long deleteByTitle(String title);
}
