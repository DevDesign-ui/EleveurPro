package com.eleveurpro.mapper;

import com.eleveurpro.dto.AnimalRequest;
import com.eleveurpro.dto.AnimalResponse;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.Espece;
import com.eleveurpro.entity.Race;

public class AnimalMapper {

    public static Animal toEntity(AnimalRequest request, Espece espece, Race race) {
        if (request == null) return null;
        return Animal.builder()
                .numeroIdentification(request.getNumeroIdentification())
                .nom(request.getNom())
                .espece(espece)
                .race(race)
                .sexe(request.getSexe())
                .dateNaissance(request.getDateNaissance())
                .poids(request.getPoids())
                .couleur(request.getCouleur())
                .dateAcquisition(request.getDateAcquisition())
                .prixAcquisition(request.getPrixAcquisition())
                .origine(request.getOrigine())
                .statut(request.getStatut())
                .photoUrl(request.getPhotoUrl())
                .description(request.getDescription())
                .build();
    }

    public static AnimalResponse toResponse(Animal animal) {
        if (animal == null) return null;
        return AnimalResponse.builder()
                .id(animal.getId())
                .numeroIdentification(animal.getNumeroIdentification())
                .nom(animal.getNom())
                .especeId(animal.getEspece() != null ? animal.getEspece().getId() : null)
                .especeNom(animal.getEspece() != null ? animal.getEspece().getNom() : null)
                .raceId(animal.getRace() != null ? animal.getRace().getId() : null)
                .raceNom(animal.getRace() != null ? animal.getRace().getNom() : null)
                .sexe(animal.getSexe())
                .dateNaissance(animal.getDateNaissance())
                .poids(animal.getPoids())
                .couleur(animal.getCouleur())
                .dateAcquisition(animal.getDateAcquisition())
                .prixAcquisition(animal.getPrixAcquisition())
                .origine(animal.getOrigine())
                .statut(animal.getStatut())
                .photoUrl(animal.getPhotoUrl())
                .description(animal.getDescription())
                .createdAt(animal.getCreatedAt())
                .updatedAt(animal.getUpdatedAt())
                .build();
    }
}
