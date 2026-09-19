package com.eleveurpro.mapper;

import com.eleveurpro.dto.NaissanceRequest;
import com.eleveurpro.dto.NaissanceResponse;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.Naissance;

public class NaissanceMapper {

    public static Naissance toEntity(NaissanceRequest request, Animal mere, Animal pere) {
        if (request == null) return null;
        return Naissance.builder()
                .mere(mere)
                .pere(pere)
                .dateNaissance(request.getDateNaissance())
                .nombrePetits(request.getNombrePetits())
                .observation(request.getObservation())
                .build();
    }

    public static NaissanceResponse toResponse(Naissance entity) {
        if (entity == null) return null;
        return NaissanceResponse.builder()
                .id(entity.getId())
                .mereId(entity.getMere() != null ? entity.getMere().getId() : null)
                .mereNom(entity.getMere() != null ? entity.getMere().getNom() : null)
                .mereNumero(entity.getMere() != null ? entity.getMere().getNumeroIdentification() : null)
                .pereId(entity.getPere() != null ? entity.getPere().getId() : null)
                .pereNom(entity.getPere() != null ? entity.getPere().getNom() : null)
                .pereNumero(entity.getPere() != null ? entity.getPere().getNumeroIdentification() : null)
                .dateNaissance(entity.getDateNaissance())
                .nombrePetits(entity.getNombrePetits())
                .observation(entity.getObservation())
                .build();
    }
}
