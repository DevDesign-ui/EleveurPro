package com.eleveurpro.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationRequest {
    @NotNull(message = "L'animal est obligatoire")
    private Long animalId;

    @NotBlank(message = "Le vaccin est obligatoire")
    @Size(max = 150, message = "Le vaccin ne doit pas dépasser 150 caractères")
    private String vaccin;

    @NotNull(message = "La date de vaccination est obligatoire")
    @PastOrPresent(message = "La date de vaccination ne doit pas être dans le futur")
    private LocalDate dateVaccination;

    @FutureOrPresent(message = "La prochaine date doit être dans le futur ou aujourd'hui")
    private LocalDate prochaineDate;

    @Size(max = 100, message = "Le nom du vétérinaire ne doit pas dépasser 100 caractères")
    private String veterinaire;

    @Size(max = 500, message = "L'observation ne doit pas dépasser 500 caractères")
    private String observation;
}
