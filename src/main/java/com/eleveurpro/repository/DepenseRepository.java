package com.eleveurpro.repository;

import com.eleveurpro.entity.Depense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {
    List<Depense> findByDateBetweenOrderByDateDesc(LocalDate debut, LocalDate fin);
}
