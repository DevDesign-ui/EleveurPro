package com.eleveurpro.repository;

import com.eleveurpro.entity.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaladieRepository extends JpaRepository<Maladie, Long> {
    boolean existsByNom(String nom);
}
