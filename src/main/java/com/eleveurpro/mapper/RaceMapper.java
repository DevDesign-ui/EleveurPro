package com.eleveurpro.mapper;

import com.eleveurpro.dto.RaceRequest;
import com.eleveurpro.dto.RaceResponse;
import com.eleveurpro.entity.Espece;
import com.eleveurpro.entity.Race;

public class RaceMapper {

    public static Race toEntity(RaceRequest request, Espece espece) {
        if (request == null) return null;
        return Race.builder()
                .nom(request.getNom())
                .description(request.getDescription())
                .espece(espece)
                .build();
    }

    public static RaceResponse toResponse(Race race) {
        if (race == null) return null;
        return RaceResponse.builder()
                .id(race.getId())
                .nom(race.getNom())
                .description(race.getDescription())
                .especeId(race.getEspece() != null ? race.getEspece().getId() : null)
                .especeNom(race.getEspece() != null ? race.getEspece().getNom() : null)
                .build();
    }
}
