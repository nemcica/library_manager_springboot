package com.libmanager.lib.services;

import com.libmanager.lib.domain.dto.LoanDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    //CRUD methods
    LoanDto create(LoanDto loanDto);

    LoanDto save(LoanDto loanDto);

    boolean exists(Long id);

    LoanDto partialUpdate(Long id, LoanDto loanDto);

    void delete(Long id);

    Page<LoanDto> findAll(Pageable pageable);

    Optional<LoanDto> findOne(Long id);

    //Additional Logic
    boolean isLoanValid(LoanDto loanDto);

    List<LoanDto> findUsersBooks(Long userId);
}
