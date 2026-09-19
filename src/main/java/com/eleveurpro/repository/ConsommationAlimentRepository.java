package com.eleveurpro.repository;

import com.eleveurpro.entity.ConsommationAliment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsommationAlimentRepository extends JpaRepository<ConsommationAliment, Long> {
    List<ConsommationAliment> findByAnimalIdOrderByDateDesc(Long animalId);
    List<ConsommationAliment> findByDateBetweenOrderByDateDesc(LocalDate debut, LocalDate fin);
}
