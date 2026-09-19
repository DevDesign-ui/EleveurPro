package com.eleveurpro.mapper;

import com.eleveurpro.dto.AchatRequest;
import com.eleveurpro.dto.AchatResponse;
import com.eleveurpro.entity.Achat;

public class AchatMapper {

    public static Achat toEntity(AchatRequest request) {
        if (request == null) return null;
        return Achat.builder()
                .categorie(request.getCategorie())
                .fournisseur(request.getFournisseur())
                .date(request.getDate())
                .montant(request.getMontant())
                .description(request.getDescription())
                .build();
    }

    public static AchatResponse toResponse(Achat entity) {
        if (entity == null) return null;
        return AchatResponse.builder()
                .id(entity.getId())
                .categorie(entity.getCategorie())
                .fournisseur(entity.getFournisseur())
                .date(entity.getDate())
                .montant(entity.getMontant())
                .description(entity.getDescription())
                .build();
    }
}
