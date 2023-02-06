package com.assignment.springcrud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private Integer bookId;
    @Column(nullable = false)
    private String title;
    private String category;
    private Integer copies;


    @ManyToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "book_member_map",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "bookId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "member_id",
                    referencedColumnName = "memberId"
            )
    )
    private List<Member> member;

    @ManyToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "author_book_map",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "bookId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "author_id",
                    referencedColumnName = "authorId"
            )
    )
    private List<Author> authors;

    public Book(Integer bookId, String title, String category, Integer copies) {
        this.bookId = bookId;
        this.title = title;
        this.category = category;
        this.copies = copies;
    }

    public Book(String title, String category, Integer copies) {
        this.title = title;
        this.category = category;
        this.copies = copies;
    }
    //
//    @ManyToOne(
//            cascade = CascadeType.ALL
////            fetch = FetchType.EAGER
//    )
//    @JoinColumn(
//            name = "author_id",
//            referencedColumnName = "authorId",
//            nullable = false
//    )
//    private Author author;
}
