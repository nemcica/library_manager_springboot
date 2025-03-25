package com.libmanager.lib.services;

import com.libmanager.lib.domain.BookStatus;
import com.libmanager.lib.domain.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {

    //CRUD methods
    BookDto save(BookDto bookDto);

    boolean exists(Long id);

    BookDto partialUpdate(Long id, BookDto bookDto);

    void delete(Long id);

    Page<BookDto> findAll(Pageable pageable);

    Optional<BookDto> findOne(Long id);

    Page<BookDto> findByAuthor(String author, Pageable pageable);

    Page<BookDto> findByTitle(String title, Pageable pageable);

    //Additional Logic
    boolean isBookAvailable(Long id);

    void updateBookStatus(Long id, BookStatus status);
}
