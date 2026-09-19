package com.eleveurpro.service;

import com.eleveurpro.dto.AnimalRequest;
import com.eleveurpro.dto.AnimalResponse;
import com.eleveurpro.dto.PageResponse;
import com.eleveurpro.entity.enums.StatutAnimal;

import java.util.List;

public interface AnimalService {
    AnimalResponse create(AnimalRequest request);
    AnimalResponse update(Long id, AnimalRequest request);
    void delete(Long id);
    AnimalResponse getById(Long id);
    List<AnimalResponse> getAll();
    PageResponse<AnimalResponse> getAllPaginated(int page, int size);
    PageResponse<AnimalResponse> search(String numero, String nom, Long especeId, Long raceId, StatutAnimal statut, int page, int size);
}
