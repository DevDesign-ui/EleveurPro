package com.eleveurpro.repository;

import com.eleveurpro.entity.Naissance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NaissanceRepository extends JpaRepository<Naissance, Long> {
    List<Naissance> findByDateNaissanceBetweenOrderByDateNaissanceDesc(LocalDate debut, LocalDate fin);
}
