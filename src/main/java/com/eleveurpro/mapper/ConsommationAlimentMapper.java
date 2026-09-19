package com.eleveurpro.mapper;

import com.eleveurpro.dto.ConsommationAlimentRequest;
import com.eleveurpro.dto.ConsommationAlimentResponse;
import com.eleveurpro.entity.Aliment;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.ConsommationAliment;

public class ConsommationAlimentMapper {

    public static ConsommationAliment toEntity(ConsommationAlimentRequest request, Animal animal, Aliment aliment) {
        if (request == null) return null;
        return ConsommationAliment.builder()
                .animal(animal)
                .aliment(aliment)
                .quantite(request.getQuantite())
                .date(request.getDate())
                .cout(request.getCout())
                .observation(request.getObservation())
                .build();
    }

    public static ConsommationAlimentResponse toResponse(ConsommationAliment entity) {
        if (entity == null) return null;
        return ConsommationAlimentResponse.builder()
                .id(entity.getId())
                .animalId(entity.getAnimal() != null ? entity.getAnimal().getId() : null)
                .animalNom(entity.getAnimal() != null ? entity.getAnimal().getNom() : null)
                .animalNumero(entity.getAnimal() != null ? entity.getAnimal().getNumeroIdentification() : null)
                .alimentId(entity.getAliment() != null ? entity.getAliment().getId() : null)
                .alimentNom(entity.getAliment() != null ? entity.getAliment().getNom() : null)
                .quantite(entity.getQuantite())
                .date(entity.getDate())
                .cout(entity.getCout())
                .observation(entity.getObservation())
                .build();
    }
}
