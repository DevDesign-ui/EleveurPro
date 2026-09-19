package com.eleveurpro.mapper;

import com.eleveurpro.dto.TraitementRequest;
import com.eleveurpro.dto.TraitementResponse;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.Traitement;

public class TraitementMapper {

    public static Traitement toEntity(TraitementRequest request, Animal animal) {
        if (request == null) return null;
        return Traitement.builder()
                .animal(animal)
                .medicament(request.getMedicament())
                .dosage(request.getDosage())
                .frequence(request.getFrequence())
                .dateDebut(request.getDateDebut())
                .dateFin(request.getDateFin())
                .veterinaire(request.getVeterinaire())
                .observation(request.getObservation())
                .build();
    }

    public static TraitementResponse toResponse(Traitement entity) {
        if (entity == null) return null;
        return TraitementResponse.builder()
                .id(entity.getId())
                .animalId(entity.getAnimal() != null ? entity.getAnimal().getId() : null)
                .animalNom(entity.getAnimal() != null ? entity.getAnimal().getNom() : null)
                .animalNumero(entity.getAnimal() != null ? entity.getAnimal().getNumeroIdentification() : null)
                .medicament(entity.getMedicament())
                .dosage(entity.getDosage())
                .frequence(entity.getFrequence())
                .dateDebut(entity.getDateDebut())
                .dateFin(entity.getDateFin())
                .veterinaire(entity.getVeterinaire())
                .observation(entity.getObservation())
                .build();
    }
}
