package com.shawnix.codepadx.repository;

import com.shawnix.codepadx.entity.Code;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long> {
    Page<Code> findByUserId(Long userId, Pageable pageable);
}
