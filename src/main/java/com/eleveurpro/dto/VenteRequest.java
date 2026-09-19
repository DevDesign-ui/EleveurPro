package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.ModePaiement;
import com.eleveurpro.entity.enums.StatutVente;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenteRequest {
    @NotNull(message = "L'animal est obligatoire")
    private Long animalId;

    @Size(max = 200, message = "Le nom du client ne doit pas dépasser 200 caractères")
    private String client;

    @NotNull(message = "La date est obligatoire")
    @PastOrPresent(message = "La date ne doit pas être dans le futur")
    private LocalDate date;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être positif")
    private BigDecimal prix;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private ModePaiement modePaiement;

    @NotNull(message = "Le statut est obligatoire")
    private StatutVente statut;

    @Size(max = 500, message = "L'observation ne doit pas dépasser 500 caractères")
    private String observation;
}
