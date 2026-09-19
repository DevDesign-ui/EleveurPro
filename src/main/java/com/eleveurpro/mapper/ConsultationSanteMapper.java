package com.eleveurpro.mapper;

import com.eleveurpro.dto.ConsultationSanteRequest;
import com.eleveurpro.dto.ConsultationSanteResponse;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.ConsultationSante;
import com.eleveurpro.entity.Maladie;

public class ConsultationSanteMapper {

    public static ConsultationSante toEntity(ConsultationSanteRequest request, Animal animal, Maladie maladie) {
        if (request == null) return null;
        return ConsultationSante.builder()
                .animal(animal)
                .maladie(maladie)
                .date(request.getDate())
                .symptomes(request.getSymptomes())
                .diagnostic(request.getDiagnostic())
                .traitement(request.getTraitement())
                .veterinaire(request.getVeterinaire())
                .observation(request.getObservation())
                .statut(request.getStatut())
                .build();
    }

    public static ConsultationSanteResponse toResponse(ConsultationSante entity) {
        if (entity == null) return null;
        return ConsultationSanteResponse.builder()
                .id(entity.getId())
                .animalId(entity.getAnimal() != null ? entity.getAnimal().getId() : null)
                .animalNom(entity.getAnimal() != null ? entity.getAnimal().getNom() : null)
                .animalNumero(entity.getAnimal() != null ? entity.getAnimal().getNumeroIdentification() : null)
                .maladieId(entity.getMaladie() != null ? entity.getMaladie().getId() : null)
                .maladieNom(entity.getMaladie() != null ? entity.getMaladie().getNom() : null)
                .date(entity.getDate())
                .symptomes(entity.getSymptomes())
                .diagnostic(entity.getDiagnostic())
                .traitement(entity.getTraitement())
                .veterinaire(entity.getVeterinaire())
                .observation(entity.getObservation())
                .statut(entity.getStatut())
                .build();
    }
}
