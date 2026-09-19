package com.eleveurpro.repository;

import com.eleveurpro.entity.Espece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspeceRepository extends JpaRepository<Espece, Long> {
    List<Espece> findByActifTrue();
}
