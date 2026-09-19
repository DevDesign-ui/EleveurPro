package com.eleveurpro.repository;

import com.eleveurpro.entity.ConsultationSante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultationSanteRepository extends JpaRepository<ConsultationSante, Long> {
    List<ConsultationSante> findByAnimalIdOrderByDateDesc(Long animalId);
    List<ConsultationSante> findByDateBetween(LocalDate debut, LocalDate fin);
}
