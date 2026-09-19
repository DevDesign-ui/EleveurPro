package com.eleveurpro.service;

import com.eleveurpro.dto.DepenseRequest;
import com.eleveurpro.dto.DepenseResponse;
import com.eleveurpro.dto.RevenuRequest;
import com.eleveurpro.dto.RevenuResponse;

import java.util.List;

public interface FinanceService {
    // Dépenses
    List<DepenseResponse> getAllDepenses();
    DepenseResponse getDepenseById(Long id);
    DepenseResponse createDepense(DepenseRequest request);
    DepenseResponse updateDepense(Long id, DepenseRequest request);
    void deleteDepense(Long id);

    // Revenus
    List<RevenuResponse> getAllRevenus();
    RevenuResponse getRevenuById(Long id);
    RevenuResponse createRevenu(RevenuRequest request);
    RevenuResponse updateRevenu(Long id, RevenuRequest request);
    void deleteRevenu(Long id);
}
