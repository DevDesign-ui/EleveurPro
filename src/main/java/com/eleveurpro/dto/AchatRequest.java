package com.eleveurpro.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchatRequest {
    @NotBlank(message = "La catégorie est obligatoire")
    @Size(max = 100, message = "La catégorie ne doit pas dépasser 100 caractères")
    private String categorie;

    @Size(max = 200, message = "Le fournisseur ne doit pas dépasser 200 caractères")
    private String fournisseur;

    @NotNull(message = "La date est obligatoire")
    @PastOrPresent(message = "La date ne doit pas être dans le futur")
    private LocalDate date;

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private BigDecimal montant;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;
}
