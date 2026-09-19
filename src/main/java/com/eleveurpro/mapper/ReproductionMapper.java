package com.eleveurpro.mapper;

import com.eleveurpro.dto.ReproductionRequest;
import com.eleveurpro.dto.ReproductionResponse;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.Reproduction;

public class ReproductionMapper {

    public static Reproduction toEntity(ReproductionRequest request, Animal male, Animal femelle) {
        if (request == null) return null;
        return Reproduction.builder()
                .male(male)
                .femelle(femelle)
                .dateAccouplement(request.getDateAccouplement())
                .datePrevueMiseBas(request.getDatePrevueMiseBas())
                .statut(request.getStatut())
                .observation(request.getObservation())
                .build();
    }

    public static ReproductionResponse toResponse(Reproduction entity) {
        if (entity == null) return null;
        return ReproductionResponse.builder()
                .id(entity.getId())
                .maleId(entity.getMale() != null ? entity.getMale().getId() : null)
                .maleNom(entity.getMale() != null ? entity.getMale().getNom() : null)
                .maleNumero(entity.getMale() != null ? entity.getMale().getNumeroIdentification() : null)
                .femelleId(entity.getFemelle() != null ? entity.getFemelle().getId() : null)
                .femelleNom(entity.getFemelle() != null ? entity.getFemelle().getNom() : null)
                .femelleNumero(entity.getFemelle() != null ? entity.getFemelle().getNumeroIdentification() : null)
                .dateAccouplement(entity.getDateAccouplement())
                .datePrevueMiseBas(entity.getDatePrevueMiseBas())
                .statut(entity.getStatut())
                .observation(entity.getObservation())
                .build();
    }
}
