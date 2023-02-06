package com.assignment.springcrud.repository;

import com.assignment.springcrud.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {
    List<Author> findAllByAuthorName(String authorName);
    Long deleteAllByAuthorName(String authorName);
}
