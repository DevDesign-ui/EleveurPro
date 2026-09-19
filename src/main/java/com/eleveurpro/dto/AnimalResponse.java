package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.Sexe;
import com.eleveurpro.entity.enums.StatutAnimal;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalResponse {
    private Long id;
    private String numeroIdentification;
    private String nom;
    private Long especeId;
    private String especeNom;
    private Long raceId;
    private String raceNom;
    private Sexe sexe;
    private LocalDate dateNaissance;
    private BigDecimal poids;
    private String couleur;
    private LocalDate dateAcquisition;
    private BigDecimal prixAcquisition;
    private String origine;
    private StatutAnimal statut;
    private String photoUrl;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
