package com.libmanager.lib.domain.dto;

import com.libmanager.lib.domain.BookGenre;
import com.libmanager.lib.domain.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDto {

    private Long id;

    private String title;

    private String author;

    private int yearPublished;

    private BookStatus status;

    private BookGenre genre;

}
