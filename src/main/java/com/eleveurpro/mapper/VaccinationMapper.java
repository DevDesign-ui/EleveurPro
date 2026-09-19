package com.eleveurpro.mapper;

import com.eleveurpro.dto.VaccinationRequest;
import com.eleveurpro.dto.VaccinationResponse;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.Vaccination;

public class VaccinationMapper {

    public static Vaccination toEntity(VaccinationRequest request, Animal animal) {
        if (request == null) return null;
        return Vaccination.builder()
                .animal(animal)
                .vaccin(request.getVaccin())
                .dateVaccination(request.getDateVaccination())
                .prochaineDate(request.getProchaineDate())
                .veterinaire(request.getVeterinaire())
                .observation(request.getObservation())
                .build();
    }

    public static VaccinationResponse toResponse(Vaccination entity) {
        if (entity == null) return null;
        return VaccinationResponse.builder()
                .id(entity.getId())
                .animalId(entity.getAnimal() != null ? entity.getAnimal().getId() : null)
                .animalNom(entity.getAnimal() != null ? entity.getAnimal().getNom() : null)
                .animalNumero(entity.getAnimal() != null ? entity.getAnimal().getNumeroIdentification() : null)
                .vaccin(entity.getVaccin())
                .dateVaccination(entity.getDateVaccination())
                .prochaineDate(entity.getProchaineDate())
                .veterinaire(entity.getVeterinaire())
                .observation(entity.getObservation())
                .build();
    }
}
