package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.Sexe;
import com.eleveurpro.entity.enums.StatutAnimal;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalRequest {
    @NotBlank(message = "Le numéro d'identification est obligatoire")
    @Size(max = 100, message = "Le numéro d'identification ne doit pas dépasser 100 caractères")
    private String numeroIdentification;

    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    @NotNull(message = "L'espèce est obligatoire")
    private Long especeId;

    private Long raceId;

    @NotNull(message = "Le sexe est obligatoire")
    private Sexe sexe;

    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;

    @Positive(message = "Le poids doit être positif")
    private BigDecimal poids;

    @Size(max = 50, message = "La couleur ne doit pas dépasser 50 caractères")
    private String couleur;

    @PastOrPresent(message = "La date d'acquisition ne doit pas être dans le futur")
    private LocalDate dateAcquisition;

    @PositiveOrZero(message = "Le prix d'acquisition doit être positif ou nul")
    private BigDecimal prixAcquisition;

    @Size(max = 200, message = "L'origine ne doit pas dépasser 200 caractères")
    private String origine;

    @NotNull(message = "Le statut est obligatoire")
    private StatutAnimal statut;

    @Size(max = 500, message = "L'URL de la photo ne doit pas dépasser 500 caractères")
    private String photoUrl;

    @Size(max = 1000, message = "La description ne doit pas dépasser 1000 caractères")
    private String description;
}
