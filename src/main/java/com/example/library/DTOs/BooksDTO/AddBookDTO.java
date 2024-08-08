package com.example.library.DTOs.BooksDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class AddBookDTO {

    private String title;
    private String author;
    private Date publicationYear;
    private String ISBN;

}
