package com.libmanager.lib.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoanDto {

    private Long id;

    private UserDto user;

    private BookDto book;

    private LocalDate startDate;

    private LocalDate dueDate;

    private boolean returned;
}
