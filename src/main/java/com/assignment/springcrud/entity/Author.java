package com.assignment.springcrud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {
    @Id
    @SequenceGenerator(
            name = "author_sequence",
            sequenceName = "author_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "author_sequence"
    )
    private Integer authorId;
    @Column(nullable = false)
    private String authorName;

    public Author(String authorName) {
        this.authorName = authorName;
    }
//    @ManyToMany(
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER,
//            mappedBy = "authors")
//    private List<Book> books;
}
