package com.example.library.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "username"})})

public class BorrowingRecord {

    public BorrowingRecord(Long id, Book book, Patron patron, Date borrowDate, Date returnDate) {
        this.id = id;
        this.book = book;
        this.patron = patron;
        this.borrowDate=borrowDate;
        this.returnDate=returnDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    @Column(name = "borrow_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date borrowDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    private Date returnDate;


}
