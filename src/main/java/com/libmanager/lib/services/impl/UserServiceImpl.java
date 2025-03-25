package com.libmanager.lib.services.impl;

import com.libmanager.lib.domain.dto.UserDto;
import com.libmanager.lib.domain.entity.UserEntity;
import com.libmanager.lib.mappers.Mapper;
import com.libmanager.lib.repositories.UserRepository;
import com.libmanager.lib.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    Mapper<UserEntity, UserDto> userMapper;

    UserServiceImpl(UserRepository userRepository, Mapper<UserEntity, UserDto> userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDto save(@Valid UserDto userDto) {
        UserEntity savedUser = userMapper.mapFrom(userDto);
        return userMapper.mapTo(userRepository.save(savedUser));
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserDto partialUpdate(Long id, UserDto userDto) {
        userDto.setId(id);
        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userDto.getName()).ifPresent(existingUser::setName);
            Optional.ofNullable(userDto.getSurname()).ifPresent(existingUser::setSurname);
            Optional.ofNullable(userDto.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(userDto.getMembershipExpiry()).ifPresent(existingUser::setMembershipExpiry);
            return userMapper.mapTo(userRepository.save(existingUser));
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::mapTo);
    }

    @Override
    public Optional<UserDto> findOne(Long id) {
        Optional<UserEntity> foundUser = userRepository.findById(id);
        return foundUser.map(userMapper::mapTo);
    }

    @Override
    public boolean isMembershipValid(Long id) {
        Optional<UserEntity> foundUser = userRepository.findById(id);
        return foundUser.map(userEntity -> userEntity.getMembershipExpiry().isAfter(LocalDate.now())).orElse(false);
    }
}
