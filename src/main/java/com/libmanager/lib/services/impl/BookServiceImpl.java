package com.libmanager.lib.services.impl;

import com.libmanager.lib.domain.BookStatus;
import com.libmanager.lib.domain.dto.BookDto;
import com.libmanager.lib.domain.entity.BookEntity;

import com.libmanager.lib.mappers.Mapper;
import com.libmanager.lib.repositories.BookRepository;
import com.libmanager.lib.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    Mapper<BookEntity, BookDto> bookMapper;

    BookServiceImpl(BookRepository bookRepository, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto save(BookDto bookDto) {
        BookEntity savedBook = bookMapper.mapFrom(bookDto);
        return bookMapper.mapTo(bookRepository.save(savedBook));
    }

    @Override
    public boolean exists(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public BookDto partialUpdate(Long id, BookDto bookDto) {
        bookDto.setId(id);
        return bookRepository.findById(id).map(existingBook -> {
            Optional.ofNullable(bookDto.getTitle()).ifPresent(existingBook::setTitle);
            Optional.ofNullable(bookDto.getAuthor()).ifPresent(existingBook::setAuthor);
            Optional.of(bookDto.getYearPublished()).ifPresent(existingBook::setYearPublished);
            Optional.ofNullable(bookDto.getStatus()).ifPresent(existingBook::setStatus);
            Optional.ofNullable(bookDto.getGenre()).ifPresent(existingBook::setGenre);
            return bookMapper.mapTo(bookRepository.save(existingBook));
        }).orElseThrow(()-> new RuntimeException("Book does not exist"));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::mapTo);
    }

    @Override
    public Optional<BookDto> findOne(Long id) {
        Optional<BookEntity> foundBook = bookRepository.findById(id);
        return foundBook.map(bookMapper::mapTo);
    }

    @Override
    public Page<BookDto> findByAuthor(String author, Pageable pageable) {
        return bookRepository.findByAuthorContaining(author, pageable).map(bookMapper::mapTo);
    }

    @Override
    public Page<BookDto> findByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitleContaining(title, pageable).map(bookMapper::mapTo);
    }

    @Override
    public boolean isBookAvailable(Long id) {
        Optional<BookEntity> foundBook = bookRepository.findById(id);
        return foundBook.map(bookEntity -> bookEntity.getStatus().equals(BookStatus.AVAILABLE)).orElse(false);
    }

    @Override
    public void updateBookStatus(Long id, BookStatus status) {
        Optional<BookEntity> foundBook = bookRepository.findById(id);
        if(foundBook.isPresent()) {
            BookEntity bookEntity = foundBook.get();
            bookEntity.setStatus(status);
            bookRepository.save(bookEntity);
        }
    }
}
