package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.TypeAliment;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimentResponse {
    private Long id;
    private String nom;
    private TypeAliment type;
    private String unite;
    private Double quantiteStock;
    private BigDecimal prixUnitaire;
    private Double seuilAlerte;
    private LocalDate dateExpiration;
    private String fournisseur;
    private Boolean actif;
    private Boolean stockBas;
}
