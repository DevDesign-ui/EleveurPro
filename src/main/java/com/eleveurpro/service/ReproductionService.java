package com.eleveurpro.service;

import com.eleveurpro.dto.NaissanceRequest;
import com.eleveurpro.dto.NaissanceResponse;
import com.eleveurpro.dto.ReproductionRequest;
import com.eleveurpro.dto.ReproductionResponse;

import java.util.List;

public interface ReproductionService {
    // Reproductions
    List<ReproductionResponse> getAllReproductions();
    ReproductionResponse getReproductionById(Long id);
    ReproductionResponse createReproduction(ReproductionRequest request);
    ReproductionResponse updateReproduction(Long id, ReproductionRequest request);
    void deleteReproduction(Long id);

    // Naissances
    List<NaissanceResponse> getAllNaissances();
    NaissanceResponse getNaissanceById(Long id);
    NaissanceResponse createNaissance(NaissanceRequest request);
    NaissanceResponse updateNaissance(Long id, NaissanceRequest request);
    void deleteNaissance(Long id);
}
