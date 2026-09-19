package com.eleveurpro.repository;

import com.eleveurpro.entity.Aliment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlimentRepository extends JpaRepository<Aliment, Long> {
    List<Aliment> findByActifTrue();

    @org.springframework.data.jpa.repository.Query("SELECT a FROM Aliment a WHERE a.actif = true AND a.quantiteStock <= a.seuilAlerte")
    List<Aliment> findStockBas();
}
