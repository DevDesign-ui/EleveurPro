package com.eleveurpro.repository;

import com.eleveurpro.entity.Reproduction;
import com.eleveurpro.entity.enums.StatutReproduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReproductionRepository extends JpaRepository<Reproduction, Long> {
    List<Reproduction> findByStatut(StatutReproduction statut);

    @org.springframework.data.jpa.repository.Query("SELECT r FROM Reproduction r WHERE r.datePrevueMiseBas IS NOT NULL AND r.datePrevueMiseBas BETWEEN :debut AND :fin AND r.statut IN ('PLANIFIE','EN_COURS') ORDER BY r.datePrevueMiseBas ASC")
    List<Reproduction> findMisesBasProchaines(@org.springframework.data.repository.query.Param("debut") LocalDate debut,
                                               @org.springframework.data.repository.query.Param("fin") LocalDate fin);
}
