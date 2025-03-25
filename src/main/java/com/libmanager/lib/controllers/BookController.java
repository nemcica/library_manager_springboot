package com.libmanager.lib.controllers;

import com.libmanager.lib.domain.dto.BookDto;
import com.libmanager.lib.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {

    BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto savedBook = bookService.save(bookDto);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookDto> fullUpdateBook(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
        if(!bookService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        bookDto.setId(id);
        BookDto savedBook = bookService.save(bookDto);
        return new ResponseEntity<>(savedBook, HttpStatus.OK);
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
        if(!bookService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        BookDto savedBook = bookService.partialUpdate(id, bookDto);
        return new ResponseEntity<>(savedBook, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/books")
    public ResponseEntity<Page<BookDto>> getAllBooks(Pageable pageable) {
        Page<BookDto> foundBooks = bookService.findAll(pageable);
        return new ResponseEntity<>(foundBooks, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getOneBook(@PathVariable("id") Long id) {
        Optional<BookDto> foundBook = bookService.findOne(id);
        return foundBook.map(bookDto -> new ResponseEntity<>(bookDto, HttpStatus.OK)).orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/books/{author}")
    public ResponseEntity<Page<BookDto>> getBooksByAuthor(@RequestParam("author") String author, Pageable pageable) {
        Page<BookDto> foundBooks = bookService.findByAuthor(author, pageable);
        return new ResponseEntity<>(foundBooks, HttpStatus.OK);
    }

    @GetMapping("/books/{title}")
    public ResponseEntity<Page<BookDto>> getBooksByTitle(@RequestParam("title") String title, Pageable pageable) {
        Page<BookDto> foundBooks = bookService.findByTitle(title, pageable);
        return new ResponseEntity<>(foundBooks, HttpStatus.OK);
    }
}
