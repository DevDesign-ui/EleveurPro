package com.eleveurpro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchatResponse {
    private Long id;
    private String categorie;
    private String fournisseur;
    private LocalDate date;
    private BigDecimal montant;
    private String description;
}
