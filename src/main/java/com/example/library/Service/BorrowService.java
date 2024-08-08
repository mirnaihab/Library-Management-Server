package com.example.library.Service;


import com.example.library.Common.Enums;
import com.example.library.DTOs.BorrowDTO.BorrowResponseDTO;
import com.example.library.DTOs.BorrowDTO.BorrowingRecordDTO;
import com.example.library.DTOs.ReturnDTO.ReturnResponseDTO;
import com.example.library.DTOs.ReturnDTO.ReturningRecordDTO;
import com.example.library.Mapper.BorrowingMapper;
import com.example.library.Models.Book;
import com.example.library.Models.BorrowingRecord;
import com.example.library.Models.Patron;
import com.example.library.Repository.IBooksRepository;
import com.example.library.Repository.IBorrowRepository;
import com.example.library.Repository.IPatronRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BorrowService {

    private final IBooksRepository IBooksRepository;
    private final IPatronRepository IPatronRepository;
    private final IBorrowRepository IBorrowRepository;

    public BorrowService(IBooksRepository IBooksRepository, IPatronRepository IPatronRepository, IBorrowRepository IBorrowRepository) {
        this.IBooksRepository = IBooksRepository;
        this.IPatronRepository = IPatronRepository;
        this.IBorrowRepository = IBorrowRepository;
    }

    @Transactional
    public BorrowResponseDTO borrowBook(Long bookId, Long patronId, BorrowingRecordDTO borrowingRecordDTO) {
        Book book = IBooksRepository.findById(bookId).orElse(null);
        if (book != null) {
            Patron existingPatron = IPatronRepository.findById(patronId).orElse(null);
            if (existingPatron != null) {
                try {
                    BorrowingRecord existingRecord = IBorrowRepository.findBorrowingRecordByBookAndPatronAndReturnDateIsNull(book, existingPatron);
                    if(existingRecord!=null) {
                        return new BorrowResponseDTO(null, null, null, null, null,null, Enums.StatusResponse.Failed, "Book Is Already Borrowed");
                    }
                    BorrowingRecord record = BorrowingMapper.mapToBorrowingRecord(borrowingRecordDTO, book, existingPatron);
                    IBorrowRepository.save(record);
                    return new BorrowResponseDTO(bookId,patronId,book.getTitle(),book.getAuthor(),book.getPublicationYear(),book.getISBN(),Enums.StatusResponse.Success, "Book Borrowed Successfully");


                } catch (Exception e) {
                    log.error("Failed To Borrow Book: {}", e.getMessage(), e);
                    return new BorrowResponseDTO(null, null, null, null, null,null, Enums.StatusResponse.Failed, "Failed To Borrow Book");
                }
            }
            else {
                return new BorrowResponseDTO(null, null, null, null, null,null, Enums.StatusResponse.Failed, "Patron Not Found");
            }
        } else {
            return new BorrowResponseDTO(null, null, null, null, null, null,Enums.StatusResponse.Failed, "Book  Not Found");
        }
    }

    @Transactional
    public ReturnResponseDTO returnBook(Long bookId, Long patronId, ReturningRecordDTO returningRecordDTO) {
        Book book = IBooksRepository.findById(bookId).orElse(null);
        if (book != null) {
            Patron existingPatron = IPatronRepository.findById(patronId).orElse(null);
            if (existingPatron != null) {
                try {
                    BorrowingRecord record = IBorrowRepository.findBorrowingRecordByBookAndPatronAndReturnDateIsNull(book, existingPatron);
                    if (record != null ) {
                        record.setReturnDate(returningRecordDTO.getReturnDate());
                        IBorrowRepository.save(record);
                        return new ReturnResponseDTO(bookId,patronId,book.getTitle(),book.getAuthor(),book.getPublicationYear(),book.getISBN(),Enums.StatusResponse.Success, "Book Returned Successfully");

                    }
                    return new ReturnResponseDTO(null, null, null, null, null, null,Enums.StatusResponse.Failed, "Borrowing Record Not Found Or Book Is Already Returned");

                } catch (Exception e) {
                    log.error("Failed To Return Book: {}", e.getMessage(), e);
                    return new ReturnResponseDTO(null, null, null, null, null,null, Enums.StatusResponse.Failed, "Failed To Return Book");
                }
            }
            else {
                return new ReturnResponseDTO(null, null, null, null, null,null, Enums.StatusResponse.Failed, "Patron Not Found");
            }
        } else {
            return new ReturnResponseDTO(null, null, null, null, null, null,Enums.StatusResponse.Failed, "Book  Not Found");
        }
    }
}
