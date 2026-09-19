package com.eleveurpro.mapper;

import com.eleveurpro.dto.AlimentRequest;
import com.eleveurpro.dto.AlimentResponse;
import com.eleveurpro.entity.Aliment;

public class AlimentMapper {

    public static Aliment toEntity(AlimentRequest request) {
        if (request == null) return null;
        return Aliment.builder()
                .nom(request.getNom())
                .type(request.getType())
                .unite(request.getUnite())
                .quantiteStock(request.getQuantiteStock() != null ? request.getQuantiteStock() : 0.0)
                .prixUnitaire(request.getPrixUnitaire())
                .seuilAlerte(request.getSeuilAlerte())
                .dateExpiration(request.getDateExpiration())
                .fournisseur(request.getFournisseur())
                .actif(request.getActif() != null ? request.getActif() : true)
                .build();
    }

    public static AlimentResponse toResponse(Aliment aliment) {
        if ( aliment == null) return null;
        boolean stockBas = false;
        if (aliment.getSeuilAlerte() != null && aliment.getQuantiteStock() != null) {
            stockBas = aliment.getQuantiteStock() <= aliment.getSeuilAlerte();
        }
        return AlimentResponse.builder()
                .id(aliment.getId())
                .nom(aliment.getNom())
                .type(aliment.getType())
                .unite(aliment.getUnite())
                .quantiteStock(aliment.getQuantiteStock())
                .prixUnitaire(aliment.getPrixUnitaire())
                .seuilAlerte(aliment.getSeuilAlerte())
                .dateExpiration(aliment.getDateExpiration())
                .fournisseur(aliment.getFournisseur())
                .actif(aliment.getActif())
                .stockBas(stockBas)
                .build();
    }
}
