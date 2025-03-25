package com.libmanager.lib.services;

import com.libmanager.lib.domain.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    //CRUD methods
    UserDto save(UserDto userDto);

    boolean exists(Long id);

    UserDto partialUpdate(Long id, UserDto userDto);

    void delete(Long id);

    Page<UserDto> findAll(Pageable pageable);

    Optional<UserDto> findOne(Long id);

    //Additional Logic
    boolean isMembershipValid(Long id);
}
