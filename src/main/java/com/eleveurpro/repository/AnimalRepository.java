package com.eleveurpro.repository;

import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.enums.StatutAnimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Optional<Animal> findByNumeroIdentification(String numeroIdentification);

    @Query("SELECT a FROM Animal a WHERE " +
           "(:numero IS NULL OR LOWER(a.numeroIdentification) LIKE LOWER(CONCAT('%', :numero, '%'))) AND " +
           "(:nom IS NULL OR LOWER(a.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
           "(:especeId IS NULL OR a.espece.id = :especeId) AND " +
           "(:raceId IS NULL OR a.race.id = :raceId) AND " +
           "(:statut IS NULL OR a.statut = :statut)")
    Page<Animal> search(@Param("numero") String numero,
                        @Param("nom") String nom,
                        @Param("especeId") Long especeId,
                        @Param("raceId") Long raceId,
                        @Param("statut") StatutAnimal statut,
                        Pageable pageable);

    long countByStatut(StatutAnimal statut);

    boolean existsByNumeroIdentification(String numeroIdentification);
}
