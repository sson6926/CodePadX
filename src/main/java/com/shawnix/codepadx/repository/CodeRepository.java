package com.shawnix.codepadx.repository;

import com.shawnix.codepadx.entity.Code;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long>, JpaSpecificationExecutor<Code> {
//    Page<Code> findByUserId(Long userId, Pageable pageable);

    @Query("""
    select c
    from Code c
    join fetch c.user u
    join fetch c.language l
    where c.id = :id
""")
    Optional<Code> findByIdWithUserAndLanguage(@Param("id") Long id);

    @EntityGraph(attributePaths = {"user", "language"})
    Page<Code> findByUserId(Long userId, Pageable pageable);
    @EntityGraph(attributePaths = {"user", "language"})
    Page<Code> findAll(Pageable pageable);
    @EntityGraph(attributePaths = {"user", "language"})
    Page<Code> findAll(Specification<Code> spec, Pageable pageable);
}
