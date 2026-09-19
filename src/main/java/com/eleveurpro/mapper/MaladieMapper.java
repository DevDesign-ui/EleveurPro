package com.eleveurpro.mapper;

import com.eleveurpro.dto.MaladieRequest;
import com.eleveurpro.dto.MaladieResponse;
import com.eleveurpro.entity.Maladie;

public class MaladieMapper {

    public static Maladie toEntity(MaladieRequest request) {
        if (request == null) return null;
        return Maladie.builder()
                .nom(request.getNom())
                .description(request.getDescription())
                .build();
    }

    public static MaladieResponse toResponse(Maladie maladie) {
        if (maladie == null) return null;
        return MaladieResponse.builder()
                .id(maladie.getId())
                .nom(maladie.getNom())
                .description(maladie.getDescription())
                .build();
    }
}
