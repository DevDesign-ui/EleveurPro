package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.StatutConsultation;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationSanteRequest {
    @NotNull(message = "L'animal est obligatoire")
    private Long animalId;

    private Long maladieId;

    @NotNull(message = "La date est obligatoire")
    @PastOrPresent(message = "La date ne doit pas être dans le futur")
    private LocalDate date;

    @Size(max = 1000, message = "Les symptômes ne doivent pas dépasser 1000 caractères")
    private String symptomes;

    @Size(max = 1000, message = "Le diagnostic ne doit pas dépasser 1000 caractères")
    private String diagnostic;

    @Size(max = 1000, message = "Le traitement ne doit pas dépasser 1000 caractères")
    private String traitement;

    @Size(max = 100, message = "Le nom du vétérinaire ne doit pas dépasser 100 caractères")
    private String veterinaire;

    @Size(max = 500, message = "L'observation ne doit pas dépasser 500 caractères")
    private String observation;

    @NotNull(message = "Le statut est obligatoire")
    private StatutConsultation statut;
}
