package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.TypeAliment;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimentRequest {
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 150, message = "Le nom ne doit pas dépasser 150 caractères")
    private String nom;

    @NotNull(message = "Le type est obligatoire")
    private TypeAliment type;

    @NotBlank(message = "L'unité est obligatoire")
    @Size(max = 20, message = "L'unité ne doit pas dépasser 20 caractères")
    private String unite;

    @PositiveOrZero(message = "La quantité en stock doit être positive ou nulle")
    private Double quantiteStock;

    @PositiveOrZero(message = "Le prix unitaire doit être positif ou nul")
    private BigDecimal prixUnitaire;

    @PositiveOrZero(message = "Le seuil d'alerte doit être positif ou nul")
    private Double seuilAlerte;

    @FutureOrPresent(message = "La date d'expiration doit être dans le futur ou aujourd'hui")
    private LocalDate dateExpiration;

    @Size(max = 200, message = "Le fournisseur ne doit pas dépasser 200 caractères")
    private String fournisseur;

    private Boolean actif;
}
