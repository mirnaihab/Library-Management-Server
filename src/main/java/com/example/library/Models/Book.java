package com.example.library.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "username"})})

public class Book {

    public Book(Long id, String title, String author, Date publicationYear, String ISBN) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;


    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "This Field is Required")
    private String title;

    @NotBlank(message = "This Field is Required")
    private String author;

    @Column(unique = true)
    @NotBlank(message = "This Field is Required")
    private Date publicationYear;

    @Column(unique = true)
    @NotBlank(message = "This Field is Required")
    private String ISBN;


}
