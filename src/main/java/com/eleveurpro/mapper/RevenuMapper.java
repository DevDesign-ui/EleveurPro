package com.eleveurpro.mapper;

import com.eleveurpro.dto.RevenuRequest;
import com.eleveurpro.dto.RevenuResponse;
import com.eleveurpro.entity.Revenu;

public class RevenuMapper {

    public static Revenu toEntity(RevenuRequest request) {
        if (request == null) return null;
        return Revenu.builder()
                .categorie(request.getCategorie())
                .montant(request.getMontant())
                .date(request.getDate())
                .description(request.getDescription())
                .build();
    }

    public static RevenuResponse toResponse(Revenu entity) {
        if (entity == null) return null;
        return RevenuResponse.builder()
                .id(entity.getId())
                .categorie(entity.getCategorie())
                .montant(entity.getMontant())
                .date(entity.getDate())
                .description(entity.getDescription())
                .build();
    }
}
