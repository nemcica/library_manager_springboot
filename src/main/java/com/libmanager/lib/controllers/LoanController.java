package com.libmanager.lib.controllers;

import com.libmanager.lib.domain.dto.LoanDto;
import com.libmanager.lib.services.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LoanController {

    LoanService loanService;

    LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/loans")
    public ResponseEntity<LoanDto> createLoan(@RequestBody LoanDto loanDto) {
        LoanDto savedLoan = loanService.create(loanDto);
        return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
    }

    @PutMapping("/loans/{id}")
    public ResponseEntity<LoanDto> fullUpdateLoan(@PathVariable("id") Long id, @RequestBody LoanDto loanDto) {
        if(!loanService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        loanDto.setId(id);
        LoanDto savedLoan = loanService.save(loanDto);
        return new ResponseEntity<>(savedLoan, HttpStatus.OK);
    }

    @PatchMapping("/loans/{id}")
    public ResponseEntity<LoanDto> partialUpdateLoan(@PathVariable("id") Long id, @RequestBody LoanDto loanDto) {
        if(!loanService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        LoanDto savedLoan = loanService.partialUpdate(id, loanDto);
        return new ResponseEntity<>(savedLoan, HttpStatus.OK);
    }

    @DeleteMapping("/loans/{id}")
    public ResponseEntity deleteLoan(@PathVariable("id") Long id) {
        loanService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/loans")
    public ResponseEntity<Page<LoanDto>> getAllLoans(Pageable pageable) {
        Page<LoanDto> foundLoans = loanService.findAll(pageable);
        return new ResponseEntity<>(foundLoans, HttpStatus.OK);
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<LoanDto> getOneLoan(@PathVariable("id") Long id) {
        Optional<LoanDto> foundLoan = loanService.findOne(id);
        return foundLoan.map(loanDto -> new ResponseEntity<>(loanDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/loans/user/{userId}")
    public ResponseEntity<List<LoanDto>> getUsersLoans(@PathVariable("userId") Long userId) {
        List<LoanDto> foundLoans = loanService.findUsersBooks(userId);
        return new ResponseEntity<>(foundLoans, HttpStatus.OK);
    }
}
