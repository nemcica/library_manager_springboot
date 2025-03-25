package com.libmanager.lib.repositories;

import com.libmanager.lib.domain.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Page<BookEntity> findByAuthorContaining(String author, Pageable pageable);

    Page<BookEntity> findByTitleContaining(String title, Pageable pageable);

}
