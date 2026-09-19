package com.eleveurpro.repository;

import com.eleveurpro.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
    List<Vaccination> findByAnimalIdOrderByDateVaccinationDesc(Long animalId);

    @org.springframework.data.jpa.repository.Query("SELECT v FROM Vaccination v WHERE v.prochaineDate IS NOT NULL AND v.prochaineDate BETWEEN :debut AND :fin ORDER BY v.prochaineDate ASC")
    List<Vaccination> findVaccinationsProchaines(@org.springframework.data.repository.query.Param("debut") LocalDate debut,
                                                  @org.springframework.data.repository.query.Param("fin") LocalDate fin);
}
