package com.eleveurpro.service;

import com.eleveurpro.dto.RaceRequest;
import com.eleveurpro.dto.RaceResponse;

import java.util.List;

public interface RaceService {
    RaceResponse create(RaceRequest request);
    RaceResponse update(Long id, RaceRequest request);
    void delete(Long id);
    RaceResponse getById(Long id);
    List<RaceResponse> getAll();
    List<RaceResponse> getByEspeceId(Long especeId);
}
