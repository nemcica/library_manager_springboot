package com.libmanager.lib.repositories;

import com.libmanager.lib.domain.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    public int countByUserIdAndReturnedFalse(Long id);

    public List<LoanEntity> findByUserIdAndReturnedFalse(Long id);
}
