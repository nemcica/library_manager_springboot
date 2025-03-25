package com.libmanager.lib.serviceTests;

import com.libmanager.lib.domain.dto.UserDto;
import com.libmanager.lib.domain.entity.UserEntity;
import com.libmanager.lib.mappers.Mapper;
import com.libmanager.lib.repositories.UserRepository;
import com.libmanager.lib.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    Mapper<UserEntity, UserDto> userMapper;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void UserService_SaveValidUser_ReturnsSavedUser() {

        UserDto newUserDto = UserDto.builder()
                .name("John")
                .surname("Doe")
                .email("johndoe@gmail.com")
                .membershipExpiry(LocalDate.of(2025, 6, 13))
                .build();

        UserEntity savedUserEntity = UserEntity.builder()
                .name("John")
                .surname("Doe")
                .email("johndoe@gmail.com")
                .membershipExpiry(LocalDate.of(2025, 6, 13))
                .build();

        when(userMapper.mapFrom(newUserDto)).thenReturn(savedUserEntity);
        when(userRepository.save(savedUserEntity)).thenReturn(savedUserEntity);
        when(userMapper.mapTo(savedUserEntity)).thenReturn(newUserDto);

        UserDto savedUser = userService.save(newUserDto);

        Assertions.assertNotNull(savedUser);

        verify(userMapper).mapFrom(newUserDto);
        verify(userMapper).mapTo(savedUserEntity);
        verify(userRepository).save(savedUserEntity);
    }

}
