package com.libmanager.lib.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "loans")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Column(nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @Column(nullable = false)
    private BookEntity book;

    private LocalDate startDate;

    private LocalDate dueDate;

    private boolean returned;
}
