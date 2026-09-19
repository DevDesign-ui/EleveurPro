package com.eleveurpro.mapper;

import com.eleveurpro.dto.VenteRequest;
import com.eleveurpro.dto.VenteResponse;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.Vente;

public class VenteMapper {

    public static Vente toEntity(VenteRequest request, Animal animal) {
        if (request == null) return null;
        return Vente.builder()
                .animal(animal)
                .client(request.getClient())
                .date(request.getDate())
                .prix(request.getPrix())
                .modePaiement(request.getModePaiement())
                .statut(request.getStatut())
                .observation(request.getObservation())
                .build();
    }

    public static VenteResponse toResponse(Vente entity) {
        if (entity == null) return null;
        return VenteResponse.builder()
                .id(entity.getId())
                .animalId(entity.getAnimal() != null ? entity.getAnimal().getId() : null)
                .animalNom(entity.getAnimal() != null ? entity.getAnimal().getNom() : null)
                .animalNumero(entity.getAnimal() != null ? entity.getAnimal().getNumeroIdentification() : null)
                .client(entity.getClient())
                .date(entity.getDate())
                .prix(entity.getPrix())
                .modePaiement(entity.getModePaiement())
                .statut(entity.getStatut())
                .observation(entity.getObservation())
                .build();
    }
}
