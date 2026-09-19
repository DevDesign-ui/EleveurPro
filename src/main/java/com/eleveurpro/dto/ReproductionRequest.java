package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.StatutReproduction;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReproductionRequest {
    @NotNull(message = "Le mâle est obligatoire")
    private Long maleId;

    @NotNull(message = "La femelle est obligatoire")
    private Long femelleId;

    @NotNull(message = "La date d'accouplement est obligatoire")
    @PastOrPresent(message = "La date d'accouplement ne doit pas être dans le futur")
    private LocalDate dateAccouplement;

    @FutureOrPresent(message = "La date prévue de mise bas doit être dans le futur ou aujourd'hui")
    private LocalDate datePrevueMiseBas;

    @NotNull(message = "Le statut est obligatoire")
    private StatutReproduction statut;

    @Size(max = 500, message = "L'observation ne doit pas dépasser 500 caractères")
    private String observation;
}
