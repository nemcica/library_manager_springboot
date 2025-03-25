package com.libmanager.lib.services.impl;

import com.libmanager.lib.domain.BookStatus;
import com.libmanager.lib.domain.dto.BookDto;
import com.libmanager.lib.domain.dto.LoanDto;
import com.libmanager.lib.domain.dto.UserDto;
import com.libmanager.lib.domain.entity.BookEntity;
import com.libmanager.lib.domain.entity.LoanEntity;
import com.libmanager.lib.domain.entity.UserEntity;
import com.libmanager.lib.mappers.Mapper;
import com.libmanager.lib.repositories.LoanRepository;
import com.libmanager.lib.services.BookService;
import com.libmanager.lib.services.LoanService;
import com.libmanager.lib.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    LoanRepository loanRepository;
    Mapper<LoanEntity, LoanDto> loanMapper;
    Mapper<UserEntity, UserDto> userMapper;
    Mapper<BookEntity, BookDto> bookMapper;
    UserService userService;
    BookService bookService;

    LoanServiceImpl(LoanRepository loanRepository,
                    Mapper<LoanEntity, LoanDto> loanMapper,
                    Mapper<UserEntity, UserDto> userMapper,
                    Mapper<BookEntity, BookDto> bookMapper,
                    UserService userService,
                    BookService bookService) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public LoanDto create(LoanDto loanDto) {
        isLoanValid(loanDto);
        bookService.updateBookStatus(loanDto.getBook().getId(), BookStatus.UNAVAILABLE);

        LoanEntity savedLoan = loanMapper.mapFrom(loanDto);
        return loanMapper.mapTo(loanRepository.save(savedLoan));
    }

    @Override
    public LoanDto save(LoanDto loanDto) {
        LoanEntity savedLoan = loanMapper.mapFrom(loanDto);
        return loanMapper.mapTo(loanRepository.save(savedLoan));
    }

    @Override
    public boolean exists(Long id) {
        return loanRepository.existsById(id);
    }

    @Override
    public LoanDto partialUpdate(Long id, LoanDto loanDto) {
        loanDto.setId(id);

        return loanRepository.findById(id).map(existingLoan -> {
            Optional.ofNullable(loanDto.getUser()).ifPresent(userDto -> existingLoan.setUser(userMapper.mapFrom(userDto)));
            Optional.ofNullable(loanDto.getBook()).ifPresent(bookDto -> existingLoan.setBook(bookMapper.mapFrom(bookDto)));
            Optional.ofNullable(loanDto.getStartDate()).ifPresent(existingLoan::setStartDate);
            Optional.ofNullable(loanDto.getDueDate()).ifPresent(existingLoan::setDueDate);
            Optional.of(loanDto.isReturned()).ifPresent(existingLoan::setReturned);
            return loanMapper.mapTo(loanRepository.save(existingLoan));
        }).orElseThrow(() -> new RuntimeException(("Loan not found")));
    }

    @Override
    public void delete(Long id) {
        loanRepository.deleteById(id);
    }

    @Override
    public Page<LoanDto> findAll(Pageable pageable) {
        Page<LoanEntity> foundLoans = loanRepository.findAll(pageable);
        return foundLoans.map(loanMapper::mapTo);
    }

    @Override
    public Optional<LoanDto> findOne(Long id) {
        Optional<LoanEntity> foundLoan = loanRepository.findById(id);
        return foundLoan.map(loanMapper::mapTo);
    }

    @Override
    public boolean isLoanValid(LoanDto loanDto) {
        if(!userService.isMembershipValid(loanDto.getUser().getId())) {
            throw new RuntimeException("User membership expired");
        }

        if(!bookService.isBookAvailable(loanDto.getBook().getId())) {
            throw  new RuntimeException("Book is not available");
        }

        List<LoanEntity> usersLoanedBooks = loanRepository.findByUserIdAndReturnedFalse(loanDto.getUser().getId());
        for(LoanEntity loan : usersLoanedBooks) {
            if(loan.getDueDate().isBefore(LocalDate.now())) {
                throw  new RuntimeException("User has an overdue book");
            }
        }

        int currentBooks = loanRepository.countByUserIdAndReturnedFalse(loanDto.getUser().getId());
        if(currentBooks >= 3) {
            throw new RuntimeException("User has borrowed the maximum amount of books");
        }

        return true;
    }

    @Override
    public List<LoanDto> findUsersBooks(Long userId) {
        return loanRepository.findByUserIdAndReturnedFalse(userId)
                .stream()
                .map(loanEntity -> loanMapper.mapTo(loanEntity)).collect(Collectors.toList());
    }
}
