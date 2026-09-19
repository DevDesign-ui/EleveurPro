package com.eleveurpro.mapper;

import com.eleveurpro.dto.EspeceRequest;
import com.eleveurpro.dto.EspeceResponse;
import com.eleveurpro.entity.Espece;

public class EspeceMapper {

    public static Espece toEntity(EspeceRequest request) {
        if (request == null) return null;
        return Espece.builder()
                .nom(request.getNom())
                .description(request.getDescription())
                .actif(request.getActif() != null ? request.getActif() : true)
                .build();
    }

    public static EspeceResponse toResponse(Espece espece) {
        if (espece == null) return null;
        return EspeceResponse.builder()
                .id(espece.getId())
                .nom(espece.getNom())
                .description(espece.getDescription())
                .actif(espece.getActif())
                .build();
    }
}
