package com.libmanager.lib.mappers.impl;

import com.libmanager.lib.domain.dto.LoanDto;
import com.libmanager.lib.domain.entity.LoanEntity;
import com.libmanager.lib.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper implements Mapper<LoanEntity, LoanDto> {

    ModelMapper modelMapper;

    LoanMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LoanDto mapTo(LoanEntity loanEntity) {
        return modelMapper.map(loanEntity, LoanDto.class);
    }

    @Override
    public LoanEntity mapFrom(LoanDto loanDto) {
        return modelMapper.map(loanDto, LoanEntity.class);
    }
}
