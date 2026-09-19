package com.eleveurpro.service;

import com.eleveurpro.dto.EspeceRequest;
import com.eleveurpro.dto.EspeceResponse;
import com.eleveurpro.dto.PageResponse;

import java.util.List;

public interface EspeceService {
    EspeceResponse create(EspeceRequest request);
    EspeceResponse update(Long id, EspeceRequest request);
    void delete(Long id);
    EspeceResponse getById(Long id);
    List<EspeceResponse> getAll();
    PageResponse<EspeceResponse> getAllPaginated(int page, int size);
}
