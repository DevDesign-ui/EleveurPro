package com.eleveurpro.repository;

import com.eleveurpro.entity.Traitement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TraitementRepository extends JpaRepository<Traitement, Long> {
    List<Traitement> findByAnimalIdOrderByDateDebutDesc(Long animalId);

    @org.springframework.data.jpa.repository.Query("SELECT t FROM Traitement t WHERE t.dateFin IS NOT NULL AND t.dateFin BETWEEN :debut AND :fin ORDER BY t.dateFin ASC")
    List<Traitement> findTraitementsEcheance(@org.springframework.data.repository.query.Param("debut") LocalDate debut,
                                              @org.springframework.data.repository.query.Param("fin") LocalDate fin);
}
