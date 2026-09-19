package com.eleveurpro.mapper;

import com.eleveurpro.dto.DepenseRequest;
import com.eleveurpro.dto.DepenseResponse;
import com.eleveurpro.entity.Depense;

public class DepenseMapper {

    public static Depense toEntity(DepenseRequest request) {
        if (request == null) return null;
        return Depense.builder()
                .categorie(request.getCategorie())
                .montant(request.getMontant())
                .date(request.getDate())
                .description(request.getDescription())
                .build();
    }

    public static DepenseResponse toResponse(Depense entity) {
        if (entity == null) return null;
        return DepenseResponse.builder()
                .id(entity.getId())
                .categorie(entity.getCategorie())
                .montant(entity.getMontant())
                .date(entity.getDate())
                .description(entity.getDescription())
                .build();
    }
}
