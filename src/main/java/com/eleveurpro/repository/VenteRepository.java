package com.eleveurpro.repository;

import com.eleveurpro.entity.Vente;
import com.eleveurpro.entity.enums.StatutVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    List<Vente> findByDateBetweenOrderByDateDesc(LocalDate debut, LocalDate fin);
    List<Vente> findByStatut(StatutVente statut);
}
