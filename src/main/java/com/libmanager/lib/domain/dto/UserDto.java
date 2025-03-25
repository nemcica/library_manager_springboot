package com.libmanager.lib.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Email
    private String email;

    private LocalDate membershipExpiry;
}
