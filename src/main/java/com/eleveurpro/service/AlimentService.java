package com.eleveurpro.service;

import com.eleveurpro.dto.AlimentRequest;
import com.eleveurpro.dto.AlimentResponse;
import com.eleveurpro.dto.ConsommationAlimentRequest;
import com.eleveurpro.dto.ConsommationAlimentResponse;

import java.util.List;

public interface AlimentService {
    AlimentResponse create(AlimentRequest request);
    AlimentResponse update(Long id, AlimentRequest request);
    void delete(Long id);
    AlimentResponse getById(Long id);
    List<AlimentResponse> getAll();
    List<AlimentResponse> getStockBas();

    ConsommationAlimentResponse createConsommation(ConsommationAlimentRequest request);
    List<ConsommationAlimentResponse> getAllConsommations();
    List<ConsommationAlimentResponse> getConsommationsByAnimal(Long animalId);
}
