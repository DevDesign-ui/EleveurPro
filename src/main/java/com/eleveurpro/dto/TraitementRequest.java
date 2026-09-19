package com.eleveurpro.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraitementRequest {
    @NotNull(message = "L'animal est obligatoire")
    private Long animalId;

    @NotBlank(message = "Le médicament est obligatoire")
    @Size(max = 150, message = "Le médicament ne doit pas dépasser 150 caractères")
    private String medicament;

    @NotBlank(message = "Le dosage est obligatoire")
    @Size(max = 100, message = "Le dosage ne doit pas dépasser 100 caractères")
    private String dosage;

    @NotBlank(message = "La fréquence est obligatoire")
    @Size(max = 100, message = "La fréquence ne doit pas dépasser 100 caractères")
    private String frequence;

    @NotNull(message = "La date de début est obligatoire")
    @PastOrPresent(message = "La date de début ne doit pas être dans le futur")
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Size(max = 100, message = "Le nom du vétérinaire ne doit pas dépasser 100 caractères")
    private String veterinaire;

    @Size(max = 500, message = "L'observation ne doit pas dépasser 500 caractères")
    private String observation;
}
