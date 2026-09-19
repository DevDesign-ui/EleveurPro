package com.eleveurpro.service;

import com.eleveurpro.dto.AchatRequest;
import com.eleveurpro.dto.AchatResponse;
import com.eleveurpro.dto.VenteRequest;
import com.eleveurpro.dto.VenteResponse;

import java.util.List;

public interface TransactionService {
    // Achats
    List<AchatResponse> getAllAchats();
    AchatResponse getAchatById(Long id);
    AchatResponse createAchat(AchatRequest request);
    AchatResponse updateAchat(Long id, AchatRequest request);
    void deleteAchat(Long id);

    // Ventes
    List<VenteResponse> getAllVentes();
    VenteResponse getVenteById(Long id);
    VenteResponse createVente(VenteRequest request);
    VenteResponse updateVente(Long id, VenteRequest request);
    void deleteVente(Long id);
    void confirmerVente(Long id);
}
