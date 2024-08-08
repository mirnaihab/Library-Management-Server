package com.example.library.Mapper;

import com.example.library.DTOs.BorrowDTO.BorrowingRecordDTO;
import com.example.library.Models.Book;
import com.example.library.Models.BorrowingRecord;
import com.example.library.Models.Patron;

public class BorrowingMapper {

    public static BorrowingRecord mapToBorrowingRecord(BorrowingRecordDTO borrowingRecordDTO, Book book, Patron patron){
        return new BorrowingRecord(
            book,
            patron,
            borrowingRecordDTO.getBorrowDate(),
            null
        );
    }
//    public static BorrowingRecord mapToReturningRecord(ReturningRecordDTO returningRecordDTO, Book book, Patron patron){
//        return new BorrowingRecord(
//            book,
//            patron,
//            null,
//            returningRecordDTO.getReturnDate()
//        );
//    }
}
